package com.github.akagawatsurunaki.novappro.mapper.impl;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import com.github.akagawatsurunaki.novappro.mapper.CourseApplicationMapper;
import com.github.akagawatsurunaki.novappro.model.approval.CourseApplication;
import com.github.akagawatsurunaki.novappro.model.course.Course;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CourseApplicationMapperImpl implements CourseApplicationMapper {

    @Override
    public Pair<VerifyCode, CourseApplication> insertCourseApplication(@NonNull CourseApplication courseApplication) {

        try {

            List<Course> appliedCourses = courseApplication.getAppliedCourses();

            List<Entity> entities = new ArrayList<>();

            for (Course course : appliedCourses) {

                var e = Entity.create(MySQLFieldName.TABLE_NAME.getName())
                        .set(MySQLFieldName.CODE.getName(), courseApplication.getCode())
                        .set(MySQLFieldName.COURSE_CODE.getName(), course.getCode());
                entities.add(e);

            }

            int[] insertedRows = Db.use().insert(entities);
            int sum = Arrays.stream(insertedRows).sum();

            if (sum == appliedCourses.size()) {
                return new ImmutablePair<>(VerifyCode.MAPPER_OK, courseApplication);
            }

            return new ImmutablePair<>(VerifyCode.ZERO_ROW_INSERTED, courseApplication);

        } catch (SQLException e) {
            e.printStackTrace();
            return new ImmutablePair<>(VerifyCode.SQL_EXCEPTION, courseApplication);
        }
    }

    @AllArgsConstructor
    enum MySQLFieldName {
        TABLE_NAME("course_application"),
        CODE("code"),
        COURSE_CODE("course_code");

        @Getter
        public final String name;

    }
}
