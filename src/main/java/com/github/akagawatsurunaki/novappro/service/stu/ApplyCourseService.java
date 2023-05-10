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
import com.github.akagawatsurunaki.novappro.model.frontend.ServiceMessage;
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

import javax.annotation.Nullable;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ApplyCourseService {
    @Getter
    private static final ApplyCourseService instance = new ApplyCourseService();


    public ServiceMessage apply(@Nullable Integer userId,
                                @Nullable String courseId,
                                @Nullable InputStream is,
                                @Nullable String remark) {

        List<String> courseIds = new ArrayList<>();
        courseIds.add(courseId);
        return apply(userId, courseIds, is, remark);
    }

    public ServiceMessage apply(@Nullable Integer userId,
                                @Nullable List<String> courseIds,
                                @Nullable InputStream is,
                                @Nullable String remark) {

        if (userId == null) {
            return ServiceMessage.of(
                    ServiceMessage.Level.WARN,
                    "用户ID不能为空"
            );
        }

        if (courseIds == null) {
            return ServiceMessage.of(
                    ServiceMessage.Level.WARN,
                    "课程代码不能为空"
            );
        }

        if (is == null) {
            return ServiceMessage.of(
                    ServiceMessage.Level.FATAL,
                    "InputStream异常"
            );
        }

        // 只允许申请一个课程
        if (courseIds.size() != 1) {
            return ServiceMessage.of(
                    ServiceMessage.Level.ERROR,
                    "只允许申请一门课程"
            );
        }

        if (remark == null) {
            remark = "";
        }

        return _apply(userId, courseIds, is, remark);
    }


    private ServiceMessage _apply(@NonNull Integer userId,
                                  @NonNull List<String> courseIds,
                                  @NonNull InputStream is,
                                  @NonNull String remark) {

        try (
                SqlSession session = MyDb.use().openSession(true);
                val inputStream = new BufferedInputStream(is)
        ) {

            val userMapper = session.getMapper(UserMapper.class);
            val uploadFileMapper = session.getMapper(UploadFileMapper.class);
            val approvalFlowMapper = session.getMapper(ApprovalFlowMapper.class);
            val approvalFlowDetailMapper = session.getMapper(ApprovalFlowDetailMapper.class);
            val courseApplicationMapper = session.getMapper(CourseApplicationMapper.class);

            val user = userMapper.selectById(userId);
            // 校验用户是否存在
            if (user == null) {
                return ServiceMessage.of(
                        ServiceMessage.Level.ERROR,
                        "该用户不存在"
                );
            }

            val flowNo = IdUtil.genFlowNo(user.getId());
            val date = new Date();

            // 上传文件
            if (!uploadFile(inputStream, userId, flowNo)) {
                return ServiceMessage.of(
                        ServiceMessage.Level.FATAL,
                        "文件上传失败。"
                );
            }

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

            assert !courseIds.isEmpty();
            for (var courseId : courseIds) {

                val approverList = getApproverList(courseId);

                // 创建ApprovalFlowDetail
                if (approverList == null || approverList.isEmpty()) {
                    return ServiceMessage.of(
                            ServiceMessage.Level.FATAL,
                            "审批人列表为空"
                    );
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
                    if (approvalFlowDetailMapper.insert(approvalFlowDetail) != 1) {
                        return ServiceMessage.of(
                                ServiceMessage.Level.FATAL,
                                "课程申请失败，无法插入审批流明细。"
                        );
                    }
                }
            }

            // 向数据库插入
            if (courseApplicationMapper.insert(approval) != 1) {
                return ServiceMessage.of(
                        ServiceMessage.Level.FATAL,
                        "课程申请失败，无法插入课程申请。"
                );
            }

            if (approvalFlowMapper.insert(approvalFlow) != 1) {
                return ServiceMessage.of(
                        ServiceMessage.Level.FATAL,
                        "课程申请失败，无法插入申请流申请。"
                );
            }
            return ServiceMessage.of(
                    ServiceMessage.Level.SUCCESS,
                    "课程申请成功！"
            );
        } catch (IOException e) {
            return ServiceMessage.of(
                    ServiceMessage.Level.FATAL,
                    "无法写入文件。"
            );
        }
    }


    private boolean uploadFile(
            @NonNull InputStream inputStream,
            @NonNull Integer userId,
            @NonNull String flowNo
    ) {
        try (var session = MyDb.use().openSession(true)) {
            val uploadFileMapper = session.getMapper(UploadFileMapper.class);

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
            if (uploadFileMapper.insert(uploadFile) == 0) {
                return false;
            }

            // 将文件存储至硬盘
            return FileUtil.writeFromStream(inputStream, file, true) != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
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
    public Triple<ServiceMessage, List<CourseApplication>, List<ApprovalStatus>> getCourseApplsByUserId(@Nullable Integer userId) {
        if (userId == null) {
            return ImmutableTriple.of(
                    ServiceMessage.of(ServiceMessage.Level.WARN, "用户ID不能为空"),
                    new ArrayList<>(), new ArrayList<>()
            );
        }

        return _getCourseApplsByUserId(userId);

    }

    public Triple<ServiceMessage, List<CourseApplication>, List<ApprovalStatus>> _getCourseApplsByUserId(@NonNull Integer userId) {

        try (SqlSession session = MyDb.use().openSession()) {
            val courseApplicationMapper = session.getMapper(CourseApplicationMapper.class);
            val userMapper = session.getMapper(UserMapper.class);

            // 用户是否存在
            val user = userMapper.selectById(userId);

            if (user == null) {
                return ImmutableTriple.of(
                        ServiceMessage.of(ServiceMessage.Level.ERROR, "用户不存在"),
                        new ArrayList<>(), new ArrayList<>()
                );
            }

            // 根据用户ID查询课程申请(CourseApplication)列表
            val courseApplicationList = courseApplicationMapper.select(user.getId());

            if (courseApplicationList == null) {
                return ImmutableTriple.of(
                        ServiceMessage.of(ServiceMessage.Level.ERROR, "课程申请列表为空"),
                        new ArrayList<>(), new ArrayList<>()
                );
            }

            val approvalStatusList = courseApplicationList.stream()
                    .map(ca -> ApprovalService.getInstance().getCurrentApprovalFlowNode(ca.getFlowNo()).getAuditStatus())
                    .toList();

            if (courseApplicationList.size() != approvalStatusList.size()) {
                return ImmutableTriple.of(
                        ServiceMessage.of(ServiceMessage.Level.ERROR, "信息不一致错误"),
                        new ArrayList<>(), new ArrayList<>()
                );
            }

            return ImmutableTriple.of(
                    ServiceMessage.of(ServiceMessage.Level.SUCCESS, "服务成功"),
                    courseApplicationList, approvalStatusList
            );
        }
    }
}
