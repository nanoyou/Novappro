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

    @ChineseFieldName(chineseFieldName = "课程代码")
    String code;

    @ChineseFieldName(chineseFieldName = "课程名称")
    String name;

    @ChineseFieldName(chineseFieldName = "学分")
    Integer credit;

    @ChineseFieldName(chineseFieldName = "课程序号")
    String serialNumber;

    @ChineseFieldName(chineseFieldName = "教师(组)")
    List<Integer> teacherIds;

    @ChineseFieldName(chineseFieldName = "线上联系方式")
    String onlineContactWay;

    @ChineseFieldName(chineseFieldName = "备注")
    String comment;

}
