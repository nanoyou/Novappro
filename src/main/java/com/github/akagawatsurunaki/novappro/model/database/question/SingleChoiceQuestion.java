package com.github.akagawatsurunaki.novappro.model.database.question;

import com.github.akagawatsurunaki.novappro.annotation.ChineseFieldName;
import com.github.akagawatsurunaki.novappro.constant.Constant;
import lombok.Data;
import java.util.List;

@Data
@ChineseFieldName(value = "单选题")
public class SingleChoiceQuestion implements Question {

    @ChineseFieldName(value = "单选题ID")
    Integer id;

    @ChineseFieldName(value = "题目类型")
    String type = "多选题";

    @ChineseFieldName(value = "问题名称")
    String title;

    @ChineseFieldName(value = "问题描述")
    String description;

    @ChineseFieldName(value = "单选题内容列表")
    List<TextItem> textItems;

    @ChineseFieldName(value = "已选择文本项目ID")
    Integer selectedTextItemId = Constant.NO_SELECTED_ITEM_ID;

    @ChineseFieldName(value = "是否必填")
    Boolean isRequired;
}
