package com.github.akagawatsurunaki.novappro.mapper.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import com.github.akagawatsurunaki.novappro.annotation.Database;
import com.github.akagawatsurunaki.novappro.mapper.UserMapper;
import com.github.akagawatsurunaki.novappro.model.User;
import com.github.akagawatsurunaki.novappro.model.approval.ApplicationEntity;
import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserMapperImpl implements UserMapper {

    @Getter
    private static final UserMapperImpl instance = new UserMapperImpl();

    @Database
    private static Connection connection;
    String selectUserByApplicationTypeOfAuthoritySQL = "mysql/select_user_by_application_type_of_authority.sql";

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
    public List<User> selectUserByApplicationTypeOfAuthority(ApplicationEntity.ApplicationType type) {
        try {
            URL resource = ResourceUtil.getResource(selectUserByApplicationTypeOfAuthoritySQL);
            String typeStr = type.getChineseFieldNameAnnotation(ApplicationEntity.ApplicationType.class, type.name()).value();
            List<Entity> userEntities = Db.use().query(FileUtil.readString(resource, StandardCharsets.UTF_8), typeStr);

            if (userEntities == null) {
                return null;
            }

            List<User> result = new ArrayList<>();
            for (Entity entity : userEntities) {
                User course = parseUserEntity(entity);
                result.add(course);
            }
            return result;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private User parseUserEntity(Entity entity){
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
        try (
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

}
