package com.github.akagawatsurunaki.novappro.model.content;

import com.github.akagawatsurunaki.novappro.annotation.ChineseFieldName;
import com.github.akagawatsurunaki.novappro.constant.Constant;
import lombok.Data;
import java.util.List;

@Data
public class SingleChoiceQuestion implements Question {

    @ChineseFieldName(chineseFieldName = "单选题ID")
    Integer id;

    @ChineseFieldName(chineseFieldName = "问题名称")
    String title;

    @ChineseFieldName(chineseFieldName = "问题描述")
    String description;

    @ChineseFieldName(chineseFieldName = "单选题内容列表")
    List<TextItem> textItems;

    @ChineseFieldName(chineseFieldName = "已选择文本项目ID")
    Integer selectedTextItemId = Constant.NO_SELECTED_ITEM_ID;

    @ChineseFieldName(chineseFieldName = "是否必填")
    boolean isRequired;
}
