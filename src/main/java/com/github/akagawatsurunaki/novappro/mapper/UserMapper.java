package com.github.akagawatsurunaki.novappro.mapper;

import com.github.akagawatsurunaki.novappro.constant.VC;
import com.github.akagawatsurunaki.novappro.model.database.User;
import lombok.NonNull;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public interface UserMapper {

    User selectById(int id);

    Pair<VC.Mapper, User> selectUserById(@NonNull Integer id);
    Pair<VC.Mapper, List<User>> selectAllUsers();
    Pair<VC.Mapper, User> insertUser(User user);

}
