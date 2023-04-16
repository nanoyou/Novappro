package com.github.akagawatsurunaki.novappro.model.approval;

import com.github.akagawatsurunaki.novappro.annotation.Field;
import com.github.akagawatsurunaki.novappro.annotation.Table;
import com.github.akagawatsurunaki.novappro.enumeration.ApprovalStatus;
import com.github.akagawatsurunaki.novappro.enumeration.BusType;
import lombok.*;

import java.util.Date;

/**
 * Course Appro Flow 1...1 Approval Flow
 * Approval Flow 1...n Approval Flow Detail
 *
 * Approval Flow 1...1 Approval
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(table = "audit_flow")
public class ApprovalFlow {

    @Field(field = "flow_no")
    String flowNo;

    @Field(field = "appro_status")
    ApprovalStatus approStatus;

    @Field(field = "title")
    String title;

    @Field(field = "bus_type")
    BusType busType;

    @Field(field = "add_user_id")
    Integer addUserId;

    @Field(field = "add_time")
    Date addTime;

}