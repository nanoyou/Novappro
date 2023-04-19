package com.github.akagawatsurunaki.novappro.model.database.course;

import com.github.akagawatsurunaki.novappro.annotation.ChineseFieldName;
import com.github.akagawatsurunaki.novappro.constant.Constant;
import com.github.akagawatsurunaki.novappro.interfase.HasChineseField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
public class Course implements HasChineseField {

    @ChineseFieldName(value = "课程代码", description = "11位的字符串")
    String code;

    @ChineseFieldName(value = "课程名称", description = "最长" + Constant.MAX_LEN_COURSE_NAME + "个字符")
    String name;

    @ChineseFieldName(value = "学分")
    BigDecimal credit;

    @ChineseFieldName(value = "课程序号", description = "最长" + Constant.MAX_LEN_COURSE_SERIAL_NUMBER + "个字符")
    String serialNumber;

    @ChineseFieldName(value = "教师组姓名", description = "至少有1位教师")
    List<String> teachers;

    @ChineseFieldName(value = "线上联系方式", description = "最多" + Constant.MAX_LEN_COURSE_ONLINE_CONTACT_WAY + "个字符")
    String onlineContactWay;

    @ChineseFieldName(value = "备注", description = "最多" + Constant.MAX_LEN_COURSE_COMMENT + "个字符")
    String comment;
}
