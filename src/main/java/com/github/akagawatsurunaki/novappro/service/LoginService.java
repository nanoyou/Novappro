package com.github.akagawatsurunaki.novappro.service;

import com.github.akagawatsurunaki.novappro.enumeration.VerifyCode;
import com.github.akagawatsurunaki.novappro.model.User;
import com.github.akagawatsurunaki.novappro.test.UserTable;
import lombok.Getter;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;

public class LoginService {

    @Getter
    private static final LoginService instance = new LoginService();

    /**
     *
     * @param username
     * @param rawPassword
     * @return
     */
    public Map<String, Object> tryLoginWithUserName(@NonNull String username, @NonNull String rawPassword) {
        final String VERIFY_CODE = VerifyCode.class.getName();
        final String USER = User.class.getName();

        Map<String, Object> result = new HashMap<>();

        for (User user : UserTable.getUsers()) {
            if (user.getUsername().equals(username)) {
                if(user.getRawPassword().equals(rawPassword)){
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

    public Map<String, Object> tryLoginWithUserId(@NonNull Integer id, @NonNull String rawPassword){
        final String VERIFY_CODE = VerifyCode.class.getName();
        final String USER = User.class.getName();

        Map<String, Object> result = new HashMap<>();

        for (User user : UserTable.getUsers()) {
            if (user.getId().equals(id)) {
                if(user.getRawPassword().equals(rawPassword)){
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

}
