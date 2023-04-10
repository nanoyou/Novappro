package com.github.akagawatsurunaki.novappro.mapper;

import com.github.akagawatsurunaki.novappro.mapper.impl.UserMapperImpl;
import com.github.akagawatsurunaki.novappro.model.User;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public interface UserMapper {

    User getUserById(int id);
    List<User> getUsers();

    Pair<UserMapperImpl.VerifyCode, User> insertUser(User user);

    enum By {
        ID,
        USERNAME,
    }


}
