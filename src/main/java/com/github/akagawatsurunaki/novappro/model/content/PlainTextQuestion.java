package com.github.akagawatsurunaki.novappro.model.content;

import com.github.akagawatsurunaki.novappro.annotation.ChineseFieldName;
import lombok.Data;

@Data
public class PlainTextQuestion implements Question {

    @ChineseFieldName(chineseFieldName = "纯文本题ID")
    Integer id;

    @ChineseFieldName(chineseFieldName = "问题名称")
    String title;

    @ChineseFieldName(chineseFieldName = "问题描述")
    String description;

    @ChineseFieldName(chineseFieldName = "纯文本内容")
    String plainText;

    @ChineseFieldName(chineseFieldName = "是否必填")
    boolean isRequired;
}
