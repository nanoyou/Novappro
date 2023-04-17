package com.github.akagawatsurunaki.novappro.model.approval;

import com.github.akagawatsurunaki.novappro.annotation.Field;
import com.github.akagawatsurunaki.novappro.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;

import java.util.Date;


@FieldNameConstants
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(table = "courses_approval")
public class CourseApplication {

    @Field(field = "flow_no")
    String flowNo;

    @Field(field = "add_user_id")
    Integer addUserId;

    @Field(field = "add_time")
    Date addTime;

    @Field(field = "appro_courses")
    String approCourseIds;

}
