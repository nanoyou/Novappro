package com.github.akagawatsurunaki.novappro.mapper.impl;

import cn.hutool.db.Db;
import com.github.akagawatsurunaki.novappro.constant.VC;
import com.github.akagawatsurunaki.novappro.mapper.UserMapper;
import com.github.akagawatsurunaki.novappro.model.database.User;
import com.github.akagawatsurunaki.novappro.util.EntityUtil;
import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserMapperImpl implements UserMapper {

    @Getter
    private static final UserMapperImpl instance = new UserMapperImpl();

    @Override
    public Pair<VC.Mapper, List<User>> selectAllUsers() {
        try {

            String sql = "SELECT * FROM user";
            var entities = Db.use().query(sql);

            // 空查询
            if (entities.isEmpty()) {
                return new ImmutablePair<>(VC.Mapper.EMPTY_ENTITY_LIST, null);
            }

            List<User> result = new ArrayList<>();

            // 解析每一个用户实体类
            entities.forEach(entity -> result.add(EntityUtil.parseEntity(User.class, entity)));

            // 如果查询到的Entity个数与解析后User对象个数的不一致
            if (result.size() != entities.size()) {
                return new ImmutablePair<>(VC.Mapper.FAILED_TO_PARSE_ENTITY, null);
            }

            return new ImmutablePair<>(VC.Mapper.OK, result);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ImmutablePair<>(VC.Mapper.SQL_EXCEPTION, null);
        }
    }

    @Override
    public Pair<VC.Mapper, User> insertUser(@NonNull User user) {

        try {

            var entity = EntityUtil.getEntity(user);

            var rows = Db.use().insert(entity);

            if (rows != 1) {
                return new ImmutablePair<>(VC.Mapper.OTHER_EXCEPTION, user);
            }

            return new ImmutablePair<>(VC.Mapper.OK, user);

        } catch (SQLException e) {
            e.printStackTrace();
            return new ImmutablePair<>(VC.Mapper.SQL_EXCEPTION, user);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    @Override
    public Pair<VC.Mapper, User> selectUserById(@NonNull Integer id) {
        try {
            var sql = "SELECT * FROM `user` WHERE `user`.`id` = ?;";
            var entities = Db.use().query(sql, id);

            if (entities.isEmpty()) {
                return new ImmutablePair<>(VC.Mapper.NO_SUCH_ENTITY, null);
            }

            if (entities.size() > 1) {
                return new ImmutablePair<>(VC.Mapper.MORE_THAN_ONE_ENTITY, null);
            }

            var entity = entities.get(0);
            var result = EntityUtil.parseEntity(User.class, entity);

            return new ImmutablePair<>(VC.Mapper.OK, result);

        } catch (SQLException e) {
            e.printStackTrace();
            return new ImmutablePair<>(VC.Mapper.SQL_EXCEPTION, null);
        }
    }

}
