package com.github.akagawatsurunaki.novappro.model.approval;

import com.github.akagawatsurunaki.novappro.annotation.Field;
import com.github.akagawatsurunaki.novappro.annotation.Table;
import com.github.akagawatsurunaki.novappro.enumeration.ApprovalStatus;
import com.github.akagawatsurunaki.novappro.enumeration.BusType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@ToString
@Table(table = "audit_flow_detail")
public class ApprovalFlowDetail {

    @Field(field = "id")
    Integer id;
    @Field(field = "flow_no")
    String flowNo;
    @Field(field = "bus_type")
    BusType busType;
    @Field(field = "audit_user_id")
    Integer auditUserId;
    @Field(field = "audit_remark")
    String auditRemark;
    @Field(field = "audit_time")
    Date auditTime;
    @Field(field = "audit_status")
    ApprovalStatus auditStatus;

}