package com.github.akagawatsurunaki.novappro.constant;

public final class VerifyCode {

    public enum Mapper{
        NO_SUCH_ENTITY,
        SQL_EXCEPTION,
        OTHER_EXCEPTION,
        OK;
    }

    public enum Service{
        ERROR,
        OK,
        PASSWORD_ERROR,
        NO_SUCH_ENTITY,
        NO_SUCH_USER,
        TOO_LONG_PASSWORD
        ;
    }
}
