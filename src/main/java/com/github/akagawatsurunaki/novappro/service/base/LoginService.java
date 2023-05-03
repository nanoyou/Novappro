package com.github.akagawatsurunaki.novappro.service.base;

import com.github.akagawatsurunaki.novappro.constant.Constant;
import com.github.akagawatsurunaki.novappro.constant.VC;
import com.github.akagawatsurunaki.novappro.mapper.UserMapper;
import com.github.akagawatsurunaki.novappro.model.database.User;
import com.github.akagawatsurunaki.novappro.util.MyDb;
import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.ibatis.session.SqlSession;

public class LoginService {

    @Getter
    private static final LoginService instance = new LoginService();

    public Pair<VC.Service, User> tryLoginWithUserId(@NonNull Integer id, @NonNull String rawPassword) {

        // 密码长度校验
        if (!(Constant.MIN_LEN_PASSWORD <= rawPassword.length() && rawPassword.length() <= Constant.MAX_LEN_PASSWORD)) {
            return new ImmutablePair<>(VC.Service.TOO_LONG_PASSWORD, null);
        }

        try (SqlSession session = MyDb.use().openSession(true)) {
            var userMapper = session.getMapper(UserMapper.class);

            // 获取用户对象
            var user = userMapper.selectById(id);

            // 判断用户是否存在
            if (user != null) {

                // 密码正确
                if (user.getRawPassword().equals(rawPassword)) {
                    return new ImmutablePair<>(VC.Service.OK, user);
                }

                return new ImmutablePair<>(VC.Service.PASSWORD_ERROR, null);
            }

            return new ImmutablePair<>(VC.Service.NO_SUCH_USER, null);
        }

    }

}
