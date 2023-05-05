package com.github.akagawatsurunaki.novappro.model.frontend;

import com.github.akagawatsurunaki.novappro.annotation.ZhField;
import com.github.akagawatsurunaki.novappro.enumeration.UserType;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

@Data
@FieldNameConstants
@Builder
public class ApprovalAuthorityItem {

    @ZhField(value = "审批人的ID")
    Integer approverId;

    @ZhField(value = "审批人的姓名")
    String approverName;

    @ZhField(value = "审批人的类型")
    UserType userType;

    /**
     * 审批人拥有的权重, 权重越高, 审批次序越靠前
     */
    @ZhField(value = "审批人的权重", desc = "权重越高，会优先进入审批链的头部部分。")
    Integer approWeight;

    /**
     * 审批人可以审批的课程的代码
     */
    @ZhField(value = "审批人可以审批的课程代码")
    String courseCode;

    @ZhField(value = "审批人可以审批的课程名称")
    String courseName;
}
