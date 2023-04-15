package com.github.akagawatsurunaki.novappro.model.approval;

import com.github.akagawatsurunaki.novappro.annotation.Field;
import com.github.akagawatsurunaki.novappro.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;

import java.util.Date;
import java.util.List;

@FieldNameConstants
@Data
@AllArgsConstructor
@ToString
@Table(table = "courses_approval")
public class CourseApproval {

    @Field(field = "flow_no")
    String flowNo;

    @Field(field = "add_user_id")
    Integer addUserId;

    @Field(field = "add_time")
    Date addTime;

    @Field(field = "appro_courses")
    List<String> approCourseIds;

}
