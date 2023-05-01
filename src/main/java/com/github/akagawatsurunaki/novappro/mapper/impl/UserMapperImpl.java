package com.github.akagawatsurunaki.novappro.mapper.impl;

import cn.hutool.db.Db;
import com.github.akagawatsurunaki.novappro.annotation.Database;
import com.github.akagawatsurunaki.novappro.constant.VC;
import com.github.akagawatsurunaki.novappro.enumeration.UserType;
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
    String selectUserById = " SELECT * FROM `user` WHERE `user`.`id` = ?;";

    public static List<User> parseResultSet(ResultSet rs) {
        List<User> result = new ArrayList<>();

        try {
            while (rs.next()) {

                Integer id = rs.getInt("id");
                String username = rs.getString("username");
                String rawPassword = rs.getString("raw_password");
                String strType = rs.getString("type");
                UserType type = UserType.valueOf(strType);
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
    public Pair<VC.Mapper, List<User>> getUsers() {
        try {
            Statement statement;
            ResultSet resultSet;
            List<User> users = new ArrayList<>();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select id, username, raw_password, type from user");
            users = parseResultSet(resultSet);
            return new ImmutablePair<>(VC.Mapper.OK, users);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ImmutablePair<>(VC.Mapper.SQL_EXCEPTION, null);
        }
    }

    @Override
    public Pair<VC.Mapper, User> insertUser(@NonNull User user) {

        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO `user` (`username`, `raw_password`, `type`) VALUES (?, ?, ?);"
            );
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getRawPassword());
            statement.setString(3, user.getType().name());
            if (statement.execute()) {
                return new ImmutablePair<>(VC.Mapper.OK, user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ImmutablePair<>(VC.Mapper.SQL_EXCEPTION, user);
    }

    @Override
    public Pair<VC.Mapper, User> selectUserById(@NonNull Integer id) {
        try {
            var entities = Db.use().query(selectUserById, id);

            if (entities.size() != 1) {
                return new ImmutablePair<>(VC.Mapper.NO_SUCH_ENTITY, null);
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
