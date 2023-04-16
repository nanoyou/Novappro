package com.github.akagawatsurunaki.novappro.constant;

import lombok.AllArgsConstructor;

public class ServletConstant {
    public static final String GET_COURSE_SERVLET = "/get_courses";

    public static final String INFO = "info";


    @AllArgsConstructor
    public enum RequestAttr{
        LOGIN_USER_ID("login_user_id"),
        COURSE_APPLICATIONS("course_applications");

        public final String name;
    }
}
