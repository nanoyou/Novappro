package com.github.akagawatsurunaki.novappro.mapper;

import com.github.akagawatsurunaki.novappro.constant.VerifyCode;
import com.github.akagawatsurunaki.novappro.model.approval.ApprovalFlowDetail;
import lombok.NonNull;
import org.apache.commons.lang3.tuple.Pair;

public interface ApprovalFlowDetailMapper {

    Pair<VerifyCode.Mapper, ApprovalFlowDetail> insert(@NonNull ApprovalFlowDetail approvalFlowDetail);

}
