package com.github.akagawatsurunaki.novappro.model.approval;

import com.github.akagawatsurunaki.novappro.annotation.ChineseFieldName;
import com.github.akagawatsurunaki.novappro.interfase.HasChineseField;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class ApprovalProcessQueue implements HasChineseField {

    @ChineseFieldName(value = "审批流程处理队列代码")
    String code;

    @ChineseFieldName(value = "审批流程队列")
    List<ApprovalProcessNode> queue;

    @ChineseFieldName(value = "当前审批流程节点ID")
    String currentNodeCode;

}
