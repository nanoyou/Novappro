package com.github.akagawatsurunaki.novappro.model;

import com.github.akagawatsurunaki.novappro.annotation.ChineseFieldName;
import lombok.Data;

import java.util.List;

@Data
public class ApprovalProcessQueue {

    @ChineseFieldName(value = "审批流程处理队列ID")
    Integer id;

    @ChineseFieldName(value = "申请表")
    ApplicationForm form;

    @ChineseFieldName(value = "审批流程队列")
    List<ApprovalProcessNode> queue;

    @ChineseFieldName(value = "当前审批流程节点ID")
    Integer currentNodeId;

}
