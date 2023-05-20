package com.github.akagawatsurunaki.novappro.constant;

import com.github.akagawatsurunaki.novappro.model.frontend.ServiceMessage;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;

public class Constant {
    public static final int MAX_LEN_PASSWORD = 16;
    public static final int MIN_LEN_PASSWORD = 8;

    public static final int MAX_LEN_USER_ID = 10;

    public static final int MIN_LEN_USER_ID = 8;

    public static final int MIN_LEN_USERNAME = 1;
    public static final int MAX_LEN_USERNAME = 25;

    public static final int MAX_LEN_COURSE_NAME = 127;

    public static final int MAX_LEN_COURSE_SERIAL_NUMBER = 7;
    public static final int MAX_LEN_COURSE_ONLINE_CONTACT_WAY = 255;
    public static final int MAX_LEN_COURSE_COMMENT = 500;

    public static final ServiceMessage exceptionServiceMessage = ServiceMessage.of(ServiceMessage.Level.FATAL,
            "你干嘛~哈哈嗨哟！服务器发生异常！");

}
