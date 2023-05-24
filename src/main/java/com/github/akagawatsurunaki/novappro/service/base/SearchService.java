package com.github.akagawatsurunaki.novappro.service.base;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson2.JSON;
import com.github.akagawatsurunaki.novappro.mapper.ApprovalFlowMapper;
import com.github.akagawatsurunaki.novappro.mapper.UserMapper;
import com.github.akagawatsurunaki.novappro.model.database.approval.ApprovalFlow;
import com.github.akagawatsurunaki.novappro.model.frontend.ServiceMessage;
import com.github.akagawatsurunaki.novappro.util.MyDb;
import lombok.Getter;
import lombok.NonNull;
import lombok.val;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchService {

    @Getter
    private static final SearchService instance = new SearchService();

    private static final int offset = 2;

    public Pair<ServiceMessage, List<ApprovalFlow>> getPageAsStudentView(@Nullable String pageParam, @Nullable String search, @Nullable String userIdParam) {

        try (var session = MyDb.use().openSession(true)) {
            val userMapper = session.getMapper(UserMapper.class);
            val listPair = getPageAsTeacherView(pageParam, search);

            if (userIdParam == null || userIdParam.isBlank()) {
                return ImmutablePair.of(
                        ServiceMessage.of(ServiceMessage.Level.WARN, "用户ID不能为空"),
                        new ArrayList<>()
                );
            }

            val userId = Integer.parseInt(userIdParam);

            if (listPair.getLeft().getMessageLevel().equals(ServiceMessage.Level.SUCCESS)) {
                val approvalFlows = listPair.getRight();
                val result = approvalFlows.stream().filter(
                        approvalFlow -> approvalFlow.getAddUserId() == userId
                ).toList();
                return ImmutablePair.of(
                        ServiceMessage.of(ServiceMessage.Level.SUCCESS, "查询到" + result.size() + "个结果"),
                        result
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ImmutablePair.of(
                ServiceMessage.of(ServiceMessage.Level.FATAL, "搜索服务异常"),
                new ArrayList<>()
        );
    }

    public Pair<ServiceMessage, List<ApprovalFlow>> getPageAsTeacherView(@Nullable String pageParam, @Nullable String search) {
        try {

            if (pageParam == null || pageParam.isBlank()) {
                return ImmutablePair.of(
                        ServiceMessage.of(ServiceMessage.Level.WARN, "页码不能为空"),
                        new ArrayList<>()
                );
            }

            if (search == null || search.isBlank()) {
                return ImmutablePair.of(
                        ServiceMessage.of(ServiceMessage.Level.WARN, "搜索内容不能为空"),
                        new ArrayList<>()
                );
            }

            int page = Integer.parseInt(pageParam);

            return _getPage(page, search);
        } catch (Exception e) {
            e.printStackTrace();
            return ImmutablePair.of(
                    ServiceMessage.of(ServiceMessage.Level.FATAL, "搜索服务异常"),
                    new ArrayList<>()
            );
        }

    }

    private Pair<ServiceMessage, List<ApprovalFlow>> _getPage(int page, @NonNull String search) {

        val serviceMessageListPair = searchApprovalFlow(search);

        if (serviceMessageListPair.getLeft().getMessageLevel() == ServiceMessage.Level.SUCCESS) {

            var approvalFlowList = serviceMessageListPair.getRight();
            int start = Math.max((page - 1) * offset, 0);
            int end = Math.min(page * offset, approvalFlowList.size());

            approvalFlowList = approvalFlowList.subList(start, end);
            return ImmutablePair.of(
                    serviceMessageListPair.getLeft(),
                    approvalFlowList
            );
        }

        return ImmutablePair.of(serviceMessageListPair.getLeft(), new ArrayList<>());
    }

    public Pair<ServiceMessage, List<ApprovalFlow>> searchApprovalFlow(@Nullable String search) {

        if (search == null || search.isBlank()) {
            return ImmutablePair.of(
                    ServiceMessage.of(ServiceMessage.Level.WARN, "无效查询动作"),
                    new ArrayList<>()
            );
        }

        val s = search.split(" ");
        val pairs = Arrays.stream(s)
                .map(this::_searchApprovalFlowBySingleStr).toList();
        List<ApprovalFlow> result = pairs.get(0);
        pairs.forEach(
                approvalFlows -> {
                    CollUtil.unionDistinct(result, approvalFlows);
                }
        );
        return ImmutablePair.of(
                ServiceMessage.of(ServiceMessage.Level.SUCCESS, "查询到" + result.size() + "个结果"),
                result
        );
    }

    private List<ApprovalFlow> _searchApprovalFlowBySingleStr(@NonNull String search) {
        try (var session = MyDb.use().openSession(true)) {
            val approvalFlowMapper = session.getMapper(ApprovalFlowMapper.class);
            val approvalFlows = approvalFlowMapper.selectAll();

            if (approvalFlows == null || approvalFlows.isEmpty()) {
                return new ArrayList<>();
            }

            val approvalFlowPairList = approvalFlows.stream().map(
                    approvalFlow ->
//                            new ImmutablePair<>(approvalFlow, approvalFlow.getFlowNo() +
//                                    " " +
//                                    approvalFlow.getApproStatus().chinese +
//                                    " " +
//                                    approvalFlow.getTitle() +
//                                    " " +
//                                    approvalFlow.getBusType().chinese +
//                                    " " +
//                                    approvalFlow.getAddUserId().toString() +
//                                    " " +
//                                    approvalFlow.getAddTime().toString() +
//                                    " " +
//                                    approvalFlow.getRemark())
                            new ImmutablePair<>(approvalFlow, approvalFlow.getFlowNo() +
                                    approvalFlow.getApproStatus().chinese +
                                    approvalFlow.getTitle() +
                                    approvalFlow.getBusType().chinese +
                                    approvalFlow.getAddUserId().toString() +
                                    approvalFlow.getAddTime().toString() +
                                    approvalFlow.getRemark())
            ).toList();

            System.out.println("approvalFlowPairList = " + JSON.toJSONString(approvalFlowPairList));

            return approvalFlowPairList.stream()
                    .filter(approvalFlow -> approvalFlow.right.contains(search))
                    .map(pair -> pair.left)
                    .toList();
        }
    }


}
