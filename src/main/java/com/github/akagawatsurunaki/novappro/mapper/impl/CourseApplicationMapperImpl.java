package com.github.akagawatsurunaki.novappro.mapper.impl;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;
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
    String selectByUserIdSQL = "SELECT * FROM `courses_approval` WHERE `courses_approval`.`add_user_id` = ?;";

    String selectByFlowNoSQL = "SELECT * FROM `courses_approval` WHERE `courses_approval`.`flow_no` = ?;";

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

            var entities = Db.use().query(selectByUserIdSQL, userId);
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

    @Override
    public Pair<VerifyCode.Mapper, CourseApplication> selectByFlowNo(@NonNull String flowNo) {
        try {

            var entities = Db.use().query(selectByFlowNoSQL, flowNo);

            if (entities.isEmpty()) {
                return new ImmutablePair<>(VerifyCode.Mapper.NO_SUCH_ENTITY, null);
            }

            var entity = entities.get(0);
            var courseApplication = EntityUtil.parseEntity(CourseApplication.class, entity);
            return new ImmutablePair<>(VerifyCode.Mapper.OK, courseApplication);

        } catch (SQLException e) {
            e.printStackTrace();
            return new ImmutablePair<>(VerifyCode.Mapper.SQL_EXCEPTION, null);
        }
    }

    @Override
    public Pair<VerifyCode.Mapper, CourseApplication> update(@NonNull CourseApplication courseApplication) {
        // TODO: 准备更新实体
        try {
            var entity = EntityUtil.getEntity(courseApplication);

            if (entity == null) {
                return new ImmutablePair<>(VerifyCode.Mapper.FAILED_TO_PARSE_ENTITY, courseApplication);
            }
            var tableName = EntityUtil.getTableName(courseApplication);

            if (tableName == null) {
                return new ImmutablePair<>(VerifyCode.Mapper.FAILED_TO_PARSE_ENTITY, courseApplication);
            }
            // TODO: TEST

            Db.use().update(entity, Entity.create(EntityUtil.getTableName(courseApplication)).set("flow_no",
                    courseApplication.getFlowNo()));
            return new ImmutablePair<>(VerifyCode.Mapper.OK, courseApplication);
        } catch (SQLException e) {
            return new ImmutablePair<>(VerifyCode.Mapper.SQL_EXCEPTION, courseApplication);
        } catch (IllegalAccessException e) {
            return new ImmutablePair<>(VerifyCode.Mapper.FAILED_TO_PARSE_ENTITY, courseApplication);
        }
    }

    @Override
    public Pair<VerifyCode.Mapper, CourseApplication> update(@NonNull List<CourseApplication> courseApplications) {
        return null;
    }


    @Override
    public Pair<VerifyCode.Mapper, CourseApplication> delete(@NonNull CourseApplication courseApplication) {
        // TODO: TEST

        try {
            var entity = EntityUtil.getEntity(courseApplication);
            int row = Db.use().del(entity);
            if (row != 1) {
                return new ImmutablePair<>(VerifyCode.Mapper.OTHER_EXCEPTION, courseApplication);
            }
            return new ImmutablePair<>(VerifyCode.Mapper.OK, courseApplication);
        } catch (SQLException e) {
            return new ImmutablePair<>(VerifyCode.Mapper.SQL_EXCEPTION, courseApplication);
        } catch (IllegalAccessException e) {
            return new ImmutablePair<>(VerifyCode.Mapper.FAILED_TO_PARSE_ENTITY, courseApplication);
        }
    }
}
