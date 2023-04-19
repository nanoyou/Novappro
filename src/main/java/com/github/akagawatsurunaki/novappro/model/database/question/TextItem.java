package com.github.akagawatsurunaki.novappro.model.database.question;

import com.github.akagawatsurunaki.novappro.annotation.ZhField;
import lombok.Data;

@Data
public class TextItem {

    @ZhField(value = "文本项目ID")
    Integer id;

    @ZhField(value = "纯文本")
    String plainText;
}
