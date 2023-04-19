package com.github.akagawatsurunaki.novappro.model.database.question;

import com.github.akagawatsurunaki.novappro.annotation.ChineseFieldName;
import lombok.Data;

@Data
public class TextItem {

    @ChineseFieldName(value = "文本项目ID")
    Integer id;

    @ChineseFieldName(value = "纯文本")
    String plainText;
}
