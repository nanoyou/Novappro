package com.github.akagawatsurunaki.novappro.mapper;

import com.github.akagawatsurunaki.novappro.model.database.User;

import java.util.List;

public interface UserMapper {

    User selectById(int id);

    List<User> selectAllUsers();

    int insert(User user);

}
