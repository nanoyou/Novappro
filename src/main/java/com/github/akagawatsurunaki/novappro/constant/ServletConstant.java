package com.github.akagawatsurunaki.novappro.constant;

import lombok.AllArgsConstructor;

public class ServletConstant {
    public static final String GET_COURSE_SERVLET = "/get_courses";

    public static final String INFO = "info";

    @AllArgsConstructor
    public enum JSPResource {
        WELCOME("welcome.jsp"),
        WELCOME_SESSION("welcome_session.jsp"),
        WELCOME_COOKIE("welcome_cookie.jsp"),
        ERROR("error.jsp");

        public final String name;
    }


    @AllArgsConstructor
    public enum RequestAttr {
        USER_ID("user_id"),
        LOGIN_USER_ID("login_user_id"),
        COURSE_APPLICATIONS("course_applications"),
        USER_USERNAME("user_username"),
        ;

        public final String name;
    }

    @AllArgsConstructor
    public enum RequestParam {
        USER_ID("userId"),
        RAW_PASSWORD("rawPassword"),
        USER_TYPE("user_type");

        public final String name;
    }
}
