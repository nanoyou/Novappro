package com.github.akagawatsurunaki.novappro.mapper.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import com.github.akagawatsurunaki.novappro.constant.VC;
import com.github.akagawatsurunaki.novappro.mapper.CourseMapper;
import com.github.akagawatsurunaki.novappro.model.database.course.Course;
import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CourseMapperImpl implements CourseMapper {
    @Override
    public List<Course> selectAllCourses() {
        return null;
    }

    @Override
    public Course selectCourseByCode(@NonNull String code) {
        return null;
    }

    @Override
    public List<Course> selectCourses(@NonNull List<String> codes) {
        return null;
    }

//
//    @Getter
//    private static final CourseMapperImpl instance = new CourseMapperImpl();
//
//    @Override
//    public List<Course> selectAllCourses() {
//
//        try {
//
//            final String sql = """
//                    SELECT
//                        ANY_VALUE ( `teacher_id` ) AS `teacher_id`,
//                        group_concat( `username` SEPARATOR ', ' ) AS `teachers`,
//                        ANY_VALUE ( `code` ) AS `code`,
//                        ANY_VALUE ( `name` ) AS `name`,
//                        ANY_VALUE ( `credit` ) AS `credit`,
//                        ANY_VALUE ( `serial_number` ) AS `serial_number`,
//                        ANY_VALUE ( `online_contact_way` ) AS `online_contact_way`,
//                        ANY_VALUE ( `comment` ) AS `comment`
//                    FROM
//                        ( SELECT `teacher_id`, `username`, `course_code` FROM `user` INNER JOIN `teaches` ON `user`.`id` = `teaches`.teacher_id ) AS teacher_name_table
//                            INNER JOIN course ON course.`code` = `course_code`
//                    GROUP BY
//                        `course_code`;""";
//
//            List<Entity> courseEntities = Db.use().query(sql);
//
//            List<Course> courses = new ArrayList<>();
//            courseEntities.forEach(entity -> {
//                Course course = parseCourseEntity(entity);
//                courses.add(course);
//            });
//
//            return new ImmutablePair<>(VC.Mapper.OK, courses);
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return new ImmutablePair<>(VC.Mapper.SQL_EXCEPTION, null);
//        }
//    }
//
//    @Override
//    public Course selectCourseByCode(@NonNull String code) {
//
//        try {
//            final String sql = """
//                    SELECT
//                        ANY_VALUE ( `teacher_id` ) AS `teacher_id`,
//                        group_concat( `username` SEPARATOR ', ' ) AS `teachers`,
//                        ANY_VALUE ( `code` ) AS `code`,
//                        ANY_VALUE ( `name` ) AS `name`,
//                        ANY_VALUE ( `credit` ) AS `credit`,
//                        ANY_VALUE ( `serial_number` ) AS `serial_number`,
//                        ANY_VALUE ( `online_contact_way` ) AS `online_contact_way`,
//                        ANY_VALUE ( `comment` ) AS `comment`
//                    FROM
//                        (
//                            SELECT
//                                *
//                            FROM
//                                ( SELECT * FROM course AS c WHERE c.`code` = ? ) AS tmp
//                                    INNER JOIN teaches AS tc ON tmp.`code` = tc.course_code
//                        ) AS course_tmp
//                            INNER JOIN `user` AS u ON u.id = course_tmp.`teacher_id`
//                    GROUP BY
//                        `code`;""";
//
//            List<Entity> courseEntities = Db.use().query(sql, code);
//
//            if (courseEntities == null || courseEntities.isEmpty()) {
//                return new ImmutablePair<>(VC.Mapper.NO_SUCH_ENTITY, null);
//            }
//
//            Course course = parseCourseEntity(courseEntities.get(0));
//
//            return new ImmutablePair<>(VC.Mapper.OK, course);
//        } catch (SQLException e) {
//            return new ImmutablePair<>(VC.Mapper.SQL_EXCEPTION, null);
//        }
//    }
//
//    @Override
//    public Pair<VC.Mapper, List<Course>> selectCourses(@NonNull List<String> codes) {
//
//        List<Course> result = new ArrayList<>();
//
//        if (codes.isEmpty()) {
//            return new ImmutablePair<>(VC.Mapper.OK, result);
//        }
//
//        for (String code : codes) {
//            var vc_course = selectCourseByCode(code);
//            if (vc_course.getLeft() == VC.Mapper.OK) {
//                result.add(vc_course.getRight());
//            } else if (vc_course.getLeft() == VC.Mapper.NO_SUCH_ENTITY) {
//                return new ImmutablePair<>(VC.Mapper.NO_SUCH_ENTITY, null);
//            } else {
//                return new ImmutablePair<>(VC.Mapper.OTHER_EXCEPTION, null);
//            }
//        }
//
//        return new ImmutablePair<>(VC.Mapper.OK, result);
//
//    }

//    private Course parseCourseEntity(Entity entity) {
//        Course course = new Course();
//        course.setCode((String) entity.get(StrUtil.toUnderlineCase(Course.Fields.code)));
//        course.setName((String) entity.get(StrUtil.toUnderlineCase(Course.Fields.name)));
//        course.setCredit((BigDecimal) entity.get(StrUtil.toUnderlineCase(Course.Fields.credit)));
//        course.setSerialNumber(((String) entity.get(StrUtil.toUnderlineCase(Course.Fields.serialNumber))));
//        course.setOnlineContactWay((String) entity.get(StrUtil.toUnderlineCase(Course.Fields.onlineContactWay)));
//        course.setComment((String) entity.get(StrUtil.toUnderlineCase(Course.Fields.comment)));
//        course.setTeachers(
//                Arrays.stream(
//                        ((String) entity.get(StrUtil.toUnderlineCase(
//                                Course.Fields.teachers))).split(", ")).toList());
//        return course;
//    }


}
