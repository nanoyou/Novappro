package com.github.akagawatsurunaki.novappro.mapper;

import com.github.akagawatsurunaki.novappro.model.course.Course;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public interface CourseMapper {

    Pair<VerifyCode, List<Course>> selectAllCourses();

    enum VerifyCode{
        OK,
        FAILED_TO_SELECT,
    }
}
