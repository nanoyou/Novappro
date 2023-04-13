package com.github.akagawatsurunaki.novappro.service;

import com.github.akagawatsurunaki.novappro.constant.PrefixConstant;
import com.github.akagawatsurunaki.novappro.mapper.ApplicationEntityMapper;
import com.github.akagawatsurunaki.novappro.mapper.CourseApplicationMapper;
import com.github.akagawatsurunaki.novappro.mapper.CourseMapper;
import com.github.akagawatsurunaki.novappro.mapper.UserMapper;
import com.github.akagawatsurunaki.novappro.mapper.impl.ApplicationEntityMapperImpl;
import com.github.akagawatsurunaki.novappro.mapper.impl.CourseApplicationMapperImpl;
import com.github.akagawatsurunaki.novappro.mapper.impl.CourseMapperImpl;
import com.github.akagawatsurunaki.novappro.mapper.impl.UserMapperImpl;
import com.github.akagawatsurunaki.novappro.model.User;
import com.github.akagawatsurunaki.novappro.model.approval.ApplicationEntity;
import com.github.akagawatsurunaki.novappro.model.approval.ApprovalProcessNode;
import com.github.akagawatsurunaki.novappro.model.approval.ApprovalProcessQueue;
import com.github.akagawatsurunaki.novappro.model.approval.CourseApplication;
import com.github.akagawatsurunaki.novappro.model.course.Course;
import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

import static com.github.akagawatsurunaki.novappro.service.CourseApplyService.VerifyCode.*;

public class CourseApplyService {

    @Getter
    private static final CourseApplyService instance = new CourseApplyService();
    private static final UserMapper USER_MAPPER = UserMapperImpl.getInstance();

    private static final CourseApplicationMapper COURSE_APPLICATION_MAPPER = new CourseApplicationMapperImpl();

    private static final ApplicationEntityMapper APPLICATION_ENTITY_MAPPER = new ApplicationEntityMapperImpl();
    private static final CourseService COURSE_SERVICE = CourseService.getInstance();

    public Pair<VerifyCode, ApprovalProcessQueue> applyCourses(
            @NonNull Integer applicantId,
            @NonNull List<String> selectedCourseCodeList
    ) {

        User applicant = USER_MAPPER.getUserById(applicantId);
        if (applicant == null) {
            return new ImmutablePair<>(NO_SUCH_APPLICANT, null);
        }

        // 查询谁能审批
        List<User> approvers = USER_MAPPER.selectUserByApplicationTypeOfAuthority(ApplicationEntity.ApplicationType.COURSE);

        // 校验申请的课程
        Pair<CourseService.VerifyCode, List<Course>> pair = COURSE_SERVICE.getCoursesByCodes(selectedCourseCodeList);
        CourseService.VerifyCode verifyCode = pair.getLeft();
        List<Course> courses = pair.getRight();
        // 如果申请人存在, 审批人存在, 课程校验成功
        if (verifyCode == CourseService.VerifyCode.SERVICE_OK) {
            // 初始化一个流程
            ApprovalProcessQueue queue = initCourseApprovalProcess(applicant, courses, approvers);
            return new ImmutablePair<>(SERVICE_OK, queue);
        }
        return new ImmutablePair<>(NO_SUCH_COURSE, null);
    }

    private ApprovalProcessQueue initCourseApprovalProcess(
            @NonNull User applicant,
            @NonNull List<Course> selectedCourseCodeList,
            @NonNull List<User> approvers) {

        // CourseApplication对象赋值
        CourseApplication courseApplication = new CourseApplication();
        courseApplication.setCode(generateApplicationEntityCode(applicant.getId()));
        courseApplication.setApplicantId(applicant.getId());
        courseApplication.setAppliedCourses(selectedCourseCodeList);
        courseApplication.setApplicationType(ApplicationEntity.ApplicationType.COURSE);

        // 创建结点
        var node = createApprovalProcessNode(applicant, approvers, ApprovalProcessNode.Type.ANY_ONE, courseApplication);

        if (node != null) {

            var queue = createApprovalProcessQueue(node);

            // 尝试向数据库插入课程申请
            Pair<CourseApplicationMapper.VerifyCode, CourseApplication> vcaPair = COURSE_APPLICATION_MAPPER.insertCourseApplication(courseApplication);

            if (vcaPair.getLeft() == CourseApplicationMapper.VerifyCode.MAPPER_OK) {
                // 尝试向数据库插入申请实体
                var vaePair = APPLICATION_ENTITY_MAPPER.insertApplicationEntity(((ApplicationEntity) courseApplication));
                if (vaePair.getLeft() == ApplicationEntityMapper.VerifyCode.MAPPER_OK) {
                    return queue;
                }
            }
        }

        return null;

    }

    /**
     * 将一个节点加入到申请流程中
     *
     * @param node
     * @return
     */
    public ApprovalProcessQueue createApprovalProcessQueue(
            @NonNull ApprovalProcessNode node) {
        ApprovalProcessQueue processQueue = new ApprovalProcessQueue();
        processQueue.setCode(generateApprovalProcessQueueCode(node.getApplicationEntity().getApplicantId()));
        processQueue.setQueue(new ArrayList<ApprovalProcessNode>());
        processQueue.getQueue().add(node);
        processQueue.setCurrentNodeCode(node.getCode());
        return processQueue;
    }

    /**
     * 提交给指定的申请人
     */
    private ApprovalProcessNode createApprovalProcessNode(
            @NonNull User applicant,
            @NonNull List<User> approvers,
            @NonNull ApprovalProcessNode.Type processType,
            @NonNull ApplicationEntity entity
    ) {
        // 赋值
        ApprovalProcessNode node = new ApprovalProcessNode();

        node.setCode(generateApprovalProcessNodeCode(applicant.getId()));
        node.setApprovers(approvers);

        if (processType == ApprovalProcessNode.Type.NO_ONE) {
            // 无效申请
            return null;
        }

        node.setProcessType(processType);
        node.setCurrentStatus(ApprovalProcessNode.Status.WAIT_FOR_APPROVAL);
        node.setApplicationEntity(entity);


        return node;
    }

    private String generateApplicationEntityCode(Integer userId) {
        String timeStr = String.valueOf(System.currentTimeMillis());
        return PrefixConstant.APPLICATION_ENTITY_CODE + String.valueOf(userId) + timeStr;
    }

    private String generateApprovalProcessNodeCode(Integer userId) {
        String timeStr = String.valueOf(System.currentTimeMillis());
        return PrefixConstant.APPROVAL_PROCESS_NODE_CODE + String.valueOf(userId) + timeStr;
    }

    private String generateApprovalProcessQueueCode(Integer userId) {
        String timeStr = String.valueOf(System.currentTimeMillis());
        return PrefixConstant.APPROVAL_PROCESS_QUEUE_CODE + String.valueOf(userId) + timeStr;
    }


    public enum VerifyCode {
        SERVICE_OK,
        MEANINGLESS_PROCESS_TYPE,

        UNKNOWN_SERVICE_ERROR,
        NO_SUCH_APPLICANT,
        NO_SUCH_APPROVER,
        NO_SUCH_COURSE,

    }

}
