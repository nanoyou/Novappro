package com.github.akagawatsurunaki.novappro.mapper;

import com.github.akagawatsurunaki.novappro.model.approval.ApprovalProcessNode;
import lombok.NonNull;

public interface HasApprovalMapper {

    ApprovalProcessNode insert(@NonNull ApprovalProcessNode node);

}
