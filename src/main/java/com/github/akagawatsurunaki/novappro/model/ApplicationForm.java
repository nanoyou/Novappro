package com.github.akagawatsurunaki.novappro.model;

import com.github.akagawatsurunaki.novappro.annotation.ChineseFieldName;
import com.github.akagawatsurunaki.novappro.model.question.Question;
import lombok.Data;

import java.util.List;

/**
 * 申请表
 */
@Data
public class ApplicationForm {

    @ChineseFieldName(value = "申请表ID")
    Integer id;

    @ChineseFieldName(value = "申请人ID")
    Integer applicantId;

    @ChineseFieldName(value = "问题列表")
    List<Question> questions;
}
