package com.github.akagawatsurunaki.novappro.mapper.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import com.github.akagawatsurunaki.novappro.mapper.CourseMapper;
import com.github.akagawatsurunaki.novappro.model.course.Course;
import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.math.BigDecimal;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CourseMapperImpl implements CourseMapper {

    @Getter
    private static final CourseMapperImpl instance = new CourseMapperImpl();

    @Getter
    private static  final  UserMapperImpl USER_MAPPER = UserMapperImpl.getInstance();

    String selectAllCoursesSQL = "mysql/select_all_courses.sql";
    @Override
    public Pair<VerifyCode, List<Course>> selectAllCourses() {

        try {
            URL resource = ResourceUtil.getResource(selectAllCoursesSQL);
            List<Entity> courseEntities = Db.use().query(FileUtil.readString(resource, StandardCharsets.UTF_8));

            List<Course> courses = new ArrayList<>();
            courseEntities.forEach(entity -> {
                Course course = parseCourseEntity(entity);
                courses.add(course);
            });

            return new ImmutablePair<>(VerifyCode.SQL_OK, courses);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ImmutablePair<>(VerifyCode.FAILED_TO_SELECT, null);
        }
    }

    String selectCourseByCodeSQL = "mysql/select_course_by_code.sql";
    @Override
    public Pair<VerifyCode, Course> selectCourseByCode(@NonNull String code) {

        try {
            URL resource = ResourceUtil.getResource(selectCourseByCodeSQL);
            List<Entity> courseEntities = Db.use().query(FileUtil.readString(resource, StandardCharsets.UTF_8), code);

            if (courseEntities == null){
                return new ImmutablePair<>(VerifyCode.NO_SUCH_COURSE, null);
            }
            Course course = parseCourseEntity(courseEntities.get(0));
            return new ImmutablePair<>(VerifyCode.SQL_OK, course);
        } catch (SQLException e) {
            return new ImmutablePair<>(VerifyCode.FAILED_TO_SELECT, null);
        }
    }

    private Course parseCourseEntity(Entity entity) {
        Course course = new Course();
        course.setCode((String) entity.get(StrUtil.toUnderlineCase(Course.Fields.code)));
        course.setName((String) entity.get(StrUtil.toUnderlineCase(Course.Fields.name)));
        course.setCredit((BigDecimal) entity.get(StrUtil.toUnderlineCase(Course.Fields.credit)));
        course.setSerialNumber(((String) entity.get(StrUtil.toUnderlineCase(Course.Fields.serialNumber))));
        course.setOnlineContactWay((String) entity.get(StrUtil.toUnderlineCase(Course.Fields.onlineContactWay)));
        course.setComment((String) entity.get(StrUtil.toUnderlineCase(Course.Fields.comment)));
        course.setTeachers(
                Arrays.stream(
                        ((String) entity.get(StrUtil.toUnderlineCase(
                                Course.Fields.teachers))).split(", ")).toList());
        return course;
    }


}
