package com.github.akagawatsurunaki.novappro.service.appro;

import com.github.akagawatsurunaki.novappro.constant.VC;
import com.github.akagawatsurunaki.novappro.enumeration.ApprovalStatus;
import com.github.akagawatsurunaki.novappro.mapper.*;
import com.github.akagawatsurunaki.novappro.mapper.impl.*;
import com.github.akagawatsurunaki.novappro.model.database.course.Course;
import com.github.akagawatsurunaki.novappro.model.frontend.ApplItem;
import com.github.akagawatsurunaki.novappro.model.frontend.CourseAppItemDetail;
import com.github.akagawatsurunaki.novappro.util.CourseUtil;
import com.github.akagawatsurunaki.novappro.util.MyDb;
import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.ibatis.session.SqlSession;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ApprovalService {

    @Getter
    private static final ApprovalService instance = new ApprovalService();

    private static final UserMapper USER_MAPPER = UserMapperImpl.getInstance();

    private static final CourseApplicationMapper COURSE_APPLICATION_MAPPER = CourseApplicationMapperImpl.getInstance();

    private static final CourseApproFlowMapper COURSE_APPRO_FLOW_MAPPER = CourseApproFlowMapperImpl.getInstance();
    private static final ApprovalFlowMapper APPROVAL_FLOW_MAPPER = ApprovalFlowMapperImpl.getInstance();

    private static final ApprovalFlowDetailMapper APPROVAL_FLOW_DETAIL_MAPPER =
            ApprovalFlowDetailMapperImpl.getInstance();

    private static final CourseMapper COURSE_MAPPER = CourseMapperImpl.getInstance();


    public Pair<VC.Service, List<ApplItem>> getApplItems(@NonNull Integer approverId) {
        var vc_flowNos = APPROVAL_FLOW_DETAIL_MAPPER.selectFlowNoByApproverId(approverId);

        if (vc_flowNos.getLeft() == VC.Mapper.OK) {

            var flowNoList = vc_flowNos.getRight();
            List<ApplItem> result = new ArrayList<>();

            for (String flowNo : flowNoList) {
                var vc_applItem = getApplItem(flowNo);
                if (vc_applItem.getLeft() != VC.Service.OK) {
                    return new ImmutablePair<>(VC.Service.ERROR, null);
                }
                result.add(vc_applItem.getRight());
            }

            return new ImmutablePair<>(VC.Service.OK, result);

        }
        return new ImmutablePair<>(VC.Service.ERROR, null);
    }

    /**
     * 获取一个ApplItem对象
     */
    public Pair<VC.Service, ApplItem> getApplItem(@NonNull String flowNo) {

        var vc_af = APPROVAL_FLOW_MAPPER.select(flowNo);

        if (vc_af.getLeft() == VC.Mapper.OK) {

            var approFlow = vc_af.getRight();
            var vc_afd = APPROVAL_FLOW_DETAIL_MAPPER.select(flowNo);

            if (vc_afd.getLeft() == VC.Mapper.OK) {

                var applFlowDetail = vc_afd.getRight();

                // TODO
                try (SqlSession session = MyDb.use().openSession()) {

                    var userMapper = session.getMapper(UserMapper.class);

                    var addUser = userMapper.selectById(approFlow.getAddUserId());
                    var approver = userMapper.selectById(applFlowDetail.getAuditUserId());

                    if (addUser != null && approver != null) {
                        var applItem = ApplItem.builder()
                                .flowNo(approFlow.getFlowNo())
                                .title(approFlow.getTitle())
                                .applicantName(addUser.getUsername())
                                .addTime(approFlow.getAddTime())
                                .approverName(approver.getUsername())
                                .approvalStatus(applFlowDetail.getAuditStatus())
                                .build();

                        return new ImmutablePair<>(VC.Service.OK, applItem);
                    }
                }


                    // END TODO

                    // var vc_addUser = USER_MAPPER.selectUserById(approFlow.getAddUserId());
                    // var vc_approver = USER_MAPPER.selectUserById(applFlowDetail.getAuditUserId());

//                if (vc_addUser.getLeft() == VC.Mapper.OK && vc_approver.getLeft() == VC.Mapper.OK) {
//
//                    var applicant = vc_addUser.getRight();
//                    var approver = vc_approver.getRight();
//
//                    var applItem = ApplItem.builder()
//                            .flowNo(approFlow.getFlowNo())
//                            .title(approFlow.getTitle())
//                            .applicantName(applicant.getUsername())
//                            .addTime(approFlow.getAddTime())
//                            .approverName(approver.getUsername())
//                            .approvalStatus(applFlowDetail.getAuditStatus())
//                            .build();
//
//                    return new ImmutablePair<>(VC.Service.OK, applItem);
//                }
                }
            }
            return new ImmutablePair<>(VC.Service.ERROR, null);
        }


        public Pair<VC.Service, CourseAppItemDetail> getDetail (@NonNull String flowNo){

            var vc_af = APPROVAL_FLOW_MAPPER.select(flowNo);

            if (vc_af.getLeft() == VC.Mapper.OK) {
                var approvalFlow = vc_af.getRight();
                var vc_addUser = USER_MAPPER.selectUserById(approvalFlow.getAddUserId());

                if (vc_addUser.getLeft() == VC.Mapper.OK) {

                    var addUser = vc_addUser.getRight();
                    var vc_afd = APPROVAL_FLOW_DETAIL_MAPPER.select(flowNo);

                    if (vc_afd.getLeft() == VC.Mapper.OK) {
                        var approFlowDetail = vc_afd.getRight();

                        var vc_ac = getAppliedCourses(flowNo);

                        if (vc_ac.getLeft() == VC.Service.OK) {
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

                            return new ImmutablePair<>(VC.Service.OK, result);
                        }
                    }
                }
            }
            return new ImmutablePair<>(VC.Service.ERROR, null);
        }


        private Pair<VC.Service, List<Course>> getAppliedCourses (@NonNull String flowNo){
            var vc_ca = COURSE_APPLICATION_MAPPER.selectByFlowNo(flowNo);
            if (vc_ca.getLeft() == VC.Mapper.OK) {
                var appl = vc_ca.getRight();
                var courseIds = CourseUtil.getCourseCodes(appl.getApproCourseIds());
                var vc_courses = COURSE_MAPPER.selectCourses(courseIds);
                if (vc_courses.getLeft() == VC.Mapper.OK) {
                    return new ImmutablePair<>(VC.Service.OK, vc_courses.getRight());
                }
            }
            return new ImmutablePair<>(VC.Service.ERROR, null);
        }

        /**
         * 审批人完成审批, 调用此方法
         *
         * @param flowNo   审批流编号
         * @param remark   同意原因或驳回原因
         * @param approSuc 是否同意
         * @return
         */
        public VC.Service saveApproResult (@NonNull String flowNo,
                @NonNull String remark,
        boolean approSuc){
            try {
                // 如果不同意审批
                if (!approSuc && remark.isBlank()) {
                    return VC.Service.REMARK_IS_BLANK;
                }

                // 更新 申请流程 表
                var status = approSuc ? ApprovalStatus.APPROVED : ApprovalStatus.REJECTED;
                APPROVAL_FLOW_MAPPER.updateApproStatus(flowNo, status);

                // 更新 申请流程细节 表
                var maxIdOfCourseApproFlow = COURSE_APPRO_FLOW_MAPPER.findMaxIdOfCourseApproFlow(flowNo);
                APPROVAL_FLOW_DETAIL_MAPPER.updateApproStatus(flowNo, maxIdOfCourseApproFlow, status);
                APPROVAL_FLOW_DETAIL_MAPPER.updateApproRemark(flowNo, maxIdOfCourseApproFlow, remark);

                return VC.Service.OK;

            } catch (SQLException e) {

                e.printStackTrace();
                return VC.Service.ERROR;

            }
        }

        /**
         * 调用该方法, 将会创建一个新的节点
         * 如果, 就不创建
         */
        public void func () {

        }

    }
