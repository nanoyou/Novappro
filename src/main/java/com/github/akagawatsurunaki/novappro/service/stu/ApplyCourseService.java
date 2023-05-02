package com.github.akagawatsurunaki.novappro.service.stu;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import com.github.akagawatsurunaki.novappro.config.ResourceConfig;
import com.github.akagawatsurunaki.novappro.constant.VC;
import com.github.akagawatsurunaki.novappro.enumeration.ApprovalStatus;
import com.github.akagawatsurunaki.novappro.enumeration.BusType;
import com.github.akagawatsurunaki.novappro.mapper.*;
import com.github.akagawatsurunaki.novappro.mapper.impl.*;
import com.github.akagawatsurunaki.novappro.model.database.User;
import com.github.akagawatsurunaki.novappro.model.database.approval.ApprovalFlow;
import com.github.akagawatsurunaki.novappro.model.database.approval.ApprovalFlowDetail;
import com.github.akagawatsurunaki.novappro.model.database.approval.CourseApplication;
import com.github.akagawatsurunaki.novappro.model.database.approval.CourseApproFlow;
import com.github.akagawatsurunaki.novappro.model.database.file.UploadFile;
import com.github.akagawatsurunaki.novappro.util.CourseUtil;
import com.github.akagawatsurunaki.novappro.util.IdUtil;
import com.github.akagawatsurunaki.novappro.util.ImgUtil;
import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;

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

    private static final UserMapper USER_MAPPER = UserMapperImpl.getInstance();

    private static final CourseApplicationMapper COURSE_APPLICATION_MAPPER = CourseApplicationMapperImpl.getInstance();

    private static final ApprovalFlowMapper APPROVAL_FLOW_MAPPER = ApprovalFlowMapperImpl.getInstance();

    private static final ApprovalFlowDetailMapper APPROVAL_FLOW_DETAIL_MAPPER =
            ApprovalFlowDetailMapperImpl.getInstance();

    private static final CourseApproFlowMapper COURSE_APPRO_FLOW_MAPPER = CourseApproFlowMapperImpl.getInstance();

    private static final UploadFileMapper UPLOAD_FILE_MAPPER = UploadFileMapperImpl.getInstance();

    private static final User approver;

    static {
        // TODO: 默认只允许于老师参与审批课程任务
        approver = USER_MAPPER.selectUserById(20210004).getRight();
    }

    public VC.Service apply(@NonNull Integer userId, @NonNull List<String> courseIds, @NonNull InputStream is) {

        // 校验用户是否存在
        try {
            var vc_user = USER_MAPPER.selectUserById(userId);
            var vc = vc_user.getLeft();

            if (vc == VC.Mapper.NO_SUCH_ENTITY) {
                return VC.Service.NO_SUCH_USER;
            }

            // TODO: 校验课程是否存在

            // 用户存在

            if (vc == VC.Mapper.OK) {

                var user = vc_user.getRight();
                var flowNo = IdUtil.genFlowNo(user.getId());
                Date date = new Date();

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
                var vc_uf = UPLOAD_FILE_MAPPER.insert(uploadFile);
                if (vc_uf.getLeft() != VC.Mapper.OK) {
                    return VC.Service.ERROR;
                }

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
                        .build();

                // 创建ApprovalFlowDetail

                var approvalFlowDetail
                        = ApprovalFlowDetail.builder()
                        .flowNo(flowNo)
                        .auditUserId(approver.getId())
                        .auditStatus(ApprovalStatus.LECTURE_TEACHER_EXAMING)
                        .auditRemark("")
                        .auditTime(date)
                        .build();

                APPROVAL_FLOW_DETAIL_MAPPER.insert(approvalFlowDetail);
                approvalFlowDetail = APPROVAL_FLOW_DETAIL_MAPPER.select(flowNo).getRight();

                // 创建CourseApproFlow

                var courseApproFlow = CourseApproFlow.builder()
                        .approFlowNos(flowNo)
                        .approFlowDetailIds(Arrays.toString(new String[]{approvalFlowDetail.getId().toString()}))
                        .currentNodeNo(approvalFlowDetail.getId())
                        .build();

                // 向数据库插入
                COURSE_APPLICATION_MAPPER.insert((CourseApplication) approval);
                APPROVAL_FLOW_MAPPER.insert(approvalFlow);

                COURSE_APPRO_FLOW_MAPPER.insert(courseApproFlow);

                return VC.Service.OK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return VC.Service.ERROR;
    }

    public Triple<VC.Service, List<CourseApplication>, List<ApprovalStatus>> getCourseApplsByUserId(@NonNull Integer userId) {
        // 校验用户是否存在
        var vc_user = USER_MAPPER.selectUserById(userId);
        var vc = vc_user.getLeft();

        if (vc == VC.Mapper.NO_SUCH_ENTITY) {
            return new ImmutableTriple<>(VC.Service.NO_SUCH_USER, null, null);
        }

        var user = vc_user.getRight();

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
}
