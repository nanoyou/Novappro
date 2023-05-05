package com.github.akagawatsurunaki.novappro.service.manage;

import cn.hutool.core.collection.CollUtil;
import com.github.akagawatsurunaki.novappro.mapper.ApprovalAuthorityMapper;
import com.github.akagawatsurunaki.novappro.mapper.CourseMapper;
import com.github.akagawatsurunaki.novappro.mapper.UserMapper;
import com.github.akagawatsurunaki.novappro.model.database.ApprovalAuthority;
import com.github.akagawatsurunaki.novappro.model.frontend.ApprovalAuthorityItem;
import com.github.akagawatsurunaki.novappro.model.frontend.ServiceMessage;
import com.github.akagawatsurunaki.novappro.util.MyDb;
import lombok.Getter;
import lombok.NonNull;
import lombok.val;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ApprovalAuthorityService {
    @Getter
    private static final ApprovalAuthorityService instance = new ApprovalAuthorityService();

    /**
     * 查询数据库，获取所有审批权限项 ApprovalAuthority，
     * 并将它们封装成 ApprovalAuthorityItem 对象的列表。
     *
     * @return 如果查询成功，该方法返回查询结果和 ServiceMessage 对象以表示处理状态。
     * <br/>如果审批权限(ApprovalAuthority)列表为空，则返回一个空列表和相应的消息。
     */
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
                    new ServiceMessage(ServiceMessage.Level.SUCCESS, "查询到审批权限关系有" + approvalAuthorities.size() + "条"),
                    result
            );
        }
    }

    /**
     * 更新审批权限列表，会先执行删除操作，再执行添加操作。
     *
     * @param approverIdAndCourseCodes 这个字符串数组中的每一项都是一个审批人 ID 和课程代码的组合，用“-”符号连接。例如，“20001111-A21292387489”。
     * @param approverIdParam          新增的审批人ID。
     * @param approWeightParam         新增的审批人拥有的权重值。
     * @param courseCodeParam          新增的审批人可以申请的课程。
     * @return 服务响应信息
     */
    public ServiceMessage update(String[] approverIdAndCourseCodes,
                                 String approverIdParam,
                                 String approWeightParam,
                                 String courseCodeParam
    ) {
        val strings = Arrays.stream(approverIdAndCourseCodes).toList();
        try {
            return update02(strings,
                    new ImmutableTriple<>(Integer.valueOf(approverIdParam),
                            Integer.valueOf(approWeightParam),
                            courseCodeParam));

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return update02(strings, null);
    }

    private ServiceMessage update02(@NonNull List<String> approverIdAndCourseCodes,
                                    Triple<Integer, Integer, String> approverId_appproWeight_courseCode) {
        try (var session = MyDb.use().openSession(true)) {
            val approvalAuthorityMapper = session.getMapper(ApprovalAuthorityMapper.class);
            val userMapper = session.getMapper(UserMapper.class);
            val courseMapper = session.getMapper(CourseMapper.class);

            boolean isDeleted;
            boolean isUpdated = false;

            val userId_courseCodes = parseUserIdAndCourseCodes(approverIdAndCourseCodes);

            if (userId_courseCodes == null) {
                return new ServiceMessage(ServiceMessage.Level.ERROR, "存在错误的用户ID或课程代码");
            }

            // 利用集合差集，求出需要删除的ApprovalAuthorities
            val allApprovalAuthorities = approvalAuthorityMapper.selectAll();
            val approvalAuthoritiesToBeRetained = getApprovalAuthoritiesByUserIdAndCourseCodes(userId_courseCodes);
            val approvalAuthoritiesToDelete = CollUtil.disjunction(allApprovalAuthorities,
                    approvalAuthoritiesToBeRetained);
            val pairs = approvalAuthoritiesToDelete.stream().map(aa ->
                    new ImmutablePair<>(aa.getUserId(), aa.getCourseCode())
            ).toList();

            isDeleted = deleteApprovalAuthorities(pairs);
            // 查看是否有添加的需求
            if (approverId_appproWeight_courseCode != null) {

                val approverId = approverId_appproWeight_courseCode.getLeft();
                val approWeight = approverId_appproWeight_courseCode.getMiddle();
                val courseCode = approverId_appproWeight_courseCode.getRight();

                if ((userMapper.selectById(approverId) != null) && (courseMapper.selectCourseByCode(courseCode) != null)) {

                    val approvalAuthority = ApprovalAuthority.builder()
                            .userId(approverId)
                            .approWeight(approWeight)
                            .courseCode(courseCode).build();
                    // 如果新增成功
                    isUpdated = addApprovalAuthority(approvalAuthority);
                }
            }
            if (!isDeleted) {
                return new ServiceMessage(ServiceMessage.Level.WARN, "添加成功，但删除失败。");
            }

            if (!isUpdated) {
                return new ServiceMessage(ServiceMessage.Level.WARN, "删除成功，但添加失败。");
            }

            return new ServiceMessage(ServiceMessage.Level.SUCCESS, "完成。");
        }
    }

    private List<ApprovalAuthority> getApprovalAuthoritiesByUserIdAndCourseCodes(@NonNull List<Pair<Integer, String>> userId_courseCodes) {
        try (var session = MyDb.use().openSession(true)) {
            val approvalAuthorityMapper = session.getMapper(ApprovalAuthorityMapper.class);
            List<ApprovalAuthority> result = new ArrayList<>();
            for (var userId_courseCode : userId_courseCodes) {
                val approvalAuthority = approvalAuthorityMapper.selectByUserIdAndCourseCode(
                        userId_courseCode.getLeft(), userId_courseCode.getRight());
                if (approvalAuthority == null) {
                    return null;
                }
                result.add(approvalAuthority);
            }
            return result;
        }
    }

    private List<Pair<Integer, String>> parseUserIdAndCourseCodes(@NonNull List<String> approverIdAndCourseCodes) {
        try {
            return approverIdAndCourseCodes.stream()
                    .map(s -> s.split("-", 2))
                    .filter(arr -> arr.length == 2)
                    .map(arr -> new ImmutablePair<>(Integer.valueOf(arr[0]), arr[1]))
                    .collect(Collectors.toList());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private boolean deleteApprovalAuthorities(List<ImmutablePair<Integer, String>> userId_courseCodes) {
        return userId_courseCodes.stream().allMatch(this::deleteApprovalAuthority);
    }

    private boolean deleteApprovalAuthority(Pair<Integer, String> userId_courseCode) {
        try (var session = MyDb.use().openSession(true)) {
            val approvalAuthorityMapper = session.getMapper(ApprovalAuthorityMapper.class);
            if (userId_courseCode.getLeft() == null || userId_courseCode.getRight() == null) {
                return false;
            }
            val rows = approvalAuthorityMapper.delete(userId_courseCode.getLeft(), userId_courseCode.getRight());
            if (rows == 1) {
                return true;
            }
        }
        return false;
    }

    private boolean addApprovalAuthority(@NonNull ApprovalAuthority approvalAuthority) {
        try (var session = MyDb.use().openSession(true)) {
            val approvalAuthorityMapper = session.getMapper(ApprovalAuthorityMapper.class);
            return approvalAuthorityMapper.insert(approvalAuthority) == 1;
        }
    }


}
