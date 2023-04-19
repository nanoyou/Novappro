package com.github.akagawatsurunaki.novappro.service.base;

import com.github.akagawatsurunaki.novappro.constant.Constant;
import com.github.akagawatsurunaki.novappro.constant.VerifyCode;
import com.github.akagawatsurunaki.novappro.mapper.impl.UserMapperImpl;
import com.github.akagawatsurunaki.novappro.model.database.User;
import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

public class LoginService {

    @Getter
    private static final LoginService instance = new LoginService();

    private static final UserMapperImpl USER_MAPPER = UserMapperImpl.getInstance();

    public Pair<VerifyCode.Service, User> tryLoginWithUserId(@NonNull Integer id, @NonNull String rawPassword) {

        // 密码长度校验
        if (!(Constant.MIN_LEN_PASSWORD <= rawPassword.length() && rawPassword.length() <= Constant.MAX_LEN_PASSWORD)) {
            return new ImmutablePair<>(VerifyCode.Service.TOO_LONG_PASSWORD, null);
        }

        var pair = USER_MAPPER.selectUserById(id);
        // 判断用户是否存在

        var vc = pair.getLeft();
        if (vc == VerifyCode.Mapper.NO_SUCH_ENTITY) {
            return new ImmutablePair<>(VerifyCode.Service.NO_SUCH_USER, null);
        }

        // 获取用户对象
        var user = pair.getRight();

        // 校验密码

        // 密码正确
        if (user.getRawPassword().equals(rawPassword)) {
            return new ImmutablePair<>(VerifyCode.Service.OK, user);
        }

        // 密码错误
        return new ImmutablePair<>(VerifyCode.Service.PASSWORD_ERROR, null);

    }

}
