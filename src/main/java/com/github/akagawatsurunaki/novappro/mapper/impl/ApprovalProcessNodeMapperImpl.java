package com.github.akagawatsurunaki.novappro.mapper.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import com.github.akagawatsurunaki.novappro.mapper.ApplicationEntityMapper;
import com.github.akagawatsurunaki.novappro.mapper.ApprovalProcessNodeMapper;
import com.github.akagawatsurunaki.novappro.mapper.HasApprovalMapper;
import com.github.akagawatsurunaki.novappro.model.User;
import com.github.akagawatsurunaki.novappro.model.approval.ApplicationEntity;
import com.github.akagawatsurunaki.novappro.model.approval.ApprovalProcessNode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ApprovalProcessNodeMapperImpl implements ApprovalProcessNodeMapper {

    @Getter
            static final ApprovalProcessNodeMapper instance = new ApprovalProcessNodeMapperImpl();

    String selectApplicationEntityByCodeSQL = "/mysql/select_application_entity_by_code.sql";

    final static HasApprovalMapper HAS_APPROVAL_MAPPER = HasApprovalMapperImpl.getInstance();

    @Override
    public Pair<VerifyCode, ApprovalProcessNode> insertApprovalProcessNode(ApprovalProcessNode node) {
        try {
            Db.use().insert(Entity.create(MySQLFieldName.TABLE_NAME.getName()).set(MySQLFieldName.CODE.getName(),
                    node.getCode()).set(MySQLFieldName.APPLICATION_ENTITY_CODE.getName(),
                    node.getApplicationEntity().getCode()).set(MySQLFieldName.PROCESS_TYPE.getName(),
                    node.getProcessType().name()).set(MySQLFieldName.CURRENT_STATUS.getName(),
                    node.getCurrentStatus().name()));

            // TODO
            for (User approver : node.getApprovers()) {
                Db.use().insert(Entity.create(MySQLFieldName.TABLE_APPROVAL_PROCESS_NODE.getName())
                        .set(MySQLFieldName.USER_ID.getName(), approver.getId())
                        .set(MySQLFieldName.APPROVAL_PROCESS_NODE_CODE.getName(), node.getCode()));
            }

            HAS_APPROVAL_MAPPER.insert(node);

            return new ImmutablePair<>(VerifyCode.MAPPER_OK, node);

        } catch (SQLException e) {
            return new ImmutablePair<>(VerifyCode.SQL_EXCEPTION, node);
        }
    }

    @Override
    public Pair<VerifyCode, ApprovalProcessNode> selectApprovalProcessNodeByCode(@NonNull String code) {

        try {
            URL resource = ResourceUtil.getResource(selectApplicationEntityByCodeSQL);
            List<Entity> entities = Db.use().query(FileUtil.readString(resource, StandardCharsets.UTF_8), code);
            if (entities.isEmpty()) {
                return new ImmutablePair<>(VerifyCode.NO_SUCH_ENTITY, null);
            }

            Entity entity = entities.get(0);
            ApprovalProcessNode node = new ApprovalProcessNode();

            // code
            node.setCode(entity.getStr(MySQLFieldName.CODE.getName()));

            // 获取 ApplicationEntity
            String applicationEntityCode =
                    entity.getStr(MySQLFieldName.APPLICATION_ENTITY_CODE.getName());
            Pair<ApplicationEntityMapper.VerifyCode, ApplicationEntity> pair =
                    ApplicationEntityMapperImpl.getInstance().selectApplicationEntityByCode(applicationEntityCode);

            if (pair.getLeft() != ApplicationEntityMapper.VerifyCode.MAPPER_OK){
                return new ImmutablePair<>(VerifyCode.NO_SUCH_ENTITY, null);
            }

            node.setApplicationEntity(pair.getRight());

            // 构建审批人列表
            List<Integer> ids = selectUserIdFromHasApprovalByApprovalProcessNodeCode(code);

            if (ids == null){
                return new ImmutablePair<>(VerifyCode.NO_SUCH_ENTITY, null);
            }

            List<User> users = new ArrayList<>();

            for (Integer id : ids) {
                User user = UserMapperImpl.getInstance().getUserById(id);
                users.add(user);
            }

            node.setApprovers(users);

            // 审批类型
            node.setProcessType(entity.getEnum(ApprovalProcessNode.Type.class, MySQLFieldName.PROCESS_TYPE.getName()));

            // 当前审批状态
            node.setCurrentStatus(entity.getEnum(ApprovalProcessNode.Status.class, MySQLFieldName.CURRENT_STATUS.getName()));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;

    }

    private List<Integer> selectUserIdFromHasApprovalByApprovalProcessNodeCode(@NonNull String code) {
        try {
            String sql = "SELECT * FROM has_approval WHERE has_approval.approval_process_node_code = ?;";
            List<Entity> entities = Db.use().query(sql, code);
            if (entities.isEmpty()) {
                return null;
            }

            List<Integer> result = new ArrayList<>();

            for (Entity entity : entities) {
                result.add(entity.getInt(MySQLFieldName.USER_ID.getName()));
            }

            return result;

        } catch (SQLException e) {
            return null;
        }
    }

    @AllArgsConstructor
    enum MySQLFieldName {
        TABLE_NAME("approval_process_node"), CODE("code"), APPLICATION_ENTITY_CODE("application_entity_code"),
        PROCESS_TYPE("process_type"), CURRENT_STATUS("current_status"), TABLE_APPROVAL_PROCESS_NODE(
                "approval_process_node"), USER_ID("user_id"), APPROVAL_PROCESS_NODE_CODE("approval_process_node_code");

        @Getter
        public final String name;

    }

}
