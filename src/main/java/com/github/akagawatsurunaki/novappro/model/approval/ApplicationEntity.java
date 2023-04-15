package com.github.akagawatsurunaki.novappro.model.approval;

import com.github.akagawatsurunaki.novappro.annotation.ChineseFieldName;
import com.github.akagawatsurunaki.novappro.interfase.HasChineseField;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;

@Data
@ToString
public class ApplicationEntity implements HasChineseField{

    @ChineseFieldName(value = "申请实体代码")
    String code;

    @ChineseFieldName(value = "申请类型")
    ApplicationType applicationType;

    @ChineseFieldName(value = "申请人ID")
    Integer applicantId;

    @FieldNameConstants
    public enum ApplicationType implements HasChineseField {
        @ChineseFieldName(value = "课程申请")
        COURSE,

    }
}
