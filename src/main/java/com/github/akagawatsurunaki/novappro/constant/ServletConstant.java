package com.github.akagawatsurunaki.novappro.constant;

import lombok.AllArgsConstructor;

public class ServletConstant {
    public static final String GET_COURSE_SERVLET = "/get_courses";

    public static final String INFO = "info";

    public static class WebServletValue {
         public final static String  MODIFY_COURSE_APPL = "/modify_course_appl";
         public final static String COURSE_APPL_DETAIL = "/course_appl_detail";
         public final static String GET_APPROS = "/get_appros";


    }

    @AllArgsConstructor
    public enum JSPResource {
        WELCOME("welcome.jsp"),
        WELCOME_SESSION("welcome_session.jsp"),
        WELCOME_COOKIE("welcome_cookie.jsp"),
        ERROR("error.jsp"),
        COURSE_APPL_DETAIL("course_appl_detail.jsp"),
        GET_APPLIED_COURSES("get_applied_courses.jsp"),
        GET_APPROS("get_appros.jsp")
        ;

        public final String name;
    }


    @AllArgsConstructor
    public enum RequestAttr {
        USER_ID("user_id"),
        LOGIN_USER_ID("login_user_id"),

        LOGIN_USER("login_user"),
        COURSE_APPLICATIONS("course_applications"),
        USER_USERNAME("user_username"),
        APPLIED_COURSES("applied_courses"),
        SELECTED_COURSE_APPL_FLOW_NO("selected_course_appl_flow_no"),
        APPL_ITEMS_WITH_GIVEN_APPROVER("APPL_ITEMS_WITH_GIVEN_APPROVER")
        ;

        public final String name;
    }

    @AllArgsConstructor
    public enum RequestParam {
        USER_ID("userId"),
        RAW_PASSWORD("rawPassword"),
        USER_TYPE("user_type"),
        UPDATED_COURSES("updated_courses"),
        // FLOW_NO("flow_no"),
        ADD_APPRO_COURSE("add_appro_course"),

        SELECTED_COURSE_APPL_FLOW_NO("selected_course_appl_flow_no"),

;

        public final String name;
    }
}
