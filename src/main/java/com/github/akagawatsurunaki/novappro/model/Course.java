package com.github.akagawatsurunaki.novappro.model;

import com.github.akagawatsurunaki.novappro.annotation.ChineseFieldName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {

    @ChineseFieldName(value = "课程代码")
    String code;

    @ChineseFieldName(value = "课程名称")
    String name;

    @ChineseFieldName(value = "学分")
    Integer credit;

    @ChineseFieldName(value = "课程序号")
    String serialNumber;

    @ChineseFieldName(value = "教师(组)")
    List<Integer> teacherIds;

    @ChineseFieldName(value = "线上联系方式")
    String onlineContactWay;

    @ChineseFieldName(value = "备注")
    String comment;

}
