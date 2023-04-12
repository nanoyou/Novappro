package com.github.akagawatsurunaki.novappro.mapper;

import com.github.akagawatsurunaki.novappro.model.User;
import com.github.akagawatsurunaki.novappro.model.approval.ApplicationEntity;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public interface UserMapper {

    User getUserById(int id);
    List<User> getUsers();

    List<User> selectUserByApplicationTypeOfAuthority(ApplicationEntity.ApplicationType type);

    Pair<VerifyCode, User> insertUser(User user);

    public enum VerifyCode{

        OK,
        INSERT_FAILED,
    }
}
