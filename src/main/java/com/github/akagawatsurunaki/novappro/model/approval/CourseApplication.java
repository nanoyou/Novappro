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

    /**
     * CourseApplication所从属的审批流的唯一标志号码
     */
    @Field(field = "flow_no")
    String flowNo;

    /**
     * CourseApplication的申请人的ID
     */
    @Field(field = "add_user_id")
    Integer addUserId;

    /**
     * CourseApplication的申请时间, 即创建的日期
     */
    @Field(field = "add_time")
    Date addTime;

    /**
     * 课程申请CourseApplication的课程代码, 可能包含了多个课程代码
     * @implNote 本字段必须实现由String List转化为String的方法
     */
    @Field(field = "appro_courses")
    String approCourseIds;

}
