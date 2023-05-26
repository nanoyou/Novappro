package com.github.akagawatsurunaki.novappro.service.base;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.ReUtil;
import com.github.akagawatsurunaki.novappro.constant.Constant;
import com.github.akagawatsurunaki.novappro.enumeration.UserType;
import com.github.akagawatsurunaki.novappro.mapper.UserMapper;
import com.github.akagawatsurunaki.novappro.model.database.User;
import com.github.akagawatsurunaki.novappro.model.frontend.ServiceMessage;
import com.github.akagawatsurunaki.novappro.util.MyDb;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.val;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;

@NoArgsConstructor
public class RegisterService {
    @Getter
    private static final RegisterService instance = new RegisterService();
    private static final String pattern =
            "^(?=.*\\d)(?=.*[a-zA-Z])(?=.*[^\\da-zA-Z\\s]).{" + Constant.MIN_LEN_PASSWORD + "," + Constant.MAX_LEN_PASSWORD + "}$";

    public Pair<ServiceMessage, User> trySignUp(@Nullable String username, @Nullable String password,
                                                @Nullable String confirmedPassword) {
        if (username == null || username.isBlank()) {
            return ImmutablePair.of(
                    ServiceMessage.of(ServiceMessage.Level.WARN, "用户名不能为空"),
                    null
            );
        }

        if (password == null || password.isBlank()) {
            return ImmutablePair.of(
                    ServiceMessage.of(ServiceMessage.Level.WARN, "密码不能为空"),
                    null
            );
        }

        if (confirmedPassword == null || confirmedPassword.isBlank()) {
            return ImmutablePair.of(
                    ServiceMessage.of(ServiceMessage.Level.WARN, "确认密码不能为空"),
                    null
            );
        }

        return _trySignUp(username, password, confirmedPassword);
    }

    private Pair<ServiceMessage, User> _trySignUp(@NonNull String username, @NonNull String password,
                                                  @NonNull String confirmedPassword) {

        if (!(Constant.MIN_LEN_USERNAME <= username.length() && username.length() <= Constant.MAX_LEN_USERNAME)) {
            return ImmutablePair.of(
                    ServiceMessage.of(ServiceMessage.Level.ERROR, "用户名长度不合法：用户名必须在" + Constant.MIN_LEN_USERNAME +
                            "位到" + Constant.MAX_LEN_USERNAME + "位范围内。"),
                    null
            );
        }

        if (!Validator.isChineseName(username)) {
            return ImmutablePair.of(
                    ServiceMessage.of(ServiceMessage.Level.ERROR, "用户名不是中文名"),
                    null
            );
        }

        if (!(Constant.MIN_LEN_PASSWORD <= password.length() && password.length() <= Constant.MAX_LEN_PASSWORD)) {
            return ImmutablePair.of(
                    ServiceMessage.of(ServiceMessage.Level.ERROR,
                            "密码长度不合法：密码必须在" + Constant.MIN_LEN_PASSWORD + "位到" + Constant.MAX_LEN_PASSWORD + "位范围内。"),
                    null
            );
        }

        if (!ReUtil.isMatch(pattern, password)) {
            return ImmutablePair.of(
                    ServiceMessage.of(ServiceMessage.Level.ERROR, "密码不是强密码：密码必须至少包含字母、数字、特殊字符。"),
                    null
            );
        }

        if (!password.equals(confirmedPassword)) {
            return ImmutablePair.of(
                    ServiceMessage.of(ServiceMessage.Level.ERROR, "两次密码输入不一致"),
                    null
            );
        }

        val user = User.builder().id(0).username(username).type(UserType.STUDENT).rawPassword(password).build();

        try (var session = MyDb.use().openSession(true)) {

            val userMapper = session.getMapper(UserMapper.class);
            if (userMapper.insert(user) != 1) {
                return ImmutablePair.of(
                        ServiceMessage.of(ServiceMessage.Level.FATAL, "注册用户失败"),
                        null
                );
            }

        }

        return ImmutablePair.of(
                ServiceMessage.of(ServiceMessage.Level.SUCCESS, "注册用户成功"),
                user
        );

    }

}
