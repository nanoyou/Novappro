package com.github.akagawatsurunaki.novappro.service;

import com.github.akagawatsurunaki.novappro.constant.VerifyCode;
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

    public Pair<VerifyCode.Service, List<Course>> getAllCourses() {

        var verifyCodeListPair = COURSE_MAPPER.selectAllCourses();
        // 如果校验正确
        if (verifyCodeListPair.getLeft() == VerifyCode.Mapper.OK) {
            List<Course> courses = verifyCodeListPair.getRight();
            return new ImmutablePair<>(VerifyCode.Service.OK, courses);
        }
        return new ImmutablePair<>(VerifyCode.Service.ERROR, null);
    }

    public Pair<VerifyCode.Service, List<Course>> getCoursesByCodes(@NonNull List<String> codes) {

        List<Course> courses = new ArrayList<>();

        for (String code : codes) {
            var verifyCode = COURSE_MAPPER.selectCourseByCode(code).getLeft();
            Course course = COURSE_MAPPER.selectCourseByCode(code).getRight();

            if (verifyCode != VerifyCode.Mapper.OK) {
                return new ImmutablePair<>(VerifyCode.Service.ERROR, null);
            }
            courses.add(course);
        }

        return new ImmutablePair<>(VerifyCode.Service.OK, courses);
    }

}
