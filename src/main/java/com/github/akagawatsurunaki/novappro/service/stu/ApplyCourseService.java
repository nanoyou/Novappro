package com.github.akagawatsurunaki.novappro.service.stu;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import com.github.akagawatsurunaki.novappro.config.ResourceConfig;
import com.github.akagawatsurunaki.novappro.constant.VC;
import com.github.akagawatsurunaki.novappro.enumeration.ApprovalStatus;
import com.github.akagawatsurunaki.novappro.enumeration.BusType;
import com.github.akagawatsurunaki.novappro.mapper.*;
import com.github.akagawatsurunaki.novappro.model.database.User;
import com.github.akagawatsurunaki.novappro.model.database.approval.ApprovalFlow;
import com.github.akagawatsurunaki.novappro.model.database.approval.ApprovalFlowDetail;
import com.github.akagawatsurunaki.novappro.model.database.approval.CourseApplication;
import com.github.akagawatsurunaki.novappro.model.database.file.UploadFile;
import com.github.akagawatsurunaki.novappro.service.appro.ApprovalService;
import com.github.akagawatsurunaki.novappro.util.CourseUtil;
import com.github.akagawatsurunaki.novappro.util.IdUtil;
import com.github.akagawatsurunaki.novappro.util.ImgUtil;
import com.github.akagawatsurunaki.novappro.util.MyDb;
import lombok.Getter;
import lombok.NonNull;
import lombok.val;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;
import org.apache.ibatis.session.SqlSession;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class ApplyCourseService {
    @Getter
    private static final ApplyCourseService instance = new ApplyCourseService();

    public VC.Service apply(@NonNull Integer userId,
                            @NonNull List<String> courseIds,
                            @NonNull InputStream is,
                            @NonNull String remark) {

        // 只允许申请一个课程
        if (courseIds.size() != 1) {
            return VC.Service.NO_SUCH_USER;
        }

        try (SqlSession session = MyDb.use().openSession(true);
             val inputStream = new BufferedInputStream(is);) {

            val userMapper = session.getMapper(UserMapper.class);
            val uploadFileMapper = session.getMapper(UploadFileMapper.class);
            val approvalFlowMapper = session.getMapper(ApprovalFlowMapper.class);
            val approvalFlowDetailMapper = session.getMapper(ApprovalFlowDetailMapper.class);
            val courseApplicationMapper = session.getMapper(CourseApplicationMapper.class);
            val user = userMapper.selectById(userId);

            // 校验用户是否存在
            if (user != null) {

                val flowNo = IdUtil.genFlowNo(user.getId());
                val date = new Date();

                // 上传文件
                var fileType = FileTypeUtil.getType(inputStream);

                var path = ResourceConfig.UPLOADED_IMG_PATH;
                var name = ImgUtil.genImgName(userId);
                var file = new File(path + "/" + name + "." + fileType);

                var uploadFile = UploadFile.builder()
                        .fileName(name + "." + fileType)
                        .flowNo(flowNo)
                        .userId(userId)
                        .build();

                // 将文件关系存储至数据库

                var rows = uploadFileMapper.insert(uploadFile);

                if (rows == 1) {
                    // 将文件存储至硬盘
                    FileUtil.writeFromStream(inputStream, file, true);

                    // 创建Approval对象
                    var approval
                            = CourseApplication.builder()
                            .approCourses(CourseUtil.toCourseCodesStr(courseIds))
                            .flowNo(flowNo)
                            .addUserId(user.getId())
                            .addTime(date)
                            .build();


                    // 创建ApprovalFlow
                    var approvalFlow
                            = ApprovalFlow.builder()
                            .flowNo(flowNo)
                            .approStatus(ApprovalStatus.SUBMITTED)
                            .title("来自 " + user.getUsername() + "(" + user.getId() + ")的" + courseIds.size() + "门课程申请")
                            .busType(BusType.LINEAR)
                            .addUserId(user.getId())
                            .addTime(date)
                            .remark(remark)
                            .build();

                    for (var courseId : courseIds) {

                        val approverList = getApproverList(courseId);

                        // 创建ApprovalFlowDetail
                        if (approverList == null || approverList.isEmpty()) {
                            return VC.Service.ERROR;
                        }

                        for (var approver : approverList) {
                            var approvalFlowDetail
                                    = ApprovalFlowDetail.builder()
                                    .flowNo(flowNo)
                                    .auditUserId(approver.getId())
                                    .auditStatus(
                                            switch (approver.getType()) {
                                                case LECTURE_TEACHER -> ApprovalStatus.LECTURE_TEACHER_EXAMING;
                                                case SUPERVISOR_TEACHER -> ApprovalStatus.SUPERVISOR_TEACHER_EXAMING;
                                                default ->
                                                        throw new IllegalStateException("Unexpected value: " + approver.getType());
                                            }
                                    )
                                    .auditRemark("")
                                    .auditTime(date)
                                    .build();

                            // 插入数据库
                            approvalFlowDetailMapper.insert(approvalFlowDetail);
                        }
                    }

                    // 向数据库插入
                    courseApplicationMapper.insert((CourseApplication) approval);

                    rows = approvalFlowMapper.insert(approvalFlow);

                    if (rows != 1) {
                        return VC.Service.ERROR;
                    }

                    return VC.Service.OK;
                }
                return VC.Service.ERROR;
            }

            return VC.Service.NO_SUCH_USER;

        } catch (IOException e) {
            e.printStackTrace();
            return VC.Service.ERROR;
        }
    }


    /**
     * 按照指定的课程代码找到审批流程中的所有审批人, 并按照每个审批人的权重值来排序.
     *
     * @param courseCode 指定的课程代码, 即审批人可以进行审批的课程的课程代码
     * @return 有顺序的
     */
    private List<User> getApproverList(@NonNull String courseCode) {
        try (var session = MyDb.use().openSession(true)) {

            val approvalAuthorityMapper = session.getMapper(ApprovalAuthorityMapper.class);
            val userMapper = session.getMapper(UserMapper.class);

            // 顺序不能变, 已经按照权重值排序
            val userIds = approvalAuthorityMapper.selectUserIdsByCourseCodeDesc(courseCode);

            if (userIds == null || userIds.isEmpty()) {
                return null;
            }

            // 使其顺序不能更改
            return Collections.unmodifiableList(userMapper.selectByIds(userIds));
        }
    }

    /**
     * 根据用户的ID查询其下的所有课程申请
     *
     * @param userId
     * @return
     */
    public Triple<VC.Service, List<CourseApplication>, List<ApprovalStatus>> getCourseApplsByUserId(@NonNull Integer userId) {

        try (SqlSession session = MyDb.use().openSession()) {
            val courseApplicationMapper = session.getMapper(CourseApplicationMapper.class);
            val userMapper = session.getMapper(UserMapper.class);

            // 用户是否存在
            var user = userMapper.selectById(userId);
            if (user != null) {

                // 根据用户ID查询课程申请(CourseApplication)列表
                var courseApplicationList = courseApplicationMapper.select(user.getId());
                List<ApprovalStatus> approvalStatusList = new ArrayList<>();

                if (courseApplicationList != null) {
                    for (var ca : courseApplicationList) {
                        assert ca.getApproCourses() != null && !ca.getApproCourses().isEmpty();
                        var currentNode = ApprovalService.getInstance().getCurrentApprovalFlowNode(ca.getFlowNo());
                        approvalStatusList.add(currentNode.getAuditStatus());
                    }
                }

                return new ImmutableTriple<>(VC.Service.OK, courseApplicationList, approvalStatusList);
            }
            return new ImmutableTriple<>(VC.Service.NO_SUCH_USER, null, null);
        }
    }
}
