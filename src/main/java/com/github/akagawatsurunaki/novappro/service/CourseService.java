package com.github.akagawatsurunaki.novappro.service;

import com.github.akagawatsurunaki.novappro.mapper.CourseMapper;
import com.github.akagawatsurunaki.novappro.mapper.impl.CourseMapperImpl;
import com.github.akagawatsurunaki.novappro.model.course.Course;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

@NoArgsConstructor
public class CourseService {

    @Getter
    private static final CourseService instance = new CourseService();

    private static final CourseMapper COURSE_MAPPER = CourseMapperImpl.getInstance();

    public Pair<VerifyCode, List<Course>> getAllCourses(){

        Pair<CourseMapper.VerifyCode, List<Course>> verifyCodeListPair = COURSE_MAPPER.selectAllCourses();
        if(verifyCodeListPair.getLeft() == CourseMapper.VerifyCode.MAPPER_OK){
            List<Course> courses = verifyCodeListPair.getRight();
            if (courses.isEmpty()) {
                return new ImmutablePair<>(VerifyCode.SERVICE_OK_BUT_EMPTY_COURSES_LIST, courses);
            }
            return new ImmutablePair<>(VerifyCode.SERVICE_OK, courses);
        }
        return new ImmutablePair<>(VerifyCode.SERVICE_ERROR, null);
    }

    public enum VerifyCode{
        SERVICE_OK,
        SERVICE_OK_BUT_EMPTY_COURSES_LIST,
        SERVICE_ERROR,
    }

}
