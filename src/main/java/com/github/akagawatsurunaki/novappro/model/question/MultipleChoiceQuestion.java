package com.github.akagawatsurunaki.novappro.model.question;

import com.github.akagawatsurunaki.novappro.annotation.ChineseFieldName;
import lombok.Data;

import java.util.List;

@Data
public class MultipleChoiceQuestion implements Question {

    @ChineseFieldName(chineseFieldName = "多选题ID")
    Integer id;

    @ChineseFieldName(chineseFieldName = "问题名称")
    String title;

    @ChineseFieldName(chineseFieldName = "问题描述")
    String description;

    @ChineseFieldName(chineseFieldName = "多选题内容列表")
    List<TextItem> textItems;

    @ChineseFieldName(chineseFieldName = "已选择文本项目ID列表")
    List<Integer> selectedTextItemId;

    @ChineseFieldName(chineseFieldName = "是否必填")
    Boolean isRequired;
}
