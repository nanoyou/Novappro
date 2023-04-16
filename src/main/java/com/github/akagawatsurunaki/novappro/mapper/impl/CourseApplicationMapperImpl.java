package com.github.akagawatsurunaki.novappro.mapper.impl;

import cn.hutool.db.Db;
import com.github.akagawatsurunaki.novappro.constant.VerifyCode;
import com.github.akagawatsurunaki.novappro.mapper.CourseApplicationMapper;
import com.github.akagawatsurunaki.novappro.model.approval.CourseApplication;
import com.github.akagawatsurunaki.novappro.util.EntityUtil;
import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseApplicationMapperImpl implements CourseApplicationMapper {

    @Getter
    private static final CourseApplicationMapper instance = new CourseApplicationMapperImpl();
    String sql = "SELECT * FROM `courses_approval` WHERE `courses_approval`.`add_user_id` = ?;";

    @Override
    public Pair<VerifyCode.Mapper, CourseApplication> insert(@NonNull CourseApplication courseApplication) {
        try {
            int rows = Db.use().insert(EntityUtil.getEntity(courseApplication));
            if (rows > 0) {
                return new ImmutablePair<>(VerifyCode.Mapper.OK, courseApplication);
            }
            return new ImmutablePair<>(VerifyCode.Mapper.OTHER_EXCEPTION, courseApplication);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ImmutablePair<>(VerifyCode.Mapper.SQL_EXCEPTION, courseApplication);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return new ImmutablePair<>(VerifyCode.Mapper.OTHER_EXCEPTION, courseApplication);
        }
    }

    @Override
    public Pair<VerifyCode.Mapper, List<CourseApplication>> select(@NonNull Integer userId) {
        try {

            var entities = Db.use().query(sql, userId);
            List<CourseApplication> result = new ArrayList<>();

            entities.forEach(
                    entity -> {
                        var e = EntityUtil.parseEntity(CourseApplication.class, entity);
                        result.add(e);
                    }
            );

            return new ImmutablePair<>(VerifyCode.Mapper.OK, result);

        } catch (SQLException e) {
            e.printStackTrace();
            return new ImmutablePair<>(VerifyCode.Mapper.SQL_EXCEPTION, null);
        }
    }
}
