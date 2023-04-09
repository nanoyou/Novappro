package com.github.akagawatsurunaki.novappro.model;

import com.github.akagawatsurunaki.novappro.annotation.ChineseFieldName;
import lombok.Data;

import java.util.List;

@Data
public class ApprovalProcessNode {

    @ChineseFieldName(chineseFieldName = "审批人",
            description = "审批人可以为多个，所有位于同一ApprovalProcessNode下的审批人都是同级别的。")
    List<Integer> userIds;

    @ChineseFieldName(chineseFieldName = "审批类型")
    Type processType = Type.NO_ONE;

    @ChineseFieldName(chineseFieldName = "当前审批状态")
    Status currentStatus;

    public enum Type {
        @ChineseFieldName(chineseFieldName = "任何人均可审批")
        ANY_ONE,
        @ChineseFieldName(chineseFieldName = "只有一个人能审批")
        ONLY_ONE,
        @ChineseFieldName(chineseFieldName = "没有任何人可以同意审批")
        NO_ONE
    }

    public enum Status{
        @ChineseFieldName(chineseFieldName = "等待审批")
        WAIT_FOR_APPROVAL,
        @ChineseFieldName(chineseFieldName = "同意审批")
        APPROVED,
        @ChineseFieldName(chineseFieldName = "拒绝审批")
        REFUSED
    }

}
