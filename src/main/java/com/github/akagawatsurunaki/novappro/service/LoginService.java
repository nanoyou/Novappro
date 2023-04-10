package com.github.akagawatsurunaki.novappro.service;

import com.github.akagawatsurunaki.novappro.annotation.ChineseFieldName;
import com.github.akagawatsurunaki.novappro.constant.Constant;
import com.github.akagawatsurunaki.novappro.mapper.impl.UserMapperImpl;
import com.github.akagawatsurunaki.novappro.model.User;
import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

public class LoginService {

    @Getter
    private static final LoginService instance = new LoginService();

    private static final UserMapperImpl USER_MAPPER = UserMapperImpl.getInstance();

    public Pair<VerifyCode, User> tryLoginWithUserId(@NonNull Integer id, @NonNull String rawPassword) {

        // 密码长度校验
        if (!(Constant.MIN_LEN_PASSWORD <= rawPassword.length() && rawPassword.length() <= Constant.MAX_LEN_PASSWORD)) {
            return new ImmutablePair<>(VerifyCode.PASSWORD_OUT_OF_BOUND, null);
        }

        User user = USER_MAPPER.getUserById(id);
        // 判断用户是否存在
        if (user == null) {
            return new ImmutablePair<>(VerifyCode.NO_SUCH_USER, null);
        }

        // 校验密码
        if (user.getRawPassword().equals(rawPassword)) {
            return new ImmutablePair<>(VerifyCode.OK, user);
        }
        return new ImmutablePair<>(VerifyCode.PASSWORD_ERROR, null);

    }

    public enum VerifyCode {
        @ChineseFieldName(value = "登录成功")
        OK,
        @ChineseFieldName(value = "无此用户")
        NO_SUCH_USER,
        @ChineseFieldName(value = "密码错误")
        PASSWORD_ERROR,
        @ChineseFieldName(value = "密码超出规定范围")
        PASSWORD_OUT_OF_BOUND,
        @ChineseFieldName(value = "严重错误")
        FATAL_ERROR
    }

}
