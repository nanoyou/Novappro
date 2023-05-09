package com.github.akagawatsurunaki.novappro.model.database.approval;

import com.alibaba.excel.annotation.ExcelProperty;
import com.github.akagawatsurunaki.novappro.annotation.Field;
import com.github.akagawatsurunaki.novappro.annotation.Table;
import com.github.akagawatsurunaki.novappro.annotation.ZhField;
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
@Table("approval_flow")
public class ApprovalFlow {

    /**
     * 审批流ApprovalFlow的唯一标志号码, 又名流水号
     */
    @ExcelProperty("流水号")
    @ZhField("流水号")
    @Field("flow_no")
    String flowNo;

    /**
     * 审批流ApprovalFlow的状态
     *
     * @implNote 如果实现了LinearBus审批流程模式, 那么审批流ApprovalFlow的状态应与最后一个审批明细的状态一致
     */
    @ExcelProperty("当前审批状态")
    @ZhField("当前审批状态")
    @Field("appro_status")
    ApprovalStatus approStatus;

    /**
     * 审批流ApprovalFlow的标题
     */
    @ExcelProperty("标题")
    @ZhField("标题")
    @Field("title")
    String title;

    /**
     * 审批流ApprovalFlow的总线类型
     *
     * @implNote LinearBus将审批流程组成一条直线, 即是一个线性流程图.
     */
    @ExcelProperty("审批流拓扑结构")
    @ZhField("审批流拓扑结构")
    @Field("bus_type")
    BusType busType;

    /**
     * 审批流ApprovalFlow的创建人ID
     */
    @ExcelProperty("申请人ID")
    @ZhField("申请人ID")
    @Field("add_user_id")
    Integer addUserId;

    /**
     * 审批流ApprovalFlow的创建时间
     */
    @ExcelProperty("申请发起时间")
    @ZhField("申请发起时间")
    @Field("add_time")
    Date addTime;

    /**
     * 申请人填写的申请原因
     */
    @ExcelProperty("申请原因")
    @ZhField("申请原因")
    @Field("remark")
    String remark;

}