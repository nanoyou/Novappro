package com.github.akagawatsurunaki.novappro.service.manage;

import cn.hutool.core.collection.CollUtil;
import com.github.akagawatsurunaki.novappro.mapper.UserMapper;
import com.github.akagawatsurunaki.novappro.model.database.User;
import com.github.akagawatsurunaki.novappro.model.frontend.ServiceMessage;
import com.github.akagawatsurunaki.novappro.util.MyDb;
import lombok.Getter;
import lombok.NonNull;
import lombok.val;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class UserManageService {

    @Getter
    private final static UserManageService instance = new UserManageService();

    /**
     * 获取所有的用户
     *
     * @return
     */
    public Pair<ServiceMessage, List<User>> getAllUsers() {
        try (var session = MyDb.use().openSession(true)) {
            val userMapper = session.getMapper(UserMapper.class);

            val users = userMapper.selectAllUsers();

            if (users == null || users.isEmpty()) {
                return new ImmutablePair<>(
                        new ServiceMessage(ServiceMessage.Level.SUCCESS, "用户列表为空"),
                        new ArrayList<>()
                );
            }

            return new ImmutablePair<>(
                    new ServiceMessage(ServiceMessage.Level.SUCCESS, "成功查询到" + users.size()
                            + "个用户"),
                    users
            );
        }
    }

    /**
     * 根据UserIds查找当前系统中存在的用户，并删除。
     * 如果存在任何一个非法的用户ID，服务失败。
     *
     * @param userIds 要删除的用户的ID列表
     * @return
     */
    public Pair<ServiceMessage, List<User>> updateAllUsers(@NonNull List<Integer> userIds) {

        try (var session = MyDb.use().openSession(true)) {

            var userMapper = session.getMapper(UserMapper.class);
            val users = userMapper.selectByIds(userIds);
            if (users != null && !users.isEmpty()) {
                val allUsers = userMapper.selectAllUsers();
                val userToDel = CollUtil.disjunction(allUsers, users);

                List<Integer> userIdsToDel = new ArrayList<>();
                userToDel.forEach(user -> {
                    userIdsToDel.add(user.getId());
                });

                if (userMapper.delete(userIdsToDel) == userIdsToDel.size()) {
                    return new ImmutablePair<>(new ServiceMessage(ServiceMessage.Level.SUCCESS,
                            "成功地删除了" + userIdsToDel.size() + "个用户"),
                            users);
                }
            }
            return new ImmutablePair<>(
                    new ServiceMessage(ServiceMessage.Level.ERROR, "您发送的更改请求中存在非法的参数，请使用正确的学号/工号"),
                    new ArrayList<>()
            );
        }

    }


}
