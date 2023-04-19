package com.github.akagawatsurunaki.novappro.mapper.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import com.github.akagawatsurunaki.novappro.annotation.Database;
import com.github.akagawatsurunaki.novappro.constant.VerifyCode;
import com.github.akagawatsurunaki.novappro.mapper.UserMapper;
import com.github.akagawatsurunaki.novappro.model.database.User;
import com.github.akagawatsurunaki.novappro.util.EntityUtil;
import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserMapperImpl implements UserMapper {

    @Getter
    private static final UserMapperImpl instance = new UserMapperImpl();

    @Database
    private static Connection connection;
    //    @Override
//    @Deprecated
//    public Pair<VerifyCode.Mapper, User> getUserById(int id) {
//        try (
//                PreparedStatement statement =
//                        connection.prepareStatement(
//                                "select `id`, `username`, `raw_password`, `type` from user where user.id = ?;"
//                        )
//        ) {
//            statement.setInt(1, id);
//            ResultSet resultSet = statement.executeQuery();
//            List<User> users = parseResultSet(resultSet);
//            if (users.isEmpty()) {
//                return new ImmutablePair<>(VerifyCode.Mapper.NO_SUCH_ENTITY, null);
//            }
//            resultSet.close();
//            return new ImmutablePair<>(VerifyCode.Mapper.OK, users.get(0));
//        } catch (SQLException e) {
//            return null;
//        }
//    }
    String selectUserById = " SELECT * FROM `user` WHERE `user`.`id` = ?;";

    public static List<User> parseResultSet(ResultSet rs) {
        List<User> result = new ArrayList<>();

        try {
            while (rs.next()) {

                Integer id = rs.getInt("id");
                String username = rs.getString("username");
                String rawPassword = rs.getString("raw_password");
                String strType = rs.getString("type");
                User.Type type = User.Type.getType(strType);
                User user = new User(id, username, rawPassword, type);

                result.add(user);
                return result;
            }
        } catch (Exception ignored) {
            ;
        }
        return result;
    }

    @Override
    public Pair<VerifyCode.Mapper, List<User>> getUsers() {
        try {
            Statement statement;
            ResultSet resultSet;
            List<User> users = new ArrayList<>();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select id, username, raw_password, type from user");
            users = parseResultSet(resultSet);
            return new ImmutablePair<>(VerifyCode.Mapper.OK, users);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ImmutablePair<>(VerifyCode.Mapper.SQL_EXCEPTION, null);
        }
    }

    private User parseUserEntity(Entity entity) {
        User user = new User();
        user.setId(entity.getInt(StrUtil.toUnderlineCase(User.Fields.id)));
        user.setUsername(entity.getStr(StrUtil.toUnderlineCase(User.Fields.username)));
        user.setRawPassword(entity.getStr(StrUtil.toUnderlineCase(User.Fields.rawPassword)));
        String typeStr = entity.getStr(StrUtil.toUnderlineCase(User.Fields.type));
        User.Type type = User.Type.getType(typeStr);
        user.setType(type);
        return user;
    }

    @Override
    public Pair<VerifyCode.Mapper, User> insertUser(@NonNull User user) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO `user` (`username`, `raw_password`, `type`) VALUES (?, ?, ?);"
            );
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getRawPassword());
            statement.setString(3, user.getType().getChineseName());
            if (statement.execute()) {
                return new ImmutablePair<>(VerifyCode.Mapper.OK, user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ImmutablePair<>(VerifyCode.Mapper.SQL_EXCEPTION, user);
    }

    @Override
    public Pair<VerifyCode.Mapper, User> selectUserById(@NonNull Integer id) {
        try {
            var entities = Db.use().query(selectUserById, id);

            if (entities.size() != 1) {
                return new ImmutablePair<>(VerifyCode.Mapper.NO_SUCH_ENTITY, null);
            }

            var entity = entities.get(0);
            var result = EntityUtil.parseEntity(User.class, entity);

            return new ImmutablePair<>(VerifyCode.Mapper.OK, result);

        } catch (SQLException e) {
            e.printStackTrace();
            return new ImmutablePair<>(VerifyCode.Mapper.SQL_EXCEPTION, null);
        }
    }

}
