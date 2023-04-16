package com.github.akagawatsurunaki.novappro.model.approval;

import com.github.akagawatsurunaki.novappro.annotation.Field;
import com.github.akagawatsurunaki.novappro.annotation.Table;
import com.github.akagawatsurunaki.novappro.enumeration.ApprovalStatus;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Table(table = "audit_flow_detail")
public class ApprovalFlowDetail {

    @Field(field = "id")
    Integer id;

    @Field(field = "flow_no")
    String flowNo;

    @Field(field = "audit_user_id")
    Integer auditUserId;

    @Field(field = "audit_remark")
    String auditRemark;

    @Field(field = "audit_time")
    Date auditTime;

    @Field(field = "audit_status")
    ApprovalStatus auditStatus;

}