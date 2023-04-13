package com.github.akagawatsurunaki.novappro.mapper.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import com.github.akagawatsurunaki.novappro.mapper.ApplicationEntityMapper;
import com.github.akagawatsurunaki.novappro.mapper.CourseMapper;
import com.github.akagawatsurunaki.novappro.model.approval.ApplicationEntity;
import com.github.akagawatsurunaki.novappro.model.course.Course;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ApplicationEntityMapperImpl implements ApplicationEntityMapper {

    @AllArgsConstructor
    enum MySQLFieldName {
        TABLE_NAME("application_entity"),
        CODE("code"),
        APPLICATION_TYPE("application_type"),
        APPLICATION_ID("applicant_id");

        @Getter
        public final String name;

    }

    @Override
    public Pair<VerifyCode, ApplicationEntity> insertApplicationEntity(@NonNull ApplicationEntity applicationEntity) {
        try {

            int insertedRows = Db.use().insert(
                    Entity.create(
                                    MySQLFieldName.TABLE_NAME.getName()
                            )
                            .set(MySQLFieldName.CODE.getName(), applicationEntity.getCode())
                            .set(MySQLFieldName.APPLICATION_TYPE.getName(), applicationEntity.getApplicationType().name())
                            .set(MySQLFieldName.APPLICATION_ID.getName(), applicationEntity.getApplicantId())
            );

            if (insertedRows > 0) {
                return new ImmutablePair<>(VerifyCode.MAPPER_OK, applicationEntity);
            }
            return new ImmutablePair<>(VerifyCode.ZERO_ROW_INSERTED, applicationEntity);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ImmutablePair<>(VerifyCode.SQL_EXCEPTION, applicationEntity);
        }
    }
}
