package com.github.akagawatsurunaki.novappro.mapper.impl;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import com.github.akagawatsurunaki.novappro.constant.VerifyCode;
import com.github.akagawatsurunaki.novappro.mapper.ApprovalFlowDetailMapper;
import com.github.akagawatsurunaki.novappro.model.approval.ApprovalFlowDetail;
import com.github.akagawatsurunaki.novappro.util.EntityUtil;
import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.sql.SQLException;

public class ApprovalFlowDetailMapperImpl implements ApprovalFlowDetailMapper {

    @Getter
    private static final ApprovalFlowDetailMapper instance = new ApprovalFlowDetailMapperImpl();

    @Override
    public Pair<VerifyCode.Mapper, ApprovalFlowDetail> insert(@NonNull ApprovalFlowDetail approvalFlowDetail) {
        try {
            Entity entity = EntityUtil.getEntity(approvalFlowDetail);
            Db.use().insert(entity);
            return new ImmutablePair<>(VerifyCode.Mapper.OK, approvalFlowDetail);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ImmutablePair<>(VerifyCode.Mapper.SQL_EXCEPTION, approvalFlowDetail);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return new ImmutablePair<>(VerifyCode.Mapper.OTHER_EXCEPTION, approvalFlowDetail);
        }
    }
    String selectSQL = "SELECT * FROM `audit_flow_detail` WHERE `audit_flow_detail`.`flow_no` = ?;";
    @Override
    public Pair<VerifyCode.Mapper, ApprovalFlowDetail> select(@NonNull String flowNo) {
        try {
            var query = Db.use().query(selectSQL, flowNo);

            if (query.isEmpty()) {
                return new ImmutablePair<>(VerifyCode.Mapper.NO_SUCH_ENTITY, null);
            }

            var entity = query.get(0);

            ApprovalFlowDetail approvalFlowDetail = EntityUtil.parseEntity(ApprovalFlowDetail.class, entity);

            return new ImmutablePair<>(VerifyCode.Mapper.OK, approvalFlowDetail);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ImmutablePair<>(VerifyCode.Mapper.OK, null);
        }
    }

}
