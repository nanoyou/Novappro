package com.github.akagawatsurunaki.novappro.service.appro;

import com.github.akagawatsurunaki.novappro.enumeration.ApprovalStatus;
import com.github.akagawatsurunaki.novappro.mapper.*;
import com.github.akagawatsurunaki.novappro.model.database.approval.ApprovalFlow;
import com.github.akagawatsurunaki.novappro.model.database.approval.ApprovalFlowDetail;
import com.github.akagawatsurunaki.novappro.model.database.course.Course;
import com.github.akagawatsurunaki.novappro.model.frontend.ApplItem;
import com.github.akagawatsurunaki.novappro.model.frontend.CourseAppItemDetail;
import com.github.akagawatsurunaki.novappro.model.frontend.ServiceMessage;
import com.github.akagawatsurunaki.novappro.util.CourseUtil;
import com.github.akagawatsurunaki.novappro.util.MyDb;
import lombok.Getter;
import lombok.NonNull;
import lombok.val;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.ibatis.session.SqlSession;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ApprovalService {

    @Getter
    private static final ApprovalService instance = new ApprovalService();

    /**
     * 根据审批人的ID获取对应的可审批对象
     *
     * @param approverId
     * @return 服务响应码,
     * @apiNote ApplItem是前端使用的实体类
     */
    public Pair<ServiceMessage, List<ApplItem>> getApplItems(@NonNull Integer approverId) {

        try (var session = MyDb.use().openSession(true)) {

            val approvalFlowDetailMapper = session.getMapper(ApprovalFlowDetailMapper.class);

            var flowNos = approvalFlowDetailMapper.selectFlowNoByApproverId(approverId);

            if (flowNos == null || flowNos.isEmpty()) {
                return new ImmutablePair<>(
                        new ServiceMessage(ServiceMessage.Level.INFO, "您没有需要审批的项目。"),
                        new ArrayList<>());
            }

            List<ApplItem> result = new ArrayList<>();

            for (String flowNo : flowNos) {

                val applItem = getApplItem(flowNo, approverId);

                // 如果ApplItem不存在则直接跳过
                if (applItem == null) {
                    continue;
                }

                // 如果审批流已经结束那么跳过
                if (isSpecifiedApprovalFlowEnded(flowNo)) {
                    continue;
                }

                result.add(applItem);
            }

            if (result.isEmpty()) {
                return new ImmutablePair<>(
                        new ServiceMessage(ServiceMessage.Level.INFO, "您没有需要审批的项目。"),
                        new ArrayList<>());
            }

            return new ImmutablePair<>(
                    new ServiceMessage(ServiceMessage.Level.SUCCESS, "您有" + result.size() + "个项目将要审批。"),
                    result);
        }
    }

    private ApplItem getApplItem(@NonNull String flowNo, @NonNull Integer approverId) {

        try (SqlSession session = MyDb.use().openSession(true)) {

            val userMapper = session.getMapper(UserMapper.class);
            val approvalFlowMapper = session.getMapper(ApprovalFlowMapper.class);

            val approver = userMapper.selectById(approverId);
            val currentApprovalFlowNode = getCurrentApprovalFlowNode(flowNo);
            val auditUserId = currentApprovalFlowNode.getAuditUserId();
            val approFlow = approvalFlowMapper.select(flowNo);
            val addUser = userMapper.selectById(approFlow.getAddUserId());
            val currentApprovalNode = getCurrentApprovalFlowNode(flowNo);

            if (Objects.equals(auditUserId, approverId)) {
                return ApplItem.builder()
                        .flowNo(approFlow.getFlowNo())
                        .title(approFlow.getTitle())
                        .applicantName(addUser.getUsername())
                        .addTime(approFlow.getAddTime())
                        .approverName(approver.getUsername())
                        .approvalStatus(currentApprovalNode.getAuditStatus())
                        .build();
            }

        }
        return null;
    }

    /**
     * 获取一个ApplItem对象
     */
    public Pair<ServiceMessage, Optional<ApplItem>> getApplItem(@Nullable String flowNo) {
        if (flowNo == null || flowNo.isBlank()) {
            return ImmutablePair.of(
                    ServiceMessage.of(ServiceMessage.Level.WARN, "流水号不能为空"),
                    Optional.empty()
            );
        }

        return _getApplItem(flowNo);
    }

    private Pair<ServiceMessage, Optional<ApplItem>> _getApplItem(@NonNull String flowNo) {

        try (SqlSession session = MyDb.use().openSession(true)) {

            val userMapper = session.getMapper(UserMapper.class);
            val approvalFlowMapper = session.getMapper(ApprovalFlowMapper.class);

            var approFlow = approvalFlowMapper.select(flowNo);

            if (approFlow == null) {
                return new ImmutablePair<>(
                        ServiceMessage.of(ServiceMessage.Level.ERROR, "流水号为" + flowNo + "的申请流不存在"),
                        Optional.empty()
                );
            }

            var currentApprovalNode = getCurrentApprovalFlowNode(flowNo);

            if (currentApprovalNode == null) {
                return new ImmutablePair<>(
                        ServiceMessage.of(ServiceMessage.Level.FATAL, "当前的申请节点不存在，可能是申请流没有指定的审批人。"),
                        Optional.empty()
                );
            }

            var addUser = userMapper.selectById(approFlow.getAddUserId());
            assert addUser != null;

            var approver = userMapper.selectById(currentApprovalNode.getAuditUserId());
            assert approver != null;

            var applItem = ApplItem.builder()
                    .flowNo(approFlow.getFlowNo())
                    .title(approFlow.getTitle())
                    .applicantName(addUser.getUsername())
                    .addTime(approFlow.getAddTime())
                    .approverName(approver.getUsername())
                    .approvalStatus(currentApprovalNode.getAuditStatus())
                    .build();

            return new ImmutablePair<>(
                    ServiceMessage.of(ServiceMessage.Level.SUCCESS, "服务成功"),
                    Optional.of(applItem)
            );
        }
    }

    /**
     * 根据流水号获取课程申请明细(CourseAppItemDetail)
     *
     * @param flowNo 指定的流水号
     * @return 服务响应码, 课程申请明细(可能有多个?)
     */
    public Pair<ServiceMessage, Optional<CourseAppItemDetail>> getCourseAppItemDetail(
            @Nullable String flowNo,
            @Nullable Integer approverId) {
        if (flowNo == null || flowNo.isBlank()) {
            return ImmutablePair.of(
                    ServiceMessage.of(ServiceMessage.Level.WARN, "流水号不能为空"),
                    Optional.empty()
            );
        }
        if (approverId == null) {
            return ImmutablePair.of(
                    ServiceMessage.of(ServiceMessage.Level.WARN, "审批人的用户ID不能为空"),
                    Optional.empty()
            );
        }

        return _getDetail(flowNo, approverId);
    }

    private Pair<ServiceMessage, Optional<CourseAppItemDetail>> _getDetail(
            @NonNull String flowNo,
            @NonNull Integer approverId) {

        try (SqlSession session = MyDb.use().openSession(true)) {
            val approvalFlowMapper = session.getMapper(ApprovalFlowMapper.class);
            val userMapper = session.getMapper(UserMapper.class);
            val approvalFlowDetailMapper = session.getMapper(ApprovalFlowDetailMapper.class);

            val approvalFlow = approvalFlowMapper.select(flowNo);

            if (approvalFlow == null) {
                return ImmutablePair.of(
                        ServiceMessage.of(ServiceMessage.Level.ERROR, "流水号为" + flowNo + "的审批流对象(ApprovalFlow)不存在"),
                        Optional.empty()
                );
            }

            val addUser = userMapper.selectById(approvalFlow.getAddUserId());
            assert addUser != null;

            var approFlowDetail = approvalFlowDetailMapper.select(flowNo, approverId);
            assert approFlowDetail != null;

            var appliedCourses = getAppliedCourses(flowNo);
            assert appliedCourses != null && !appliedCourses.isEmpty();

            var result = CourseAppItemDetail.builder()
                    .flowNo(flowNo)
                    .title(approvalFlow.getTitle())
                    .applicantId(addUser.getId())
                    .applicantName(addUser.getUsername())
                    .addTime(approvalFlow.getAddTime())
                    .approStatus(approFlowDetail.getAuditStatus())
                    .applCourses(appliedCourses)
                    .build();

            return ImmutablePair.of(
                    ServiceMessage.of(ServiceMessage.Level.SUCCESS, "服务成功"),
                    Optional.of(result)
            );
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

        try (var session = MyDb.use().openSession(true)) {
            val courseApplicationMapper = session.getMapper(CourseApplicationMapper.class);
            val courseMapper = session.getMapper(CourseMapper.class);

            var appl = courseApplicationMapper.selectByFlowNo(flowNo);

            if (appl != null) {
                assert appl.getApproCourses() != null;
                var courseIds = CourseUtil.getCourseCodes(appl.getApproCourses());
                return courseMapper.selectCourses(courseIds);
            }
            return null;
        }
    }

    /**
     * 审批人完成审批, 调用此方法
     *
     * @param flowNo   审批流编号
     * @param remark   同意原因或驳回原因
     * @param approSuc 是否同意
     * @return 注意CourseAppItemDetail始终为空
     */
    public Pair<ServiceMessage, Optional<CourseAppItemDetail>> saveApproResult(
            @Nullable String flowNo,
            @Nullable String remark,
            @Nullable String approSuc
    ) {
        if (flowNo == null) {
            return ImmutablePair.of(
                    ServiceMessage.of(ServiceMessage.Level.WARN, "流水号不能为空"),
                    Optional.empty()
            );
        }

        boolean flag;

        if (approSuc == null || approSuc.isBlank()) {
            return ImmutablePair.of(
                    ServiceMessage.of(ServiceMessage.Level.WARN, "无操作"),
                    Optional.empty()
            );
        } else if (approSuc.equals("驳回审批")) {
            flag = false;
        } else if (approSuc.equals("同意审批")) {
            flag = true;
        } else {
            return ImmutablePair.of(
                    ServiceMessage.of(ServiceMessage.Level.WARN, "非法操作"),
                    Optional.empty()
            );
        }

        if (remark == null) {
            remark = "";
        }

        if (remark.length() > 500) {
            return ImmutablePair.of(
                    ServiceMessage.of(ServiceMessage.Level.WARN, "备注最大字数为500个字符"),
                    Optional.empty()
            );
        }

        return _saveApproResult(flowNo, remark, flag);

    }


    public Pair<ServiceMessage, Optional<CourseAppItemDetail>> _saveApproResult(
            @NonNull String flowNo,
            @NonNull String remark,
            boolean approSuc) {
        try (SqlSession session = MyDb.use().openSession(true)) {
            val approvalFlowMapper = session.getMapper(ApprovalFlowMapper.class);
            val approvalFlowDetailMapper = session.getMapper(ApprovalFlowDetailMapper.class);

            // 如果不同意审批
            // 审批流程下不能为空
            if (!approSuc && remark.isBlank()) {
                return ImmutablePair.of(
                        ServiceMessage.of(ServiceMessage.Level.ERROR, "若驳回审批，必须填写备注"),
                        Optional.empty()
                );
            }

            // 更新 申请流程 表: 状态修改
            val status = approSuc ? ApprovalStatus.APPROVED : ApprovalStatus.REJECTED;
            if (approvalFlowMapper.updateApproStatus(flowNo, status) != 1) {
                return ImmutablePair.of(
                        ServiceMessage.of(ServiceMessage.Level.FATAL, "更新审批流失败"),
                        Optional.empty()
                );
            }

            // 更新 申请流程细节 表
            val node = getCurrentApprovalFlowNode(flowNo);
            if (node == null) {
                return ImmutablePair.of(
                        ServiceMessage.of(ServiceMessage.Level.FATAL, "申请流程明细表不存在"),
                        Optional.empty()
                );
            }
            assert node.getId() != null;

            if (approvalFlowDetailMapper.updateApproStatus(flowNo, node.getId(), status) != 1) {
                return ImmutablePair.of(
                        ServiceMessage.of(ServiceMessage.Level.FATAL, "申请流程明细表更新审批状态失败"),
                        Optional.empty()
                );
            }

            if (approvalFlowDetailMapper.updateApproRemark(flowNo, node.getId(), remark) != 1) {
                return ImmutablePair.of(
                        ServiceMessage.of(ServiceMessage.Level.FATAL, "申请流程明细表更新备注失败"),
                        Optional.empty()
                );
            }

            return ImmutablePair.of(
                    ServiceMessage.of(ServiceMessage.Level.SUCCESS, "服务成功"),
                    Optional.empty()
            );
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

    /**
     * 判断当前的审批流是否已经结束
     * 当一个审批流中存在被拒绝的节点，或者一个审批流中所有的节点都审批通过，那么这个审批流将被断定为结束的。
     *
     * @param flowNo 指定的审批流流水号，一个审批流可能有多个审批明细。
     * @return true 当审批流结束； false 当审批流未结束。
     */
    public boolean isSpecifiedApprovalFlowEnded(@NonNull String flowNo) {
        try (var session = MyDb.use().openSession(true)) {
            val approvalFlowDetailMapper = session.getMapper(ApprovalFlowDetailMapper.class);

            val approvalFlowDetails = approvalFlowDetailMapper.selectByFlowNo(flowNo);
            assert approvalFlowDetails != null && !approvalFlowDetails.isEmpty();

            // 如果有人拒绝了，那么整个审批流将被关闭（也算是完成）
            val isRejected = approvalFlowDetails.stream()
                    .anyMatch(detail -> detail.getAuditStatus() == ApprovalStatus.REJECTED);
            // 如果所有人都同意了，那么整个审批流将被完成
            val isAllApproved = approvalFlowDetails.stream()
                    .allMatch(detail -> detail.getAuditStatus() == ApprovalStatus.APPROVED);

            return isRejected || isAllApproved;
        }

    }

    public Pair<ServiceMessage, List<ApprovalFlowDetail>> getApprovalFlowDetails(@Nullable String flowNo) {
        try (var session = MyDb.use().openSession(true)) {
            val approvalFlowDetailMapper = session.getMapper(ApprovalFlowDetailMapper.class);

            if (flowNo == null || flowNo.isBlank()) {
                return ImmutablePair.of(
                        ServiceMessage.of(ServiceMessage.Level.WARN, "流水号不能为空"),
                        new ArrayList<>()
                );
            }

            val approvalFlowDetails = approvalFlowDetailMapper.selectByFlowNo(flowNo);

            if (approvalFlowDetails.isEmpty()) {
                return ImmutablePair.of(
                        ServiceMessage.of(ServiceMessage.Level.INFO, "无审批明细"),
                        approvalFlowDetails
                );
            }

            return ImmutablePair.of(
                    ServiceMessage.of(ServiceMessage.Level.SUCCESS, "共有" + approvalFlowDetails.size() + "个审批明细"),
                    approvalFlowDetails
            );

        }
    }

    /**
     * 从数据库中获取所有的申请流对象
     *
     * @return 所有的申请流对象
     */
    public Pair<ServiceMessage, List<ApprovalFlow>> getAllApprovalFlows() {
        try (var session = MyDb.use().openSession(true)) {
            val approvalFlowMapper = session.getMapper(ApprovalFlowMapper.class);
            val approvalFlows = approvalFlowMapper.selectAll();

            if (approvalFlows == null || approvalFlows.isEmpty()) {
                return ImmutablePair.of(
                        ServiceMessage.of(ServiceMessage.Level.INFO, "没有申请流可以被查询"),
                        new ArrayList<>()
                );
            }

            return ImmutablePair.of(
                    ServiceMessage.of(ServiceMessage.Level.SUCCESS, "查询到" + approvalFlows.size() + "条申请流对象"),
                    approvalFlows
            );
        }
    }
}
