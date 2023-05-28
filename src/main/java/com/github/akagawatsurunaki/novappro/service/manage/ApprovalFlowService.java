package com.github.akagawatsurunaki.novappro.service.manage;

import com.github.akagawatsurunaki.novappro.enumeration.ApprovalStatus;
import com.github.akagawatsurunaki.novappro.enumeration.BusType;
import com.github.akagawatsurunaki.novappro.mapper.ApprovalFlowDetailMapper;
import com.github.akagawatsurunaki.novappro.mapper.ApprovalFlowMapper;
import com.github.akagawatsurunaki.novappro.mapper.CourseApplicationMapper;
import com.github.akagawatsurunaki.novappro.mapper.UserMapper;
import com.github.akagawatsurunaki.novappro.model.database.approval.ApprovalFlow;
import com.github.akagawatsurunaki.novappro.model.database.approval.ApprovalFlowDetail;
import com.github.akagawatsurunaki.novappro.model.database.approval.CourseApplication;
import com.github.akagawatsurunaki.novappro.model.frontend.ServiceMessage;
import com.github.akagawatsurunaki.novappro.util.CourseUtil;
import com.github.akagawatsurunaki.novappro.util.IdUtil;
import com.github.akagawatsurunaki.novappro.util.MyDb;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.val;
import org.apache.ibatis.session.SqlSession;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
public class ApprovalFlowService {

    @Getter
    private static final ApprovalFlowService instance = new ApprovalFlowService();

    public ServiceMessage apply(@Nullable Integer userId,
                                @Nullable String approverId,
                                @Nullable String courseId) {

        try {
            if (userId == null) {
                return ServiceMessage.of(ServiceMessage.Level.WARN, "用户ID不能为空");
            }
            if (courseId == null) {
                return ServiceMessage.of(ServiceMessage.Level.WARN, "课程代码不能为空");
            }
            if (approverId == null) {
                return ServiceMessage.of(ServiceMessage.Level.WARN, "审批人ID不能为空");
            }
            return _apply(userId, Integer.valueOf(approverId), courseId);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return ServiceMessage.of(ServiceMessage.Level.ERROR, "非法的用户ID");
        }

    }


    private ServiceMessage _apply(@NonNull Integer userId, @NonNull Integer approverId, @NonNull String courseId) {

        try (SqlSession session = MyDb.use().openSession(true)) {

            val userMapper = session.getMapper(UserMapper.class);
            val approvalFlowMapper = session.getMapper(ApprovalFlowMapper.class);
            val courseApplicationMapper = session.getMapper(CourseApplicationMapper.class);
            val approvalFlowDetailMapper = session.getMapper(ApprovalFlowDetailMapper.class);

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

            List<String> courseIds = new ArrayList<>();
            courseIds.add(courseId);

            // 创建Approval对象
            var courseApplication
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
                    .remark("")
                    .build();

            courseApplicationMapper.insert(courseApplication);
            approvalFlowMapper.insert(approvalFlow);


            val approver = userMapper.selectById(approverId);
            if (approver == null) {
                return ServiceMessage.of(ServiceMessage.Level.ERROR, "审批人不存在");
            }

            val approvalFlowDetail = ApprovalFlowDetail.builder()
                    .auditRemark("")
                    .auditTime(date)
                    .auditStatus(
                            switch (approver.getType()) {
                                case LECTURE_TEACHER -> ApprovalStatus.LECTURE_TEACHER_EXAMING;
                                case SUPERVISOR_TEACHER -> ApprovalStatus.SUPERVISOR_TEACHER_EXAMING;
                                default -> throw new IllegalStateException("Unexpected value: " + approver.getType());
                            }
                    )
                    .auditUserId(approverId)
                    .flowNo(flowNo)
                    .build();

            approvalFlowDetailMapper.insert(approvalFlowDetail);

            return ServiceMessage.of(
                    ServiceMessage.Level.SUCCESS,
                    "创建审批流成功！"
            );
        } catch (Exception e) {
            return ServiceMessage.of(
                    ServiceMessage.Level.FATAL,
                    "服务失败。"
            );
        }
    }
}
