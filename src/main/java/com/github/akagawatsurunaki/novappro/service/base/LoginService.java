package com.github.akagawatsurunaki.novappro.service.base;

import com.github.akagawatsurunaki.novappro.constant.Constant;
import com.github.akagawatsurunaki.novappro.mapper.UserMapper;
import com.github.akagawatsurunaki.novappro.model.database.User;
import com.github.akagawatsurunaki.novappro.model.frontend.ServiceMessage;
import com.github.akagawatsurunaki.novappro.util.MyDb;
import lombok.Getter;
import lombok.NonNull;
import lombok.val;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.ibatis.session.SqlSession;

import javax.annotation.Nullable;

public class LoginService {

    @Getter
    private static final LoginService instance = new LoginService();

    /**
     * 登录
     *
     * @param id 用户的工号/学号, 即用户ID
     * @param rawPassword 用户输入的密码
     * @return 成功登录的用户对象
     */
    public Pair<ServiceMessage, User> login(@Nullable String id,
                                            @Nullable String rawPassword) {
        try {
            if (id == null || id.isBlank()) {
                return new ImmutablePair<>(
                        ServiceMessage.of(ServiceMessage.Level.WARN,
                                "工号/学号不能为空"),
                        null);
            }

            val userId = Integer.valueOf(id);

            if (rawPassword == null || rawPassword.isBlank()){
                return new ImmutablePair<>(
                        ServiceMessage.of(ServiceMessage.Level.WARN,
                                "密码不能为空"),
                        null);
            }

            return _login(userId, rawPassword);

        } catch (NumberFormatException e) {
            return new ImmutablePair<>(
                    ServiceMessage.of(ServiceMessage.Level.ERROR,
                            "工号/学号必须为一个数字"),
                    null);
        }
    }

    private Pair<ServiceMessage, User> _login(@NonNull Integer id,
                                              @NonNull String rawPassword) {

        // 密码长度校验
        if (!(Constant.MIN_LEN_PASSWORD <= rawPassword.length()
                && rawPassword.length() <= Constant.MAX_LEN_PASSWORD)) {

            return new ImmutablePair<>(
                    ServiceMessage.of(ServiceMessage.Level.WARN,
                            "密码长度应在" + Constant.MIN_LEN_PASSWORD + "~" + Constant.MAX_LEN_PASSWORD + "之间"),
                    null);
        }

        try (SqlSession session = MyDb.use().openSession(true)) {
            val userMapper = session.getMapper(UserMapper.class);

            // 获取用户对象
            val user = userMapper.selectById(id);

            // 判断用户是否存在
            if (user == null) {
                return new ImmutablePair<>(
                        ServiceMessage.of(ServiceMessage.Level.ERROR,
                                "用户ID为" + id + "的用户不存在"),
                        null);
            }

            // 密码校验
            assert user.getRawPassword() != null;
            if (!user.getRawPassword().equals(rawPassword)) {
                return new ImmutablePair<>(
                        ServiceMessage.of(ServiceMessage.Level.ERROR,
                                "密码错误"),
                        null);
            }

            return new ImmutablePair<>(
                    ServiceMessage.of(ServiceMessage.Level.SUCCESS,
                            "登录成功"),
                    user);
        }

    }

}
