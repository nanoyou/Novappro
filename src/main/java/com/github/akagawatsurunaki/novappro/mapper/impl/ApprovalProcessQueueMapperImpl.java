package com.github.akagawatsurunaki.novappro.mapper.impl;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import com.github.akagawatsurunaki.novappro.mapper.ApprovalProcessNodeMapper;
import com.github.akagawatsurunaki.novappro.mapper.ApprovalProcessQueueMapper;
import com.github.akagawatsurunaki.novappro.model.approval.ApprovalProcessNode;
import com.github.akagawatsurunaki.novappro.model.approval.ApprovalProcessQueue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ApprovalProcessQueueMapperImpl implements ApprovalProcessQueueMapper {

    @Getter
    static final ApprovalProcessQueueMapper instance = new ApprovalProcessQueueMapperImpl();

    static final ApprovalProcessNodeMapper APPROVAL_PROCESS_NODE_MAPPER = ApprovalProcessNodeMapperImpl.getInstance();

    @Override
    public Pair<VerifyCode, ApprovalProcessQueue> insertApprovalProcessQueue(@NonNull ApprovalProcessQueue queue) {
        try {
            Entity approvalProcessQueueEntity = Entity.create(TableApprovalProcessQueue.TABLE_NAME)
                    .set(TableApprovalProcessQueue.CODE.getFieldName(), queue.getCode())
                    .set(TableApprovalProcessQueue.COURSE_NODE_CODE.getFieldName(), queue.getCurrentNodeCode());
            Db.use().insert(approvalProcessQueueEntity);

            Entity hasApprovalProcessNodeEntity = Entity.create(TableHasApprovalProcessNode.TABLE_NAME)
                    .set(TableHasApprovalProcessNode.APPROVAL_PROCESS_NODE_CODE.getFieldName(), queue.getCurrentNodeCode())
                    .set(TableHasApprovalProcessNode.APPROVAL_PROCESS_QUEUE_CODE.getFieldName(), queue.getCode())
                    ;
            Db.use().insert(hasApprovalProcessNodeEntity);

            return new ImmutablePair<>(VerifyCode.MAPPER_OK, queue);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ImmutablePair<>(VerifyCode.SQL_ERROR, queue);
        }
    }

    @Override
    public Pair<VerifyCode, ApprovalProcessQueue> selectApprovalProcessQueueByCode(@NonNull String code) {
        try {

            String sql =
                    "SELECT * FROM " + TableApprovalProcessQueue.TABLE_NAME + "WHERE " + TableApprovalProcessQueue.CODE + "= ?;";
            List<Entity> entities = Db.use().query(sql, code);

            if (entities.isEmpty()) {
                return new ImmutablePair<>(VerifyCode.NO_SUCH_ENTITY, null);
            }

            Entity entity = entities.get(0);

            ApprovalProcessQueue result = new ApprovalProcessQueue();

            result.setCode(entity.getStr(TableApprovalProcessQueue.CODE.getFieldName()));
            result.setCurrentNodeCode(entity.getStr(TableApprovalProcessQueue.COURSE_NODE_CODE.getFieldName()));

            String sql2 =
                    "SELECT * FROM " + TableHasApprovalProcessNode.TABLE_NAME + "WHERE " + TableHasApprovalProcessNode.APPROVAL_PROCESS_QUEUE_CODE + "= ?;";
            List<Entity> entities2 = Db.use().query(sql2, code);

            List<ApprovalProcessNode> nodes = new ArrayList<>();
            for (Entity e : entities2) {
                String apnCode = e.getStr(result.getCode());

                var pair =
                        APPROVAL_PROCESS_NODE_MAPPER.selectApprovalProcessNodeByCode(apnCode);
                if (pair.getLeft() != ApprovalProcessNodeMapper.VerifyCode.MAPPER_OK) {
                    return new ImmutablePair<>(VerifyCode.NO_SUCH_ENTITY, null);
                }
                ApprovalProcessNode node = pair.getRight();
                nodes.add(node);
            }
            result.setQueue(nodes);

            return new ImmutablePair<>(VerifyCode.MAPPER_OK, result);
        } catch (SQLException e) {
            return new ImmutablePair<>(VerifyCode.SQL_ERROR, null);
        }
    }

    @AllArgsConstructor
    enum TableApprovalProcessQueue {
        CODE("code"),
        COURSE_NODE_CODE("current_node_code");

        @Getter
        public final String fieldName;
        public static final String TABLE_NAME = "approval_process_queue";

    }

    @AllArgsConstructor
    enum TableHasApprovalProcessNode {

        APPROVAL_PROCESS_QUEUE_CODE("approval_process_queue_code"),
        APPROVAL_PROCESS_NODE_CODE("approval_process_node_code");

        public static final String TABLE_NAME = "has_approval_process_node";
        @Getter
        public final String fieldName;
    }

    @AllArgsConstructor
    enum TableHasApproval{

        USER_ID("user_id"),
        APPROVAL_PROCESS_NODE_CODE("approval_process_node_code");

        public static final String TABLE_NAME = "has_approval";

        @Getter
        public final String fieldName;
    }
}
