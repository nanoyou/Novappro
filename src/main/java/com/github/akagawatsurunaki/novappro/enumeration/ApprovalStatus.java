package com.github.akagawatsurunaki.novappro.enumeration;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ApprovalStatus {

    AUDITING("正在审批"),
    WAITING_FOR_ME("等我审批"),
    APPROVED("审批通过"),
    REJECTED("审批拒绝");

    public final String chinese;
}
