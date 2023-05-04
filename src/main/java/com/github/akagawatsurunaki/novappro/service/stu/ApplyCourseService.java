package com.github.akagawatsurunaki.novappro.service.stu;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import com.github.akagawatsurunaki.novappro.config.ResourceConfig;
import com.github.akagawatsurunaki.novappro.constant.VC;
import com.github.akagawatsurunaki.novappro.enumeration.ApprovalStatus;
import com.github.akagawatsurunaki.novappro.enumeration.BusType;
import com.github.akagawatsurunaki.novappro.mapper.*;
import com.github.akagawatsurunaki.novappro.mapper.impl.ApprovalFlowDetailMapperImpl;
import com.github.akagawatsurunaki.novappro.mapper.impl.CourseApplicationMapperImpl;
import com.github.akagawatsurunaki.novappro.mapper.impl.CourseApproFlowMapperImpl;
import com.github.akagawatsurunaki.novappro.model.database.User;
import com.github.akagawatsurunaki.novappro.model.database.approval.ApprovalFlow;
import com.github.akagawatsurunaki.novappro.model.database.approval.ApprovalFlowDetail;
import com.github.akagawatsurunaki.novappro.model.database.approval.CourseApplication;
import com.github.akagawatsurunaki.novappro.model.database.approval.CourseApproFlow;
import com.github.akagawatsurunaki.novappro.model.database.file.UploadFile;
import com.github.akagawatsurunaki.novappro.util.CourseUtil;
import com.github.akagawatsurunaki.novappro.util.IdUtil;
import com.github.akagawatsurunaki.novappro.util.ImgUtil;
import com.github.akagawatsurunaki.novappro.util.MyDb;
import lombok.Getter;
import lombok.NonNull;
import lombok.val;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.apache.ibatis.session.SqlSession;

import javax.crypto.spec.OAEPParameterSpec;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ApplyCourseService {
    @Getter
    private static final ApplyCourseService instance = new ApplyCourseService();

    private static final CourseApplicationMapper COURSE_APPLICATION_MAPPER = CourseApplicationMapperImpl.getInstance();


    private static final ApprovalFlowDetailMapper APPROVAL_FLOW_DETAIL_MAPPER =
            ApprovalFlowDetailMapperImpl.getInstance();

    private static final CourseApproFlowMapper COURSE_APPRO_FLOW_MAPPER = CourseApproFlowMapperImpl.getInstance();

    private static final User approver;

    static {
        // TODO: 默认只允许于老师参与审批课程任务
        try (SqlSession session = MyDb.use().openSession(true)) {
            var userMapper = session.getMapper(UserMapper.class);
            approver = userMapper.selectById(20210004);
        }
    }

    public VC.Service apply(@NonNull Integer userId,
                            @NonNull List<String> courseIds,
                            @NonNull InputStream is,
                            @NonNull String remark) {

        // 只允许申请一个课程
        if (courseIds.size() != 1) {
            return VC.Service.NO_SUCH_USER;
        }

        try (SqlSession session = MyDb.use().openSession(true)) {

            val userMapper = session.getMapper(UserMapper.class);
            val uploadFileMapper = session.getMapper(UploadFileMapper.class);
            val approvalFlowMapper = session.getMapper(ApprovalFlowMapper.class);
            val user = userMapper.selectById(userId);

            // 校验用户是否存在
            if (user != null) {

                val flowNo = IdUtil.genFlowNo(user.getId());
                val date = new Date();

                // 上传文件

                var fileType = FileTypeUtil.getType(is);
                // 如果不调用此方法, 文件写入将无法被识别为图片文件, 图片文件将会无法打开.
                is.reset();

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
                    FileUtil.writeFromStream(is, file, true);

                    // 创建Approval对象
                    var approval
                            = CourseApplication.builder()
                            .approCourseIds(CourseUtil.toCourseCodesStr(courseIds))
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

                    // TODO: 每个ApprovalFlowDetail应该被创建多个, 形成一个链条

                    for (var courseId : courseIds) {

                        var approverList = getApproverList(courseId);

                        // 应调用下面的方法
                        // 创建ApprovalFlowDetail

                        for (var approver : approverList) {
                            var approvalFlowDetail
                                    = ApprovalFlowDetail.builder()
                                    .flowNo(flowNo)
                                    .auditUserId(approver.getId())
                                    // TODO: check if has any error
//                                    .auditStatus(ApprovalStatus.LECTURE_TEACHER_EXAMING)
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
                            APPROVAL_FLOW_DETAIL_MAPPER.insert(approvalFlowDetail);
                        }


                        //   approvalFlowDetail = APPROVAL_FLOW_DETAIL_MAPPER.select(flowNo).getRight();

                    }


                    // TODO: crs_appro_flow may be deprecated later. To check safety.

                    // 创建CourseApproFlow
//                    var courseApproFlow = CourseApproFlow.builder()
//                            .approFlowNos(flowNo)
//                            .approFlowDetailIds(Arrays.toString(new String[]{approvalFlowDetail.getId().toString()}))
//                            .currentNodeNo(approvalFlowDetail.getId())
//                            .build();

                    // 向数据库插入
                    COURSE_APPLICATION_MAPPER.insert((CourseApplication) approval);

                    rows = approvalFlowMapper.insert(approvalFlow);

                    if (rows != 1) {
                        return VC.Service.ERROR;
                    }

                    //  COURSE_APPRO_FLOW_MAPPER.insert(courseApproFlow);

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
     * TODO: 获取审批人列表, 选择指定的课程, 按照权重值降序排序.(id -> get -> approvers)
     * 把审批人对应需要创建的ApproveDetail创建出来保存在数据库中
     */
    private List<User> getApproverList(@NonNull String courseCode) {
        throw new NotImplementedException();
    }

    /**
     * 根据用户的ID查询其下的所有课程申请
     *
     * @param userId
     * @return
     */
    public Triple<VC.Service, List<CourseApplication>, List<ApprovalStatus>> getCourseApplsByUserId(@NonNull Integer userId) {

        try (SqlSession session = MyDb.use().openSession()) {

            var userMapper = session.getMapper(UserMapper.class);
            var user = userMapper.selectById(userId);

            if (user != null) {
                var vc_l = COURSE_APPLICATION_MAPPER.select(user.getId());
                var courseApplicationList = vc_l.getRight();

                List<ApprovalStatus> approvalStatusList = new ArrayList<>();

                for (var ca : courseApplicationList) {
                    var s = APPROVAL_FLOW_DETAIL_MAPPER.select(ca.getFlowNo()).getRight();
                    var st = s.getAuditStatus();
                    approvalStatusList.add(st);
                }

                if (vc_l.getLeft() == VC.Mapper.OK) {
                    return new ImmutableTriple<>(VC.Service.OK, courseApplicationList, approvalStatusList);
                }
                return new ImmutableTriple<>(VC.Service.ERROR, null, null);
            }
            return new ImmutableTriple<>(VC.Service.NO_SUCH_USER, null, null);
        }
    }
}
