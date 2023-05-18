package com.github.akagawatsurunaki.novappro.mapper;

import com.github.akagawatsurunaki.novappro.model.database.User;
import lombok.NonNull;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {

    User selectById(@Param("id") int id);

    List<User> selectAllUsers();

    List<User> selectByIds(@Param("ids") @NonNull List<Integer> ids);

    int insert(User user);

    int delete(@Param("ids") @NonNull List<Integer> ids);

    int update(@NonNull User user);



}
