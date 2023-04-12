package com.github.akagawatsurunaki.novappro.mapper;

import com.github.akagawatsurunaki.novappro.model.course.Course;
import lombok.NonNull;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public interface CourseMapper {

    Pair<VerifyCode, List<Course>> selectAllCourses();

    Pair<VerifyCode, Course> selectCourseByCode(@NonNull String code);

    enum VerifyCode{
        SQL_OK,
        FAILED_TO_SELECT,
        NO_SUCH_COURSE,
    }
}
