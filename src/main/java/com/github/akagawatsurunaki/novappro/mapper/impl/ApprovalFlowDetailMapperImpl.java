package com.github.akagawatsurunaki.novappro.mapper.impl;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import com.github.akagawatsurunaki.novappro.constant.VerifyCode;
import com.github.akagawatsurunaki.novappro.mapper.ApprovalFlowDetailMapper;
import com.github.akagawatsurunaki.novappro.model.approval.ApprovalFlowDetail;
import com.github.akagawatsurunaki.novappro.util.EntityUtil;
import lombok.NonNull;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.sql.SQLException;

public class ApprovalFlowDetailMapperImpl implements ApprovalFlowDetailMapper {

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
}
