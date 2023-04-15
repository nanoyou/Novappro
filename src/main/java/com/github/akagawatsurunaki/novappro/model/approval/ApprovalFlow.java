package com.github.akagawatsurunaki.novappro.model.approval;

import com.github.akagawatsurunaki.novappro.annotation.Field;
import com.github.akagawatsurunaki.novappro.annotation.Table;
import com.github.akagawatsurunaki.novappro.enumeration.BusType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
@AllArgsConstructor
@Table(table = "audit_flow")
public class ApprovalFlow {

    @Field(field = "flow_no")
    String flowNo;

    @Field(field = "appro_status")
    String approStatus;

    @Field(field = "title")
    String title;

    @Field(field = "bus_type")
    BusType busType;

    @Field(field = "add_user_id")
    Integer addUserId;

    @Field(field = "add_time")
    Date addTime;
}
