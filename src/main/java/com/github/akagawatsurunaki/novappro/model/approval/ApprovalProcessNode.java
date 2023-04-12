package com.github.akagawatsurunaki.novappro.model.approval;

import com.github.akagawatsurunaki.novappro.annotation.ChineseFieldName;
import com.github.akagawatsurunaki.novappro.interfase.HasChineseField;
import com.github.akagawatsurunaki.novappro.model.User;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;

import java.util.List;

@Data
@ToString
public class ApprovalProcessNode implements HasChineseField {

    @ChineseFieldName(value = "审批节点代码")
    String code;

    @ChineseFieldName(value = "审批人",
            description = "审批人可以为多个，所有位于同一ApprovalProcessNode下的审批人都是同级别的。")
    List<User> approvers;

    @ChineseFieldName(value = "申请实体")
    ApplicationEntity applicationEntity;

    @ChineseFieldName(value = "审批类型")
    Type processType = Type.NO_ONE;

    @ChineseFieldName(value = "当前审批状态")
    Status currentStatus;

    @FieldNameConstants
    public enum Type implements HasChineseField {
        @ChineseFieldName(value = "任何人均可审批")
        ANY_ONE,
        @ChineseFieldName(value = "只有一个人能审批")
        ONLY_ONE,
        @ChineseFieldName(value = "没有任何人可以同意审批")
        NO_ONE
    }

    @FieldNameConstants
    public enum Status implements HasChineseField{
        @ChineseFieldName(value = "等待审批")
        WAIT_FOR_APPROVAL,
        @ChineseFieldName(value = "同意审批")
        APPROVED,
        @ChineseFieldName(value = "拒绝审批")
        REFUSED
    }

}
