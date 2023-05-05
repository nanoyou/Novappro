package com.github.akagawatsurunaki.novappro.service.manage;

import com.github.akagawatsurunaki.novappro.mapper.ApprovalAuthorityMapper;
import com.github.akagawatsurunaki.novappro.mapper.CourseMapper;
import com.github.akagawatsurunaki.novappro.mapper.UserMapper;
import com.github.akagawatsurunaki.novappro.model.frontend.ApprovalAuthorityItem;
import com.github.akagawatsurunaki.novappro.model.frontend.ServiceMessage;
import com.github.akagawatsurunaki.novappro.util.MyDb;
import lombok.Getter;
import lombok.val;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class ApprovalAuthorityService {
    @Getter
    private static final ApprovalAuthorityService instance = new ApprovalAuthorityService();

    public Pair<ServiceMessage, List<ApprovalAuthorityItem>> getApprovalAuthorityItems() {

        try (var session = MyDb.use().openSession(true)) {
            val approvalAuthorityMapper = session.getMapper(ApprovalAuthorityMapper.class);
            val userMapper = session.getMapper(UserMapper.class);
            val courseMapper = session.getMapper(CourseMapper.class);

            val approvalAuthorities = approvalAuthorityMapper.selectAll();

            if (approvalAuthorities == null || approvalAuthorities.isEmpty()) {
                return new ImmutablePair<>(
                        new ServiceMessage(ServiceMessage.Level.INFO, "审批权限列表是空的"),
                        new ArrayList<>()
                );
            }

            List<ApprovalAuthorityItem> result = new ArrayList<>();

            approvalAuthorities.forEach(approvalAuthority -> {

                val approver = userMapper.selectById(approvalAuthority.getUserId());
                val course = courseMapper.selectCourseByCode(approvalAuthority.getCourseCode());

                val approvalAuthorityItem = ApprovalAuthorityItem.builder()
                        .approverId(approver.getId())
                        .approverName(approver.getUsername())
                        .userType(approver.getType())
                        .approWeight(approvalAuthority.getApproWeight())
                        .courseCode(course.getCode())
                        .courseName(course.getName()).build();

                result.add(approvalAuthorityItem);

            });


            return new ImmutablePair<>(
                    new ServiceMessage(ServiceMessage.Level.SUCCESS, "查询到审批权限关系有"+ approvalAuthorities.size() +"条"),
                    result
            );
        }
    }
}
