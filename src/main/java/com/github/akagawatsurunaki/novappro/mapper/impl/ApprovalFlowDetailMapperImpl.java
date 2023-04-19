package com.github.akagawatsurunaki.novappro.mapper.impl;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import com.github.akagawatsurunaki.novappro.constant.VerifyCode;
import com.github.akagawatsurunaki.novappro.mapper.ApprovalFlowDetailMapper;
import com.github.akagawatsurunaki.novappro.model.database.approval.ApprovalFlowDetail;
import com.github.akagawatsurunaki.novappro.util.EntityUtil;
import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ApprovalFlowDetailMapperImpl implements ApprovalFlowDetailMapper {

    @Getter
    private static final ApprovalFlowDetailMapper instance = new ApprovalFlowDetailMapperImpl();
    String selectSQL = "SELECT * FROM `audit_flow_detail` WHERE `audit_flow_detail`.`flow_no` = ?;";
    String selectFlowNoByApproverIdSQL = "SELECT `flow_no` FROM `audit_flow_detail` WHERE `audit_flow_detail`" +
            ".`audit_user_id` = ?;";

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

    @Override
    public Pair<VerifyCode.Mapper, ApprovalFlowDetail> select(@NonNull String flowNo) {
        try {
            var query = Db.use().query(selectSQL, flowNo);

            if (query.size() != 1) {
                return new ImmutablePair<>(VerifyCode.Mapper.NO_SUCH_ENTITY, null);
            }

            var entity = query.get(0);

            ApprovalFlowDetail approvalFlowDetail = EntityUtil.parseEntity(ApprovalFlowDetail.class, entity);

            return new ImmutablePair<>(VerifyCode.Mapper.OK, approvalFlowDetail);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ImmutablePair<>(VerifyCode.Mapper.SQL_EXCEPTION, null);
        }
    }

    @Override
    public Pair<VerifyCode.Mapper, List<String>> selectFlowNoByApproverId(@NonNull Integer approverId) {
        try {
            var query = Db.use().query(selectFlowNoByApproverIdSQL, approverId);

            if (query.isEmpty()) {
                return new ImmutablePair<>(VerifyCode.Mapper.NO_SUCH_ENTITY, null);
            }

            List<String> result = new ArrayList<>();

            query.forEach(
                    entity -> {
                        var flowNo = entity.getStr(EntityUtil.getFieldName(ApprovalFlowDetail.class,
                                ApprovalFlowDetail.Fields.flowNo));
                        result.add(flowNo);
                    }
            );

            return new ImmutablePair<>(VerifyCode.Mapper.OK, result);

        } catch (SQLException e) {
            e.printStackTrace();
            return new ImmutablePair<>(VerifyCode.Mapper.SQL_EXCEPTION, null);
        }
    }

}
