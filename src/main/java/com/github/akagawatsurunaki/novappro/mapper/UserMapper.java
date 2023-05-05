package com.github.akagawatsurunaki.novappro.mapper;

import com.github.akagawatsurunaki.novappro.model.database.User;
import lombok.NonNull;

import java.util.List;

public interface UserMapper {

    User selectById(int id);

    List<User> selectAllUsers();

    List<User> selectByIds(@NonNull List<Integer> ids);

    int insert(User user);

    int delete(@NonNull List<Integer> ids);

}
