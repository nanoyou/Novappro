package com.github.akagawatsurunaki.novappro.model.approval;

import com.github.akagawatsurunaki.novappro.annotation.ChineseFieldName;
import com.github.akagawatsurunaki.novappro.model.course.Course;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class CourseApplication extends ApplicationEntity {

    @ChineseFieldName(value = "申请的课程", description = "可以为多个。")
    List<Course> appliedCourses;

}
