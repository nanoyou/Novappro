package com.github.akagawatsurunaki.novappro.mapper;

import com.github.akagawatsurunaki.novappro.model.approval.ApprovalProcessNode;
import lombok.NonNull;
import org.apache.commons.lang3.tuple.Pair;

public interface ApprovalProcessNodeMapper {

    Pair<VerifyCode, ApprovalProcessNode> insertApprovalProcessNode(ApprovalProcessNode node);

    Pair<VerifyCode, ApprovalProcessNode> selectApprovalProcessNodeByCode(@NonNull String code);

    enum VerifyCode {
        MAPPER_OK,
        NO_SUCH_ENTITY,
        SQL_EXCEPTION
    }
}
