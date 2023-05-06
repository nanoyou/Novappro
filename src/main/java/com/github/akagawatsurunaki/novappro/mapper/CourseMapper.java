package com.github.akagawatsurunaki.novappro.mapper;

import com.github.akagawatsurunaki.novappro.constant.VC;
import com.github.akagawatsurunaki.novappro.model.database.course.Course;
import lombok.NonNull;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CourseMapper {

    List<Course> selectAllCourses();

    Course selectCourseByCode(@Param("code") @NonNull String code);

    List<Course> selectCourses( @NonNull List<String> codes);

}
