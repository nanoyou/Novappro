package com.github.akagawatsurunaki.novappro.service.appro;

import com.github.akagawatsurunaki.novappro.constant.VerifyCode;
import com.github.akagawatsurunaki.novappro.mapper.*;
import com.github.akagawatsurunaki.novappro.mapper.impl.*;
import com.github.akagawatsurunaki.novappro.model.database.course.Course;
import com.github.akagawatsurunaki.novappro.model.frontend.ApplItem;
import com.github.akagawatsurunaki.novappro.model.frontend.CourseAppItemDetail;
import com.github.akagawatsurunaki.novappro.util.CourseUtil;
import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class ApprovalService {

    @Getter
    private static final ApprovalService instance = new ApprovalService();

    private static final UserMapper USER_MAPPER = UserMapperImpl.getInstance();

    private static final CourseApplicationMapper COURSE_APPLICATION_MAPPER = CourseApplicationMapperImpl.getInstance();

    private static final ApprovalFlowMapper APPROVAL_FLOW_MAPPER = ApprovalFlowMapperImpl.getInstance();

    private static final ApprovalFlowDetailMapper APPROVAL_FLOW_DETAIL_MAPPER =
            ApprovalFlowDetailMapperImpl.getInstance();

    private static final CourseMapper COURSE_MAPPER = CourseMapperImpl.getInstance();


    public Pair<VerifyCode.Service, List<ApplItem>> getApplItems(@NonNull Integer approverId) {
        var vc_flowNos = APPROVAL_FLOW_DETAIL_MAPPER.selectFlowNoByApproverId(approverId);

        if (vc_flowNos.getLeft() == VerifyCode.Mapper.OK) {

            var flowNoList = vc_flowNos.getRight();
            List<ApplItem> result = new ArrayList<>();

            for (String flowNo : flowNoList) {
                var vc_applItem = getApplItem(flowNo);
                if (vc_applItem.getLeft() != VerifyCode.Service.OK) {
                    return new ImmutablePair<>(VerifyCode.Service.ERROR, null);
                }
                result.add(vc_applItem.getRight());
            }

            return new ImmutablePair<>(VerifyCode.Service.OK, result);

        }
        return new ImmutablePair<>(VerifyCode.Service.ERROR, null);
    }

    /**
     * 获取一个ApplItem对象
     */
    public Pair<VerifyCode.Service, ApplItem> getApplItem(@NonNull String flowNo) {

        var vc_af = APPROVAL_FLOW_MAPPER.select(flowNo);

        if (vc_af.getLeft() == VerifyCode.Mapper.OK) {

            var approFlow = vc_af.getRight();
            var vc_afd = APPROVAL_FLOW_DETAIL_MAPPER.select(flowNo);

            if (vc_afd.getLeft() == VerifyCode.Mapper.OK) {

                var applFlowDetail = vc_afd.getRight();

                var vc_addUser = USER_MAPPER.selectUserById(approFlow.getAddUserId());
                var vc_approver = USER_MAPPER.selectUserById(applFlowDetail.getAuditUserId());

                if (vc_addUser.getLeft() == VerifyCode.Mapper.OK && vc_approver.getLeft() == VerifyCode.Mapper.OK) {

                    var applicant = vc_addUser.getRight();
                    var approver = vc_approver.getRight();

                    var applItem = ApplItem.builder()
                            .flowNo(approFlow.getFlowNo())
                            .title(approFlow.getTitle())
                            .applicantName(applicant.getUsername())
                            .addTime(approFlow.getAddTime())
                            .approverName(approver.getUsername())
                            .approvalStatus(applFlowDetail.getAuditStatus())
                            .build();

                    return new ImmutablePair<>(VerifyCode.Service.OK, applItem);
                }
            }
        }
        return new ImmutablePair<>(VerifyCode.Service.ERROR, null);
    }


    public Pair<VerifyCode.Service, CourseAppItemDetail> getDetail(@NonNull String flowNo) {
        // TODO: 2023年4月20日 DEBUG
        var vc_af = APPROVAL_FLOW_MAPPER.select(flowNo);

        if (vc_af.getLeft() == VerifyCode.Mapper.OK) {
            var approvalFlow = vc_af.getRight();
            var vc_addUser = USER_MAPPER.selectUserById(approvalFlow.getAddUserId());

            if (vc_addUser.getLeft() == VerifyCode.Mapper.OK) {

                var addUser = vc_addUser.getRight();
                var vc_afd = APPROVAL_FLOW_DETAIL_MAPPER.select(flowNo);

                if (vc_afd.getLeft() == VerifyCode.Mapper.OK) {
                    var approFlowDetail = vc_afd.getRight();

                    var vc_ac = getAppliedCourses(flowNo);

                    if (vc_ac.getLeft() == VerifyCode.Service.OK) {
                        var appliedCourses = vc_ac.getRight();

                        var result = CourseAppItemDetail.builder()
                                .flowNo(flowNo)
                                .title(approvalFlow.getTitle())
                                .applicantId(addUser.getId())
                                .applicantName(addUser.getUsername())
                                .addTime(approvalFlow.getAddTime())
                                .approStatus(approFlowDetail.getAuditStatus())
                                .applCourses(appliedCourses)
                                .build();

                        return new ImmutablePair<>(VerifyCode.Service.OK, result);
                    }
                }
            }
        }
        return new ImmutablePair<>(VerifyCode.Service.ERROR, null);
    }


    private Pair<VerifyCode.Service, List<Course>> getAppliedCourses(@NonNull String flowNo) {
        var vc_ca = COURSE_APPLICATION_MAPPER.selectByFlowNo(flowNo);
        if (vc_ca.getLeft() == VerifyCode.Mapper.OK) {
            var appl = vc_ca.getRight();
            var courseIds = CourseUtil.getCourseCodes(appl.getApproCourseIds());
            var vc_courses = COURSE_MAPPER.selectCourses(courseIds);
            if (vc_courses.getLeft() == VerifyCode.Mapper.OK) {
                return new ImmutablePair<>(VerifyCode.Service.OK, vc_courses.getRight());
            }
        }
        return new ImmutablePair<>(VerifyCode.Service.ERROR, null);
    }


    /*
    TODO: 默认于老师为课程申请的审批人, 登录后, 网页上将会显示当前需要审批的审批.

    登陆后跳转到审批人的界面, 界面显示了loginUser的可以进行审批的审批列表.


    appl_list

    |---------------------------------------------|
    |审批流号  |标题|发起人|审批人|申请时间|申请状态 |操作  |
    -----------------------------------------------
    | weuusa  你好  我      待我审批    |查看详情   |
    -----------------------------------------------
    |...                                          |

    点入其中一个审批列表

    其中有一分类为课程审批, 将会

    appl_detail

    申请详情

    flowNo applTitle
    applicant..
    applDate
    applCourses
    applStatus
    --------------------------------------------
    | 显示流水号:           |          申请标题  |
    --------------------------------------------
    |申请人信息
    --------------------------------------------
    |学号: 2398833
    |姓名: 你好
    --------------------------------------------
    |申请内容
    --------------------------------------------
    |申请时间: 2023年4月18日 12:12
    |申请内容: 申请人请求申请如下的课程
    | 课程名称(课程编号) | 授课教师
    |
    --------------------------------------------
    |审批状态: 待审批
    --------------------------------------------

    appro_comment

    审批意见
    [
                                                ]
    submit_appl_status

    [同意]              [拒绝]               [取消]

     */

}
