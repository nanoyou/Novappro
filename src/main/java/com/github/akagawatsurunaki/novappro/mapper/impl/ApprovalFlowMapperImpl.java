package com.github.akagawatsurunaki.novappro.mapper.impl;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import com.github.akagawatsurunaki.novappro.constant.VerifyCode;
import com.github.akagawatsurunaki.novappro.mapper.ApprovalFlowMapper;
import com.github.akagawatsurunaki.novappro.model.database.approval.ApprovalFlow;
import com.github.akagawatsurunaki.novappro.util.EntityUtil;
import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.sql.SQLException;

public class ApprovalFlowMapperImpl implements ApprovalFlowMapper {

    @Getter
    public static final ApprovalFlowMapper instance = new ApprovalFlowMapperImpl();

    @Override
    public Pair<VerifyCode.Mapper, ApprovalFlow> insert(ApprovalFlow approvalFlow) {
        try {
            Entity entity = EntityUtil.getEntity(approvalFlow);
            Db.use().insert(entity);
            return new ImmutablePair<>(VerifyCode.Mapper.OK, approvalFlow);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return new ImmutablePair<>(VerifyCode.Mapper.OTHER_EXCEPTION, approvalFlow);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ImmutablePair<>(VerifyCode.Mapper.SQL_EXCEPTION, approvalFlow);
        }
    }

    String sql = "SELECT * FROM `audit_flow` WHERE `audit_flow`.`flow_no` = ?";
    @Override
    public Pair<VerifyCode.Mapper, ApprovalFlow> select(@NonNull String flowNo) {
        try {
            var entities = Db.use().query(sql, flowNo);

            if (entities.size() != 1){
                return new ImmutablePair<>(VerifyCode.Mapper.NO_SUCH_ENTITY, null);
            }

            var entity = entities.get(0);
            var result = EntityUtil.parseEntity(ApprovalFlow.class, entity);
            return new ImmutablePair<>(VerifyCode.Mapper.OK, result);

        } catch (SQLException e) {
            e.printStackTrace();
            return new ImmutablePair<>(VerifyCode.Mapper.SQL_EXCEPTION, null);
        }
    }
}
