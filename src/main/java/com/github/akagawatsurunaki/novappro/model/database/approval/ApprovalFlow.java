package com.github.akagawatsurunaki.novappro.model.database.approval;

import com.github.akagawatsurunaki.novappro.annotation.Field;
import com.github.akagawatsurunaki.novappro.annotation.Table;
import com.github.akagawatsurunaki.novappro.enumeration.ApprovalStatus;
import com.github.akagawatsurunaki.novappro.enumeration.BusType;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.util.Date;

/**
 * Course Appro Flow 1...1 Approval Flow
 * Approval Flow 1...n Approval Flow Detail
 * <p>
 * Approval Flow 1...1 Approval
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldNameConstants
@Table(table = "approval_flow")
public class ApprovalFlow {

    /**
     * 审批流ApprovalFlow的唯一标志号码, 又名流水号
     */
    @Field(field = "flow_no")
    String flowNo;

    /**
     * 审批流ApprovalFlow的状态
     *
     * @implNote 如果实现了LinearBus审批流程模式, 那么审批流ApprovalFlow的状态应与最后一个审批明细的状态一致
     */
    @Field(field = "appro_status")
    ApprovalStatus approStatus;

    /**
     * 审批流ApprovalFlow的标题
     */
    @Field(field = "title")
    String title;

    /**
     * 审批流ApprovalFlow的总线类型
     *
     * @implNote LinearBus将审批流程组成一条直线, 即是一个线性流程图.
     */
    @Field(field = "bus_type")
    BusType busType;

    /**
     * 审批流ApprovalFlow的创建人ID
     */
    @Field(field = "add_user_id")
    Integer addUserId;

    /**
     * 审批流ApprovalFlow的创建时间
     */
    @Field(field = "add_time")
    Date addTime;

    /**
     * 申请人填写的申请原因
     */
    @Field(field = "remark")
    String remark;

}