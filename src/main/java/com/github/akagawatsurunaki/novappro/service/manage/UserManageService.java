package com.github.akagawatsurunaki.novappro.service.manage;

import cn.hutool.core.collection.CollUtil;
import com.github.akagawatsurunaki.novappro.mapper.UserMapper;
import com.github.akagawatsurunaki.novappro.model.database.User;
import com.github.akagawatsurunaki.novappro.model.frontend.ServiceMessage;
import com.github.akagawatsurunaki.novappro.util.MyDb;
import lombok.Getter;
import lombok.NonNull;
import lombok.val;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    public Pair<ServiceMessage, List<User>> updateAllUsers(@NonNull String[] userIds) {
        try {
            var ids = Arrays.stream(userIds)
                    .mapToInt(Integer::parseInt) // 将每个元素转为 int 类型
                    .boxed() // 转换为 Integer 对象
                    .collect(Collectors.toList()); // 收集为 List<Integer>
            return updateAllUsers(ids);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return new ImmutablePair<>(new ServiceMessage(ServiceMessage.Level.ERROR,
                    "您发送的更改请求中存在非法的参数，含有不能解析为学号的参数，请使用正确的学号/工号"),
                    new ArrayList<>());
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
                    new ServiceMessage(ServiceMessage.Level.ERROR, "您所要更改的用户不存在"),
                    new ArrayList<>()
            );
        }

    }

    public Pair<ServiceMessage, User> updateUser(@Nullable User user) {
        if (user == null) {
            return ImmutablePair.of(ServiceMessage.of(ServiceMessage.Level.WARN, "不能新建空用户"), null);
        }
        return _updateUser(user);
    }

    private Pair<ServiceMessage, User> _updateUser(@NonNull User user) {
        try (var session = MyDb.use().openSession(true)) {
            val userMapper = session.getMapper(UserMapper.class);
            if (userMapper.update(user) == 1) {
                return ImmutablePair.of(ServiceMessage.of(ServiceMessage.Level.SUCCESS, "成功新建了1个新用户"),
                        userMapper.selectById(user.getId()));
            }
        }
        return ImmutablePair.of(ServiceMessage.of(ServiceMessage.Level.FATAL, "无法新建1个新用户"), null);
    }

    public Pair<ServiceMessage, User> deleteUser(@Nullable String userId) {
        if (userId == null || userId.isBlank()) {
            return ImmutablePair.of(ServiceMessage.of(ServiceMessage.Level.WARN, "要删除的用户ID为空"), null);
        }
        try {
            return _deleteUser(Integer.valueOf(userId));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return ImmutablePair.of(ServiceMessage.of(ServiceMessage.Level.WARN, "用户ID非法"), null);
        }
    }

    private Pair<ServiceMessage, User> _deleteUser(@NonNull Integer userId) {
        try (var session = MyDb.use().openSession(true)) {

            val userMapper = session.getMapper(UserMapper.class);

            val user = userMapper.selectById(userId);

            if (user == null) {
                return ImmutablePair.of(ServiceMessage.of(ServiceMessage.Level.ERROR, "ID为" + userId + "的用户不存在"), null);
            }

            if (userMapper.delete(new ArrayList<>(userId)) == 1) {
                return ImmutablePair.of(ServiceMessage.of(ServiceMessage.Level.SUCCESS, "ID为" + userId + "的用户删除成功"), user);
            }
        }
        return ImmutablePair.of(ServiceMessage.of(ServiceMessage.Level.FATAL, "ID为" + userId + "的用户删除失败"), null);

    }

    public Pair<ServiceMessage, User> addUser(@Nullable User user) {

        if (user == null) {
            return ImmutablePair.of(ServiceMessage.of(ServiceMessage.Level.WARN, "不能添加一个空用户"), null);
        }

        return _addUser(user);
    }

    private Pair<ServiceMessage, User> _addUser(@NonNull User user) {
        try (var session = MyDb.use().openSession(true)) {

            val userMapper = session.getMapper(UserMapper.class);

            if (userMapper.insert(user) == 1) {
                return ImmutablePair.of(ServiceMessage.of(ServiceMessage.Level.SUCCESS, "成功添加用户"), user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ImmutablePair.of(ServiceMessage.of(ServiceMessage.Level.FATAL, "添加用户失败"), null);
    }

}
