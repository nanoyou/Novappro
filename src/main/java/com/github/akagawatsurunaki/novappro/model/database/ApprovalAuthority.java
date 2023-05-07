package com.github.akagawatsurunaki.novappro.model.database;

import com.github.akagawatsurunaki.novappro.annotation.Field;
import com.github.akagawatsurunaki.novappro.annotation.Table;
import com.github.akagawatsurunaki.novappro.annotation.ZhField;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;

/**
 * 对应数据库表approval_authority的实体类
 */
@Data
@ToString
@FieldNameConstants
@Table(value = "approval_authority")
@Builder
public class ApprovalAuthority {

    /**
     * 审批人的ID
     */
    @ZhField(value = "审批人ID")
    @Field(value = "user_id")
    Integer userId;

    /**
     * 审批人拥有的权重, 权重越高, 审批次序越靠前
     */
    @ZhField(value = "审批人的权重", desc = "权重越高，会优先进入审批链的头部部分。")
    @Field(value = "appro_weight")
    Integer approWeight;

    /**
     * 审批人可以审批的课程的代码
     */
    @ZhField(value = "审批人可以审批的课程代码")
    @Field(value = "course_code")
    String courseCode;
}
