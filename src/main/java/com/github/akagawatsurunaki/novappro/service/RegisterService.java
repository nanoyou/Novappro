package com.github.akagawatsurunaki.novappro.service;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.ReUtil;
import com.github.akagawatsurunaki.novappro.annotation.ChineseFieldName;
import com.github.akagawatsurunaki.novappro.constant.Constant;
import com.github.akagawatsurunaki.novappro.mapper.impl.UserMapperImpl;
import com.github.akagawatsurunaki.novappro.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

@NoArgsConstructor
public class RegisterService {
    @Getter
    private static final RegisterService instance = new RegisterService();

    private static final UserMapperImpl USER_MAPPER = UserMapperImpl.getInstance();

    public Pair<VerifyCode, User> trySignUp(Object uname, Object rawPwd, Object cRawPwd) {

        User user = new User();

        String username = ((String) uname);

        // 校验用户名
        VerifyCode usernameLegal = UsernameUtil.isUsernameLegal(username);

        if (usernameLegal != VerifyCode.OK) {
            return new ImmutablePair<>(usernameLegal, null);
        }

        user.setUsername(username);

        String rawPassword = ((String) rawPwd);
        String confirmedRawPassword = ((String) cRawPwd);

        // 两次密码输入是否一致
        if (!rawPassword.equals(confirmedRawPassword)) {
            return new ImmutablePair<>(VerifyCode.PASSWORD_INCONSISTENT, null);
        }

        // 密码是否合法:
        VerifyCode passwordLegal = PasswordUtil.isPasswordLegal(rawPassword);
        if (passwordLegal != VerifyCode.OK) {
            return new ImmutablePair<>(passwordLegal, null);
        }

        user.setRawPassword(rawPassword);

        // TODO: 默认注册的用户均为学生, 未来可能支持多种身份的注册.
        user.setType(User.Type.STUDENT);

        // 调用Mapper对数据库执行INSERT语句
        var verifyCodeUserPair = USER_MAPPER.insertUser(user);

        // 数据库插入是否成功
        if (verifyCodeUserPair.getLeft() == UserMapperImpl.VerifyCode.OK) {
            return new ImmutablePair<>(VerifyCode.OK, user);
        }
        return new ImmutablePair<>(VerifyCode.MAPPER_FAILED, user);
    }

    public enum VerifyCode {
        @ChineseFieldName(value = "用户ID不是一个整数")
        USER_ID_IS_NOT_A_NUMBER,
        @ChineseFieldName(value = "两次密码输入不一致")
        PASSWORD_INCONSISTENT,
        @ChineseFieldName(value = "密码超过规定长度",
                description = "密码必须在" + Constant.MIN_LEN_PASSWORD + "位到" + Constant.MAX_LEN_PASSWORD + "位范围内。")
        PASSWORD_OUT_OF_BOUND,
        @ChineseFieldName(value = "密码不合法",
                description = "密码必须至少包含字母、数字、特殊字符。")
        PASSWORD_MISMATCH,
        @ChineseFieldName(value = "用户名不是中文")
        USERNAME_IS_NOT_CHINESE,

        @ChineseFieldName(value = "用户名超过规定长度",
                description = "用户名必须在" + Constant.MIN_LEN_USERNAME + "位到" + Constant.MAX_LEN_USERNAME + "位范围内。")
        USERNAME_OUT_OF_BOUND,

        @ChineseFieldName(value = "下层服务错误",
                description = "")
        MAPPER_FAILED,

        OK

    }

}

class UsernameUtil {

    public static RegisterService.VerifyCode isUsernameLegal(@NonNull String username) {
        if (Constant.MIN_LEN_USERNAME <= username.length() && username.length() <= Constant.MAX_LEN_USERNAME) {

            if (Validator.isChineseName(username)) {
                return RegisterService.VerifyCode.OK;
            } else {
                return RegisterService.VerifyCode.USERNAME_IS_NOT_CHINESE;
            }

        } else {
            return RegisterService.VerifyCode.USERNAME_OUT_OF_BOUND;
        }
    }
}

class PasswordUtil {
    private static final String pattern = "^(?=.*\\d)(?=.*[a-zA-Z])(?=.*[^\\da-zA-Z\\s]).{" + Constant.MIN_LEN_PASSWORD + "," + Constant.MAX_LEN_PASSWORD + "}$";

    public static RegisterService.VerifyCode isPasswordLegal(@NonNull String password) {
        if (Constant.MIN_LEN_PASSWORD <= password.length() && password.length() <= Constant.MAX_LEN_PASSWORD) {
            if (ReUtil.isMatch(pattern, password)) {
                return RegisterService.VerifyCode.OK;
            } else {
                return RegisterService.VerifyCode.PASSWORD_MISMATCH;
            }
        } else {
            return RegisterService.VerifyCode.PASSWORD_OUT_OF_BOUND;
        }
    }
}
