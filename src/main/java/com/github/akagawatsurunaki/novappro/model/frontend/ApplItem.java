package com.github.akagawatsurunaki.novappro.model.frontend;

import com.github.akagawatsurunaki.novappro.annotation.ZhField;
import com.github.akagawatsurunaki.novappro.enumeration.ApprovalStatus;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.util.Date;

@Data
@FieldNameConstants
@Builder
public class ApplItem {

    @ZhField(value = "审批流编号")
    String flowNo;

    @ZhField(value = "标题")
    String title;

    @ZhField(value = "发起人")
    String applicantName;

    @ZhField(value = "审批人")
    String approverName;

    @ZhField(value = "申请时间")
    Date addTime;

    @ZhField(value = "申请状态")
    ApprovalStatus approvalStatus;



}
