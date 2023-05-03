package com.github.akagawatsurunaki.novappro.mapper.impl;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import com.github.akagawatsurunaki.novappro.constant.VC;
import com.github.akagawatsurunaki.novappro.enumeration.ApprovalStatus;
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

    @Override
    public Pair<VC.Mapper, ApprovalFlowDetail> insert(@NonNull ApprovalFlowDetail approvalFlowDetail) {
        try {
            Entity entity = EntityUtil.getEntity(approvalFlowDetail);
            Db.use().insert(entity);
            return new ImmutablePair<>(VC.Mapper.OK, approvalFlowDetail);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ImmutablePair<>(VC.Mapper.SQL_EXCEPTION, approvalFlowDetail);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return new ImmutablePair<>(VC.Mapper.OTHER_EXCEPTION, approvalFlowDetail);
        }
    }

    /**
     *
     * @return
     */
    public Pair<VC.Mapper, ApprovalFlowDetail> selectCurrentNode() {
        return null;
    }

    @Override
    @Deprecated
    public Pair<VC.Mapper, ApprovalFlowDetail> select(@NonNull String flowNo) {
        try {
            String selectSQL = "SELECT * FROM `audit_flow_detail` WHERE `audit_flow_detail`.`flow_no` = ?;";
            var query = Db.use().query(selectSQL, flowNo);

            if (query.size() != 1) {
                return new ImmutablePair<>(VC.Mapper.NO_SUCH_ENTITY, null);
            }

            var entity = query.get(0);

            ApprovalFlowDetail approvalFlowDetail = EntityUtil.parseEntity(ApprovalFlowDetail.class, entity);

            return new ImmutablePair<>(VC.Mapper.OK, approvalFlowDetail);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ImmutablePair<>(VC.Mapper.SQL_EXCEPTION, null);
        }
    }

    @Override
    public Pair<VC.Mapper, List<ApprovalFlowDetail>> selectList(@NonNull List<String> flowNos) {
        List<ApprovalFlowDetail> result = new ArrayList<>();
        for (String flowNo : flowNos) {
            var vc_afd = select(flowNo);
            if (vc_afd.getLeft() == VC.Mapper.OK){
                var afd = vc_afd.getRight();
                result.add(afd);
            } else {
                return new ImmutablePair<>(vc_afd.getLeft(), null);
            }
        }
        return new ImmutablePair<>(VC.Mapper.OK, result);
    }

    @Override
    public Pair<VC.Mapper, List<String>> selectFlowNoByApproverId(@NonNull Integer approverId) {
        try {
            final String selectFlowNoByApproverIdSQL = """
                    SELECT `flow_no`
                    FROM `audit_flow_detail`
                    WHERE `audit_flow_detail`.`audit_user_id` = ?;""";
            var query = Db.use().query(selectFlowNoByApproverIdSQL, approverId);

            if (query.isEmpty()) {
                return new ImmutablePair<>(VC.Mapper.NO_SUCH_ENTITY, null);
            }

            List<String> result = new ArrayList<>();

            query.forEach(
                    entity -> {
                        var flowNo = entity.getStr(EntityUtil.getFieldName(ApprovalFlowDetail.class,
                                ApprovalFlowDetail.Fields.flowNo));
                        result.add(flowNo);
                    }
            );

            return new ImmutablePair<>(VC.Mapper.OK, result);

        } catch (SQLException e) {
            e.printStackTrace();
            return new ImmutablePair<>(VC.Mapper.SQL_EXCEPTION, null);
        }
    }

    @Override
    public VC.Mapper updateApproStatus(@NonNull String flowNo, @NonNull Integer id,
                                       @NonNull ApprovalStatus status) throws SQLException {

        String sql = """
                UPDATE audit_flow_detail
                SET audit_flow_detail.audit_status = ?
                WHERE audit_flow_detail.flow_no = ? AND audit_flow_detail.id = ?""";

        Db.use().execute(sql, status.name(), flowNo, id);

        return VC.Mapper.OK;

    }

    @Override
    public VC.Mapper updateApproRemark(@NonNull String flowNo, @NonNull Integer id,
                                       @NonNull String remark) throws SQLException {

        String sql = """
                UPDATE audit_flow_detail
                SET audit_flow_detail.audit_remark = ?
                WHERE audit_flow_detail.flow_no = ? AND audit_flow_detail.id = ?""";

        Db.use().execute(sql, remark, flowNo, id);

        return VC.Mapper.OK;
    }


}
