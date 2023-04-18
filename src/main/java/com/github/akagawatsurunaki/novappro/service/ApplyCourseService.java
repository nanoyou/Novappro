package com.github.akagawatsurunaki.novappro.service;

import com.github.akagawatsurunaki.novappro.constant.VerifyCode;
import com.github.akagawatsurunaki.novappro.enumeration.ApprovalStatus;
import com.github.akagawatsurunaki.novappro.enumeration.BusType;
import com.github.akagawatsurunaki.novappro.mapper.*;
import com.github.akagawatsurunaki.novappro.mapper.impl.*;
import com.github.akagawatsurunaki.novappro.model.User;
import com.github.akagawatsurunaki.novappro.model.approval.ApprovalFlow;
import com.github.akagawatsurunaki.novappro.model.approval.ApprovalFlowDetail;
import com.github.akagawatsurunaki.novappro.model.approval.CourseApplication;
import com.github.akagawatsurunaki.novappro.model.approval.CourseApproFlow;
import com.github.akagawatsurunaki.novappro.util.CourseUtil;
import com.github.akagawatsurunaki.novappro.util.IdUtil;
import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

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

    private static final User approver;

    static {
        // TODO: 默认只允许于老师参与审批课程任务
        approver = USER_MAPPER.getUserById(20210004).getRight();
    }

    public VerifyCode.Service apply(@NonNull Integer userId, @NonNull List<String> courseIds) {

        // 校验用户是否存在
        var vc_user = USER_MAPPER.getUserById(userId);
        var vc = vc_user.getLeft();

        if (vc == VerifyCode.Mapper.NO_SUCH_ENTITY) {
            return VerifyCode.Service.NO_SUCH_USER;
        }

        // TODO: 校验课程是否存在

        // 用户存在

        if (vc == VerifyCode.Mapper.OK) {

            var user = vc_user.getRight();
            var flowNo = IdUtil.genFlowNo(user.getId());
            Date date = new Date();

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
                    .approStatus(ApprovalStatus.AUDITING)
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
                    .auditStatus(ApprovalStatus.AUDITING)
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

            return VerifyCode.Service.OK;
        }
        return VerifyCode.Service.ERROR;
    }

    public Pair<VerifyCode.Service, List<CourseApplication>> getAppliedCourses(@NonNull Integer userId) {
        // 校验用户是否存在
        var vc_user = USER_MAPPER.getUserById(userId);
        var vc = vc_user.getLeft();

        if (vc == VerifyCode.Mapper.NO_SUCH_ENTITY) {
            return new ImmutablePair<>(VerifyCode.Service.NO_SUCH_USER, null);
        }

        var user = vc_user.getRight();

        var vc_l = COURSE_APPLICATION_MAPPER.select(user.getId());

        if (vc_l.getLeft() == VerifyCode.Mapper.OK) {

            return new ImmutablePair<>(VerifyCode.Service.OK, vc_l.getRight());
        }
        return new ImmutablePair<>(VerifyCode.Service.ERROR, null);
    }

}
