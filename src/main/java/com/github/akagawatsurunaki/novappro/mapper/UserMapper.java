package com.github.akagawatsurunaki.novappro.mapper;

import com.github.akagawatsurunaki.novappro.model.User;

import java.util.List;
import java.util.Map;

public interface UserMapper {

    User getUser(By by, Object key);
    List<User> getUsers();

    enum By {
        ID,
        USERNAME,
    }


}
