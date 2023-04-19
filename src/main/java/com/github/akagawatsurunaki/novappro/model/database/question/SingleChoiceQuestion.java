package com.github.akagawatsurunaki.novappro.model.database.question;

import com.github.akagawatsurunaki.novappro.annotation.ZhField;
import com.github.akagawatsurunaki.novappro.constant.Constant;
import lombok.Data;
import java.util.List;

@Data
@ZhField(value = "单选题")
public class SingleChoiceQuestion implements Question {

    @ZhField(value = "单选题ID")
    Integer id;

    @ZhField(value = "题目类型")
    String type = "多选题";

    @ZhField(value = "问题名称")
    String title;

    @ZhField(value = "问题描述")
    String description;

    @ZhField(value = "单选题内容列表")
    List<TextItem> textItems;

    @ZhField(value = "已选择文本项目ID")
    Integer selectedTextItemId = Constant.NO_SELECTED_ITEM_ID;

    @ZhField(value = "是否必填")
    Boolean isRequired;
}
