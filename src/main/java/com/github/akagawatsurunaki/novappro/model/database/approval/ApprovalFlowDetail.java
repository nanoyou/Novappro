package com.github.akagawatsurunaki.novappro.model.database.approval;

import com.github.akagawatsurunaki.novappro.annotation.Field;
import com.github.akagawatsurunaki.novappro.annotation.Table;
import com.github.akagawatsurunaki.novappro.annotation.ZhField;
import com.github.akagawatsurunaki.novappro.enumeration.ApprovalStatus;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@FieldNameConstants
@Table("audit_flow_detail")
public class ApprovalFlowDetail {

    /**
     * 审批明细ApprovalFlowDetail的id
     * @implNote 这里如果想要按照LinearBus的方式读取, 必须按照此字段的值大小进行排序.
     */
    @ZhField("审批明细表ID")
    @Field("id")
    Integer id;

    /**
     * 审批明细ApprovalFlowDetail所从属的审批流的唯一标志号码
     */
    @ZhField("流水号")
    @Field("flow_no")
    String flowNo;

    /**
     * 审批人的ID
     */
    @ZhField("审批人ID")
    @Field("audit_user_id")
    Integer auditUserId;

    /**
     * 审批人的批注(备忘)
     */
    @ZhField("审批人备注")
    @Field("audit_remark")
    String auditRemark;

    /**
     * 审批人创建这个审批明细ApprovalFlowDetail的时间
     */
    @ZhField("审批时间")
    @Field("audit_time")
    Date auditTime;

    /**
     * 审批状态
     * @implNote 更改状态时, 应使用枚举类
     */
    @ZhField("当前审批状态")
    @Field("audit_status")
    ApprovalStatus auditStatus;

}