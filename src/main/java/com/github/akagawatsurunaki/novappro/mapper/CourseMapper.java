package com.github.akagawatsurunaki.novappro.mapper;

import com.github.akagawatsurunaki.novappro.constant.VerifyCode;
import com.github.akagawatsurunaki.novappro.model.course.Course;
import lombok.NonNull;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public interface CourseMapper {

    Pair<VerifyCode.Mapper, List<Course>> selectAllCourses();

    Pair<VerifyCode.Mapper, Course> selectCourseByCode(@NonNull String code);

}
