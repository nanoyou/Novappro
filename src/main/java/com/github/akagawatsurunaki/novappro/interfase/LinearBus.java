package com.github.akagawatsurunaki.novappro.interfase;

import com.github.akagawatsurunaki.novappro.model.database.approval.ApprovalFlowDetail;

public interface LinearBus {

    ApprovalFlowDetail nextNode();
}
