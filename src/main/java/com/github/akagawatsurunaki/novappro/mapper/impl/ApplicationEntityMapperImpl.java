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

    @Getter
    final static ApplicationEntityMapper instance = new ApplicationEntityMapperImpl();

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

    String selectApplicationEntityByCodeSQL= "/mysql/select_application_entity_by_code.sql";
    @Override
    public Pair<VerifyCode, ApplicationEntity> selectApplicationEntityByCode(@NonNull String code) {
        try {
            URL resource = ResourceUtil.getResource(selectApplicationEntityByCodeSQL);
            List<Entity> entities = Db.use().query(FileUtil.readString(resource, StandardCharsets.UTF_8), code);
            if(entities.isEmpty()){
                return new ImmutablePair<>(VerifyCode.NO_SUCH_ENTITY, null);
            }
            Entity entity = entities.get(0);
            ApplicationEntity result = new ApplicationEntity();
            result.setCode( entity.getStr(MySQLFieldName.CODE.getName()));
            result.setApplicationType(entity.getEnum(ApplicationEntity.ApplicationType.class, MySQLFieldName.APPLICATION_TYPE.getName()));
            result.setApplicantId(entity.getInt(MySQLFieldName.APPLICATION_ID.getName()));
            return new ImmutablePair<>(VerifyCode.MAPPER_OK, result);
        } catch (SQLException e) {
            return new ImmutablePair<>(VerifyCode.SQL_EXCEPTION, null);
        }
    }
}
