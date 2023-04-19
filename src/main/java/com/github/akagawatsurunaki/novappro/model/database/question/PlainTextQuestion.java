package com.github.akagawatsurunaki.novappro.model.database.question;

import com.github.akagawatsurunaki.novappro.annotation.ZhField;
import lombok.Data;

@Data
public class PlainTextQuestion implements Question {

    @ZhField(value = "纯文本题ID")
    Integer id;

    @ZhField(value = "题目类型")
    String type = "纯文本题";

    @ZhField(value = "问题名称")
    String title;

    @ZhField(value = "问题描述")
    String description;

    @ZhField(value = "纯文本内容")
    String plainText;

    @ZhField(value = "是否必填")
    Boolean isRequired;
}
