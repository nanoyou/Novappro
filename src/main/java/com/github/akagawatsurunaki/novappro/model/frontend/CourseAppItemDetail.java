package com.github.akagawatsurunaki.novappro.model.frontend;

import com.github.akagawatsurunaki.novappro.annotation.ZhField;
import com.github.akagawatsurunaki.novappro.enumeration.ApprovalStatus;
import com.github.akagawatsurunaki.novappro.model.database.course.Course;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;

import java.util.Date;
import java.util.List;

@Data
@Builder
@ToString
@FieldNameConstants
public class CourseAppItemDetail {

    @ZhField(value = "审批流编号")
    String flowNo;

    @ZhField(value = "标题")
    String title;

    @ZhField(value = "申请人学号/工号")
    Integer applicantId;

    @ZhField(value = "申请人姓名")
    String applicantName;

    @ZhField(value = "申请时间")
    Date addTime;

    @ZhField(value = "当前审批状态")
    ApprovalStatus approStatus;

    @ZhField(value = "申请人提出申请的课程")
    List<Course> applCourses;

}
