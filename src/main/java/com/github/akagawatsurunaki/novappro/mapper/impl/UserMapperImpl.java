package com.github.akagawatsurunaki.novappro.mapper.impl;

import com.github.akagawatsurunaki.novappro.mapper.UserMapper;
import com.github.akagawatsurunaki.novappro.model.User;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserMapperImpl implements UserMapper {

    @Getter
    private static final UserMapperImpl instance = new UserMapperImpl();

    @Setter
    private Connection connection;

    @Override
    public User getUser(@NonNull By by, @NonNull Object key) {
        switch (by) {
            case ID:
                int id = (int) key;
                return getUserById(id);
            case USERNAME:
                // TODO: 根据名称查询User
                break;
        }
        return null;
    }

    public List<User> getUsers() {

        Statement statement;
        ResultSet resultSet;
        List<User> users = new ArrayList<>();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select id, username, raw_password, type from user");
            users = User.parseResultSet(resultSet);
        } catch (SQLException e) {
            return users;
        }
        return users;
    }


    User getUserById(@NonNull int id) {
        PreparedStatement statement;
        ResultSet resultSet;
        try {
            statement = connection.prepareStatement("select `id`, `username`, `raw_password`, `type` from user where user.id = ?;");
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            List<User> users = User.parseResultSet(resultSet);
            if (users.isEmpty()){
                return null;
            }
            User user = User.parseResultSet(resultSet).get(0);
            resultSet.close();
            return user;


        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


}
