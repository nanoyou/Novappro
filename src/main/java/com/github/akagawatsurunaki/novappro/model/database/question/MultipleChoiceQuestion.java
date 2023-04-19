package com.github.akagawatsurunaki.novappro.model.database.question;

import com.github.akagawatsurunaki.novappro.annotation.ChineseFieldName;
import lombok.Data;

import java.util.List;

@Data
public class MultipleChoiceQuestion implements Question {

    @ChineseFieldName(value = "多选题ID")
    Integer id;

    @ChineseFieldName(value = "题目类型")
    String type = "多选题";

    @ChineseFieldName(value = "问题名称")
    String title;

    @ChineseFieldName(value = "问题描述")
    String description;

    @ChineseFieldName(value = "多选题内容列表")
    List<TextItem> textItems;

    @ChineseFieldName(value = "已选择文本项目ID列表")
    List<Integer> selectedTextItemId;

    @ChineseFieldName(value = "是否必填")
    Boolean isRequired;
}
