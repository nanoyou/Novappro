package com.github.akagawatsurunaki.novappro.mapper;

import com.github.akagawatsurunaki.novappro.constant.VerifyCode;
import com.github.akagawatsurunaki.novappro.model.database.User;
import lombok.NonNull;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public interface UserMapper {

    public Pair<VerifyCode.Mapper, User> selectUserById(@NonNull Integer id);
    Pair<VerifyCode.Mapper, List<User>> getUsers();

    Pair<VerifyCode.Mapper, User> insertUser(User user);

}
