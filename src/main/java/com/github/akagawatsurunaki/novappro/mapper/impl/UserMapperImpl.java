package com.github.akagawatsurunaki.novappro.mapper.impl;

import com.github.akagawatsurunaki.novappro.annotation.Database;
import com.github.akagawatsurunaki.novappro.mapper.UserMapper;
import com.github.akagawatsurunaki.novappro.model.User;
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

    public List<User> getUsers() {

        Statement statement;
        ResultSet resultSet;
        List<User> users = new ArrayList<>();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select id, username, raw_password, type from user");
            users = parseResultSet(resultSet);
        } catch (SQLException e) {
            return users;
        }
        return users;
    }

    @Override
    public Pair<VerifyCode, User> insertUser(@NonNull User user) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO `user` (`username`, `raw_password`, `type`) VALUES (?, ?, ?);"
            );
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getRawPassword());
            statement.setString(3, user.getType().getChineseName());
            if (statement.execute()) {
                return new ImmutablePair<>(VerifyCode.OK, user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ImmutablePair<>(VerifyCode.INSERT_FAILED, user);
    }


    @Override
    public User getUserById(int id) {
        try(
              PreparedStatement statement =
                      connection.prepareStatement(
                              "select `id`, `username`, `raw_password`, `type` from user where user.id = ?;"
                      )
                ) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            List<User> users = parseResultSet(resultSet);
            if (users.isEmpty()) {
                return null;
            }
            resultSet.close();
            return users.get(0);
        } catch (SQLException e) {
            return null;
        }
    }



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

}
