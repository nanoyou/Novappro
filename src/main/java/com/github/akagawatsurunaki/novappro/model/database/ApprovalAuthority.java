package com.github.akagawatsurunaki.novappro.model.database;

import cn.hutool.bloomfilter.filter.HfIpFilter;
import com.github.akagawatsurunaki.novappro.annotation.Field;
import com.github.akagawatsurunaki.novappro.annotation.Table;
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
@Table(table = "approval_authority")
@Builder
public class ApprovalAuthority {

    /**
     * 审批人的ID
     */
    @Field(field = "user_id")
    Integer userId;

    /**
     * 审批人拥有的权重, 权重越高, 审批次序越靠前
     */
    @Field(field = "appro_weight")
    Integer approWeight;

    /**
     * 审批人可以审批的课程的代码
     */
    @Field(field = "course_code")
    String courseCode;
}
