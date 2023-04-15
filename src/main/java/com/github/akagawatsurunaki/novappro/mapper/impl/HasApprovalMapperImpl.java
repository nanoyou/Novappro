package com.github.akagawatsurunaki.novappro.mapper.impl;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import com.github.akagawatsurunaki.novappro.constant.Table;
import com.github.akagawatsurunaki.novappro.mapper.ApprovalProcessNodeMapper;
import com.github.akagawatsurunaki.novappro.mapper.ApprovalProcessQueueMapper;
import com.github.akagawatsurunaki.novappro.mapper.HasApprovalMapper;
import com.github.akagawatsurunaki.novappro.model.User;
import com.github.akagawatsurunaki.novappro.model.approval.ApprovalProcessNode;
import com.github.akagawatsurunaki.novappro.model.approval.ApprovalProcessQueue;
import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.lang3.tuple.Pair;

import java.sql.SQLException;
import java.util.List;

public class HasApprovalMapperImpl implements HasApprovalMapper {

    @Getter
    final static HasApprovalMapper instance = new HasApprovalMapperImpl();

    /**
     * 将给定ApprovalProcessNode对象中的每一位审批人对应多个审批流程节点的记录储存在数据库中
     *
     * @param node
     * @return
     */
    @Override
    public ApprovalProcessNode insert(@NonNull ApprovalProcessNode node) {
        try {

            // 每一位审批人对应多个审批流程节点
            for (User approver : node.getApprovers()) {

                var userId = approver.getId();
                var entity =
                        Entity.create(Table.HasApproval.TABLE_NAME)
                                .set(Table.HasApproval.USER_ID.field, userId)
                                .set(Table.HasApproval.APPROVAL_PROCESS_NODE_CODE.field, node.getCode());
                Db.use().insert(entity);

            }
            return node;
        } catch (SQLException e) {
            return null;
        }
    }

}
