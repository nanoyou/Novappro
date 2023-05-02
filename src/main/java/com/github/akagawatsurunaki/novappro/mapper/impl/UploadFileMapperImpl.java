package com.github.akagawatsurunaki.novappro.mapper.impl;

import cn.hutool.db.Db;
import com.github.akagawatsurunaki.novappro.constant.VC;
import com.github.akagawatsurunaki.novappro.mapper.UploadFileMapper;
import com.github.akagawatsurunaki.novappro.model.database.User;
import com.github.akagawatsurunaki.novappro.model.database.file.UploadFile;
import com.github.akagawatsurunaki.novappro.util.EntityUtil;
import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.sql.SQLException;

public class UploadFileMapperImpl implements UploadFileMapper {

    @Getter
    private static final UploadFileMapperImpl instance = new UploadFileMapperImpl();

    @Override
    public Pair<VC.Mapper, UploadFile> selectByFlowNo(@NonNull String flowNo) {
        try {
            var sql = "SELECT * FROM upload_file WHERE upload_file.flow_no = ?;";
            var entities = Db.use().query(sql, flowNo);

            if (entities.isEmpty()) {
                return new ImmutablePair<>(VC.Mapper.NO_SUCH_ENTITY, null);
            }

            if (entities.size() > 1) {
                return new ImmutablePair<>(VC.Mapper.MORE_THAN_ONE_ENTITY, null);
            }

            var entity = entities.get(0);
            var result = EntityUtil.parseEntity(UploadFile.class, entity);

            return new ImmutablePair<>(VC.Mapper.OK, result);

        } catch (SQLException e) {
            e.printStackTrace();
            return new ImmutablePair<>(VC.Mapper.SQL_EXCEPTION, null);
        }
    }

    @Override
    public Pair<VC.Mapper, UploadFile> insert(@NonNull UploadFile uploadFile) {
        try {

            var entity = EntityUtil.getEntity(uploadFile);

            var rows = Db.use().insert(entity);

            if (rows != 1) {
                return new ImmutablePair<>(VC.Mapper.OTHER_EXCEPTION, uploadFile);
            }

            return new ImmutablePair<>(VC.Mapper.OK, uploadFile);

        } catch (SQLException e) {
            e.printStackTrace();
            return new ImmutablePair<>(VC.Mapper.SQL_EXCEPTION, uploadFile);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return new ImmutablePair<>(VC.Mapper.OTHER_EXCEPTION, uploadFile);
        }
    }
}
