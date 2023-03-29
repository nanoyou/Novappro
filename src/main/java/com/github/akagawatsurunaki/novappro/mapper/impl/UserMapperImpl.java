package com.github.akagawatsurunaki.novappro.mapper.impl;

import com.github.akagawatsurunaki.novappro.annotation.Database;
import com.github.akagawatsurunaki.novappro.mapper.UserMapper;
import com.github.akagawatsurunaki.novappro.model.User;
import lombok.Getter;
import lombok.NonNull;

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
    public User getUserById(int id) {
        try(
              PreparedStatement statement = connection.prepareStatement("select `id`, `username`, `raw_password`, `type` from user where user.id = ?;");
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
            e.printStackTrace();
            return null;
        }


//        try {
//            statement = connection.prepareStatement("select `id`, `username`, `raw_password`, `type` from user where user.id = ?;");
//            statement.setInt(1, id);
//            resultSet = statement.executeQuery();
//            List<User> users = parseResultSet(resultSet);
//            if (users.isEmpty()){
//                return null;
//            }
//            User user = users.get(0);
//            resultSet.close();
//            return user;
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return null;
//        }


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
