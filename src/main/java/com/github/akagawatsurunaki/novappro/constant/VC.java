package com.github.akagawatsurunaki.novappro.constant;

import lombok.AllArgsConstructor;

/**
 * Verify Code
 */
public final class VC {

    public enum Mapper{
        NO_SUCH_ENTITY,
        EMPTY_ENTITY_LIST,
        MORE_THAN_ONE_ENTITY,
        UPDATE_FAILED,
        SQL_EXCEPTION,
        OTHER_EXCEPTION,
        FAILED_TO_PARSE_ENTITY,
        OK;
    }

    @AllArgsConstructor
    public enum Service{
        ERROR("未知错误"),
        MEANINGLESS("无意义动作"),
        OK("服务成功"),
        PASSWORD_ERROR("密码错误"),
        NO_SUCH_ENTITY("未查询到此实体"),
        NO_SUCH_USER("该用户不存在"),
        NO_SUCH_COURSE_APPL("该课程申请不存在"),
        NO_SUCH_COURSE("该课程不存在"),
        TOO_LONG_PASSWORD("密码过长"),
        REMARK_IS_BLANK("评论为空"),
        BLANK_PASSWORD("密码为空"),
        USER_ID_NAN("非法的用户ID"),
        ;

        public final String message;

    }
}
