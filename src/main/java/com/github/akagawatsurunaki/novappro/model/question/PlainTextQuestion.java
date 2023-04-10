package com.github.akagawatsurunaki.novappro.model.question;

import com.github.akagawatsurunaki.novappro.annotation.ChineseFieldName;
import lombok.Data;

@Data
public class PlainTextQuestion implements Question {

    @ChineseFieldName(value = "纯文本题ID")
    Integer id;

    @ChineseFieldName(value = "题目类型")
    String type = "纯文本题";

    @ChineseFieldName(value = "问题名称")
    String title;

    @ChineseFieldName(value = "问题描述")
    String description;

    @ChineseFieldName(value = "纯文本内容")
    String plainText;

    @ChineseFieldName(value = "是否必填")
    Boolean isRequired;
}
