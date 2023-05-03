package com.github.akagawatsurunaki.novappro.model.database.course;

import com.github.akagawatsurunaki.novappro.annotation.ZhField;
import com.github.akagawatsurunaki.novappro.constant.Constant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
public class Course {

    @ZhField(value = "课程代码", desc = "11位的字符串")
    String code;

    @ZhField(value = "课程名称", desc = "最长" + Constant.MAX_LEN_COURSE_NAME + "个字符")
    String name;

    @ZhField(value = "学分")
    BigDecimal credit;

    @ZhField(value = "课程序号", desc = "最长" + Constant.MAX_LEN_COURSE_SERIAL_NUMBER + "个字符")
    String serialNumber;

    @ZhField(value = "教师组姓名", desc = "至少有1位教师")
    String teachers;

    @ZhField(value = "线上联系方式", desc = "最多" + Constant.MAX_LEN_COURSE_ONLINE_CONTACT_WAY + "个字符")
    String onlineContactWay;

    @ZhField(value = "备注", desc = "最多" + Constant.MAX_LEN_COURSE_COMMENT + "个字符")
    String comment;
}
