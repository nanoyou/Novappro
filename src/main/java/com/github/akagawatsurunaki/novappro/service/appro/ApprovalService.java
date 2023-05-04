package com.github.akagawatsurunaki.novappro.service.appro;

import com.github.akagawatsurunaki.novappro.constant.VC;
import com.github.akagawatsurunaki.novappro.enumeration.ApprovalStatus;
import com.github.akagawatsurunaki.novappro.mapper.*;
import com.github.akagawatsurunaki.novappro.mapper.impl.CourseApplicationMapperImpl;
import com.github.akagawatsurunaki.novappro.model.database.User;
import com.github.akagawatsurunaki.novappro.model.database.approval.ApprovalFlowDetail;
import com.github.akagawatsurunaki.novappro.model.database.course.Course;
import com.github.akagawatsurunaki.novappro.model.frontend.ApplItem;
import com.github.akagawatsurunaki.novappro.model.frontend.CourseAppItemDetail;
import com.github.akagawatsurunaki.novappro.util.CourseUtil;
import com.github.akagawatsurunaki.novappro.util.MyDb;
import lombok.Getter;
import lombok.NonNull;
import lombok.val;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.ibatis.session.SqlSession;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ApprovalService {

    @Getter
    private static final ApprovalService instance = new ApprovalService();

    private static final CourseApplicationMapper COURSE_APPLICATION_MAPPER = CourseApplicationMapperImpl.getInstance();

    static List<User> approvers = new ArrayList<>();

    static {
        try (var session = MyDb.use().openSession(true)) {
            val userMapper = session.getMapper(UserMapper.class);
            // TODO: 查询一个固定的管理员
            val approver1 = userMapper.selectById(20210000);
            val approver2 = userMapper.selectById(20210000);
            approvers.add(approver1);
            approvers.add(approver2);
        }
    }

    /**
     * 根据审批人的ID获取对应的可审批对象
     *
     * @param approverId
     * @return 服务响应码,
     * @apiNote ApplItem是前端使用的实体类
     */
    public Pair<VC.Service, List<ApplItem>> getApplItems(@NonNull Integer approverId) {

        // 判断用户的类型
        // 获取审批人的权重
        // 构建审批人链条, 按照权重排序
        // 如果链条为空直接返回空

        // 如果当前的ApplItem是第一个节点, 直接返回

        // 如果当前的ApplItem还有前继节点, 检查它的status是否为(已通过or已拒绝)
        //  如果(已拒绝) 则不显示
        //  如果(已通过) 则可以开始审批
        //  如果(审批|审批|审批...) 则不显示

        try (var session = MyDb.use().openSession(true)) {

            val approvalFlowDetailMapper = session.getMapper(ApprovalFlowDetailMapper.class);

            var flowNos = approvalFlowDetailMapper.selectFlowNoByApproverId(approverId);

            if (flowNos != null && (!flowNos.isEmpty())) {

                List<ApplItem> result = new ArrayList<>();

                for (String flowNo : flowNos) {
                    var vc_applItem = getApplItem(flowNo);
                    if (vc_applItem.getLeft() != VC.Service.OK) {
                        return new ImmutablePair<>(VC.Service.ERROR, null);
                    }
                    result.add(vc_applItem.getRight());
                }
                return new ImmutablePair<>(VC.Service.OK, result);
            }
        }
        return new ImmutablePair<>(VC.Service.ERROR, null);
    }

    /**
     * 获取一个ApplItem对象
     */
    public Pair<VC.Service, ApplItem> getApplItem(@NonNull String flowNo) {

        try (SqlSession session = MyDb.use().openSession(true)) {

            val userMapper = session.getMapper(UserMapper.class);
            val approvalFlowMapper = session.getMapper(ApprovalFlowMapper.class);

            var approFlow = approvalFlowMapper.select(flowNo);

            if (approFlow == null) {
                return new ImmutablePair<>(VC.Service.NO_SUCH_ENTITY, null);
            }

            var currentApprovalNode = getCurrentApprovalFlowNode(flowNo);

            if (currentApprovalNode != null) {
                var addUser = userMapper.selectById(approFlow.getAddUserId());
                var approver = userMapper.selectById(currentApprovalNode.getAuditUserId());

                if (addUser != null && approver != null) {
                    var applItem = ApplItem.builder()
                            .flowNo(approFlow.getFlowNo())
                            .title(approFlow.getTitle())
                            .applicantName(addUser.getUsername())
                            .addTime(approFlow.getAddTime())
                            .approverName(approver.getUsername())
                            .approvalStatus(currentApprovalNode.getAuditStatus())
                            .build();

                    return new ImmutablePair<>(VC.Service.OK, applItem);
                }
            }
        }
        return new ImmutablePair<>(VC.Service.ERROR, null);
    }

    /**
     * 根据流水号获取课程申请明细(CourseAppItemDetail)
     *
     * @param flowNo 指定的流水号
     * @return 服务响应码, 课程申请明细(可能有多个?)
     */
    public Pair<VC.Service, CourseAppItemDetail> getDetail(@NonNull String flowNo, @NonNull Integer approverId) {

        try (SqlSession session = MyDb.use().openSession(true)) {
            var approvalFlowMapper = session.getMapper(ApprovalFlowMapper.class);
            var userMapper = session.getMapper(UserMapper.class);
            val approvalFlowDetailMapper = session.getMapper(ApprovalFlowDetailMapper.class);

            var approvalFlow = approvalFlowMapper.select(flowNo);

            if (approvalFlow != null) {

                var addUser = userMapper.selectById(approvalFlow.getAddUserId());

                if (addUser != null) {

                    var approFlowDetail = approvalFlowDetailMapper.select(flowNo, approverId);

                    if (approFlowDetail != null) {

                        var appliedCourses = getAppliedCourses(flowNo);

                        if (appliedCourses != null) {

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
    }

    /**
     * 按照指定的流水号查询课程, 即查询一个课程申请(ApprovalFlow)内包含的所有课程(可能有0, 1, 或更多)
     *
     * @param flowNo 指定了ApprovalFlow的流水号
     * @return 服务响应码, 查询到的所有课程
     * @implNote 该方法从MySQL数据库中抽取crs_appro? 数据, 按照其主键查询
     */
    private List<Course> getAppliedCourses(@NonNull String flowNo) {
        var vc_ca = COURSE_APPLICATION_MAPPER.selectByFlowNo(flowNo);
        if (vc_ca.getLeft() == VC.Mapper.OK) {
            var appl = vc_ca.getRight();
            var courseIds = CourseUtil.getCourseCodes(appl.getApproCourseIds());

            try (SqlSession session = MyDb.use().openSession(true)) {

                var courseMapper = session.getMapper(CourseMapper.class);
                return courseMapper.selectCourses(courseIds);
            }
        }
        return null;
    }

    /**
     * 审批人完成审批, 调用此方法
     *
     * @param flowNo   审批流编号
     * @param remark   同意原因或驳回原因
     * @param approSuc 是否同意
     * @return
     */
    public VC.Service saveApproResult(@NonNull String flowNo,
                                      @NonNull String remark,
                                      boolean approSuc) {
        try (SqlSession session = MyDb.use().openSession(true)) {
            var approvalFlowMapper = session.getMapper(ApprovalFlowMapper.class);
            val approvalFlowDetailMapper = session.getMapper(ApprovalFlowDetailMapper.class);

            // 如果不同意审批
            if (!approSuc && remark.isBlank()) {
                return VC.Service.REMARK_IS_BLANK;
            }

            // 更新 申请流程 表
            var status = approSuc ? ApprovalStatus.APPROVED : ApprovalStatus.REJECTED;
            var rows = approvalFlowMapper.updateApproStatus(flowNo, status);

            if (rows != 1) {
                return VC.Service.FAILED_UPDATE;
            }

            // 更新 申请流程细节 表

            var node = getCurrentApprovalFlowNode(flowNo);
            if (node == null) {
                return VC.Service.ERROR;
            }
            var maxIdOfCourseApproFlow = node.getId();

            approvalFlowDetailMapper.updateApproStatus(flowNo, maxIdOfCourseApproFlow, status);
            approvalFlowDetailMapper.updateApproRemark(flowNo, maxIdOfCourseApproFlow, remark);

            return VC.Service.OK;

        } catch (SQLException e) {

            e.printStackTrace();
            return VC.Service.ERROR;

        }
    }

    /**
     * 获取当前审批节点
     *
     * @param flowNo
     * @return 如果申请已经结束将会返回最后一个审批通过的节点; <br>如果其中有至少一个节点被驳回, 那么返回第一个被驳回的节点; <br>否则返回第一个既不是被驳回, 也不是被同意的节点.
     */
    public ApprovalFlowDetail getCurrentApprovalFlowNode(@NonNull String flowNo) {

        try (var session = MyDb.use().openSession(true)) {
            val approvalFlowDetailMapper = session.getMapper(ApprovalFlowDetailMapper.class);
            var approvalFlowDetails = approvalFlowDetailMapper.selectByFlowNo(flowNo);

            if (!approvalFlowDetails.isEmpty()) {

                val lastDetail = approvalFlowDetails.get(approvalFlowDetails.size() - 1);
                if (lastDetail.getAuditStatus() == ApprovalStatus.APPROVED) {
                    return lastDetail;
                }

                return approvalFlowDetails.stream()
                        .filter(detail -> detail.getAuditStatus() == ApprovalStatus.REJECTED ||
                                (detail.getAuditStatus() != ApprovalStatus.APPROVED && detail.getAuditStatus() != ApprovalStatus.REJECTED))
                        .findFirst()
                        .orElse(null);
            }
        }
        return null;
    }

}
