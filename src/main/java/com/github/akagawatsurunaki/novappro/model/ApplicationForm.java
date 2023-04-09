package com.github.akagawatsurunaki.novappro.model;

import com.github.akagawatsurunaki.novappro.annotation.ChineseFieldName;
import com.github.akagawatsurunaki.novappro.model.question.Question;

import java.util.List;

/**
 * 申请表
 */
public class ApplicationForm {

    @ChineseFieldName(chineseFieldName = "申请表ID")
    Integer id;

    @ChineseFieldName(chineseFieldName = "申请人ID")
    Integer applicantId;

    @ChineseFieldName(chineseFieldName = "问题列表")
    List<Question> questions;
}
