package com.github.akagawatsurunaki.novappro.service;

import com.github.akagawatsurunaki.novappro.mapper.CourseMapper;
import com.github.akagawatsurunaki.novappro.mapper.impl.CourseMapperImpl;
import com.github.akagawatsurunaki.novappro.model.course.Course;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class CourseService {

    @Getter
    private static final CourseService instance = new CourseService();

    private static final CourseMapper COURSE_MAPPER = CourseMapperImpl.getInstance();

    public Pair<VerifyCode, List<Course>> getAllCourses() {

        Pair<CourseMapper.VerifyCode, List<Course>> verifyCodeListPair = COURSE_MAPPER.selectAllCourses();
        if (verifyCodeListPair.getLeft() == CourseMapper.VerifyCode.SQL_OK) {
            List<Course> courses = verifyCodeListPair.getRight();
            if (courses.isEmpty()) {
                return new ImmutablePair<>(VerifyCode.SERVICE_OK_BUT_EMPTY_COURSES_LIST, courses);
            }
            return new ImmutablePair<>(VerifyCode.SERVICE_OK, courses);
        }
        return new ImmutablePair<>(VerifyCode.SERVICE_ERROR, null);
    }

    public Pair<VerifyCode, List<Course>> getCoursesByCodes(@NonNull List<String> codes) {

        List<Course> courses = new ArrayList<>();
        boolean noSuchCourse = false;

        for (String code : codes) {
            CourseMapper.VerifyCode verifyCode = COURSE_MAPPER.selectCourseByCode(code).getLeft();
            Course course = COURSE_MAPPER.selectCourseByCode(code).getRight();

            if (verifyCode == CourseMapper.VerifyCode.SQL_OK) {
                courses.add(course);
            } else if (verifyCode == CourseMapper.VerifyCode.NO_SUCH_COURSE) {
                noSuchCourse = true;
            } else if (verifyCode == CourseMapper.VerifyCode.FAILED_TO_SELECT) {
                return new ImmutablePair<>(VerifyCode.SERVICE_ERROR, null);
            }
        }

        // 部分用户可能没有被返回
        if (noSuchCourse){
            return new ImmutablePair<>(VerifyCode.CAN_NOT_FIND_SOME_ENTITIES, courses);
        }

        return new ImmutablePair<>(VerifyCode.SERVICE_OK, courses);
    }

    public enum VerifyCode {
        SERVICE_OK,
        SERVICE_OK_BUT_EMPTY_COURSES_LIST,
        SERVICE_ERROR,
        CAN_NOT_FIND_SOME_ENTITIES,
    }

}
