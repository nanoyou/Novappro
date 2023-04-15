package com.github.akagawatsurunaki.novappro.mapper;

import com.github.akagawatsurunaki.novappro.constant.VerifyCode;
import com.github.akagawatsurunaki.novappro.model.User;
import com.github.akagawatsurunaki.novappro.model.approval.ApplicationEntity;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public interface UserMapper {

    Pair<VerifyCode.Mapper, User> getUserById(int id);
    Pair<VerifyCode.Mapper, List<User>> getUsers();

    Pair<VerifyCode.Mapper, List<User>> selectUserByApplicationTypeOfAuthority(ApplicationEntity.ApplicationType type);

    Pair<VerifyCode.Mapper, User> insertUser(User user);

}
