package com.github.akagawatsurunaki.novappro.mapper;

import com.github.akagawatsurunaki.novappro.model.User;

import java.util.List;

public interface UserMapper {

    User getUserById(int id);
    List<User> getUsers();

    enum By {
        ID,
        USERNAME,
    }


}
