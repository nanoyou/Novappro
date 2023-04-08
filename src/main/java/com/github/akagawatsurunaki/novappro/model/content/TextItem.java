package com.github.akagawatsurunaki.novappro.model.content;

import com.github.akagawatsurunaki.novappro.annotation.ChineseFieldName;
import lombok.Data;

@Data
public class TextItem {

    @ChineseFieldName(chineseFieldName = "文本项目ID")
    int id;

    @ChineseFieldName(chineseFieldName = "纯文本")
    String plainText;
}
