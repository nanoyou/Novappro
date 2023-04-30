package com.github.akagawatsurunaki.novappro.constant;

public final class VerifyCode {

    public enum Mapper{
        NO_SUCH_ENTITY,
        SQL_EXCEPTION,
        OTHER_EXCEPTION,

        FAILED_TO_PARSE_ENTITY,

        OK;
    }

    public enum Service{
        ERROR,
        MEANINGLESS,
        OK,
        PASSWORD_ERROR,
        NO_SUCH_ENTITY,
        NO_SUCH_USER,
        NO_SUCH_COURSE_APPL,
        NO_SUCH_COURSE,
        TOO_LONG_PASSWORD,
        REMARK_IS_BLANK
        ;
    }
}
