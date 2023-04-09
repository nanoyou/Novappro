package com.github.akagawatsurunaki.novappro.model;

import com.github.akagawatsurunaki.novappro.annotation.ChineseFieldName;
import lombok.Data;

import java.util.List;

@Data
public class ApprovalProcessQueue {

    @ChineseFieldName(chineseFieldName = "审批流程处理队列ID")
    Integer id;

    @ChineseFieldName(chineseFieldName = "申请表")
    ApplicationForm form;

    @ChineseFieldName(chineseFieldName = "审批流程队列")
    List<ApprovalProcessNode> queue;

    @ChineseFieldName(chineseFieldName = "当前审批流程节点ID")
    Integer currentNodeId;

}
