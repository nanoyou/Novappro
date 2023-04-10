package com.github.akagawatsurunaki.novappro.model;

import com.github.akagawatsurunaki.novappro.annotation.ChineseFieldName;
import lombok.Data;

import java.util.List;

@Data
public class ApprovalProcessNode {

    @ChineseFieldName(value = "审批人",
            description = "审批人可以为多个，所有位于同一ApprovalProcessNode下的审批人都是同级别的。")
    List<Integer> userIds;

    @ChineseFieldName(value = "审批类型")
    Type processType = Type.NO_ONE;

    @ChineseFieldName(value = "当前审批状态")
    Status currentStatus;

    public enum Type {
        @ChineseFieldName(value = "任何人均可审批")
        ANY_ONE,
        @ChineseFieldName(value = "只有一个人能审批")
        ONLY_ONE,
        @ChineseFieldName(value = "没有任何人可以同意审批")
        NO_ONE
    }

    public enum Status{
        @ChineseFieldName(value = "等待审批")
        WAIT_FOR_APPROVAL,
        @ChineseFieldName(value = "同意审批")
        APPROVED,
        @ChineseFieldName(value = "拒绝审批")
        REFUSED
    }

}
