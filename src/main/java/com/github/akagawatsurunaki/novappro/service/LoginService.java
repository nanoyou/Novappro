package com.github.akagawatsurunaki.novappro.service;

import com.github.akagawatsurunaki.novappro.mapper.impl.UserMapperImpl;
import com.github.akagawatsurunaki.novappro.model.User;
import com.github.akagawatsurunaki.novappro.test.UserTable;
import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;

public class LoginService {

    @Getter
    private static final LoginService instance = new LoginService();

    private static final UserMapperImpl USER_MAPPER = UserMapperImpl.getInstance();

    public enum VerifyCode {
        OK,
        NO_SUCH_USER,
        PASSWORD_ERROR,
        FATAL_ERROR
    }


    @Deprecated
    public Map<String, Object> tryLoginWithUserName(@NonNull String username, @NonNull String rawPassword) {
        final String VERIFY_CODE = VerifyCode.class.getName();
        final String USER = User.class.getName();

        Map<String, Object> result = new HashMap<>();

        for (User user : UserTable.getUsers()) {
            if (user.getUsername().equals(username)) {
                if (user.getRawPassword().equals(rawPassword)) {
                    result.put(VERIFY_CODE, VerifyCode.OK);
                    result.put(USER, user);
                } else {
                    result.put(VERIFY_CODE, VerifyCode.PASSWORD_ERROR);
                    result.put(USER, null);
                }
                return result;
            }
        }

        result.put(VERIFY_CODE, VerifyCode.NO_SUCH_USER);
        result.put(USER, null);
        return result;
    }

    public Pair<VerifyCode, User> tryLoginWithUserId(@NonNull Integer id, @NonNull String rawPassword) {

        User user = USER_MAPPER.getUserById(id);
        // 判断用户是否存在
        if (user == null) {
            return new ImmutablePair<>(VerifyCode.NO_SUCH_USER, null);
        }
        // 校验密码
        if (user.getRawPassword().equals(rawPassword)) {
            return new ImmutablePair<>(VerifyCode.OK, user);
        }
        return new ImmutablePair<>(VerifyCode.PASSWORD_ERROR, user);

    }

//    public Map<String, Object> tryLoginWithUserId(@NonNull Integer id, @NonNull String rawPassword){
//        final String VERIFY_CODE = VerifyCode.class.getName();
//        final String USER = User.class.getName();
//
//        Map<String, Object> result = new HashMap<>();
//
//        for (User user : UserTable.getUsers()) {
//            if (user.getId().equals(id)) {
//                if(user.getRawPassword().equals(rawPassword)){
//                    result.put(VERIFY_CODE, VerifyCode.OK);
//                    result.put(USER, user);
//                } else {
//                    result.put(VERIFY_CODE, VerifyCode.PASSWORD_ERROR);
//                    result.put(USER, null);
//                }
//                return result;
//            }
//        }
//
//        result.put(VERIFY_CODE, VerifyCode.NO_SUCH_USER);
//        result.put(USER, null);
//        return result;
//    }

}
