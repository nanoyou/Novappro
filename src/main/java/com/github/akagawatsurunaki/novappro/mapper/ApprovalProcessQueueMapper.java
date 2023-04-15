package com.github.akagawatsurunaki.novappro.mapper;

import com.github.akagawatsurunaki.novappro.model.approval.ApprovalProcessQueue;
import lombok.NonNull;
import org.apache.commons.lang3.tuple.Pair;

public interface ApprovalProcessQueueMapper {


    Pair<VerifyCode, ApprovalProcessQueue> insertApprovalProcessQueue(@NonNull ApprovalProcessQueue queue);

    Pair<VerifyCode, ApprovalProcessQueue> selectApprovalProcessQueueByCode(@NonNull String code);

    enum VerifyCode{
        MAPPER_OK,
        SQL_ERROR,
        NO_SUCH_ENTITY,
    }

}
