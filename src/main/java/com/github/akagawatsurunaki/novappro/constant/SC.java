package com.github.akagawatsurunaki.novappro.constant;

import lombok.AllArgsConstructor;

/**
 * 即ServletConstant, 保存了一些防止硬编码的常量
 */
public class SC {
    public static final String GET_COURSE_SERVLET = "/get_courses";

    public static final String INFO = "info";

    @AllArgsConstructor
    public enum JSPResource {
        WELCOME("welcome.jsp"),
        WELCOME_SESSION("welcome_session.jsp"),
        WELCOME_COOKIE("welcome_cookie.jsp"),
        ERROR("error.jsp"),
        COURSE_APPL_DETAIL("course_appl_detail.jsp"),
        GET_APPLIED_COURSES("get_applied_courses.jsp"),
        GET_APPROS("get_appros.jsp"),
        GET_CRS_APPL_ITEM("get_crs_appl_item.jsp");

        public final String name;
    }

    @AllArgsConstructor
    public enum ReqAttr {
        USER_ID("user_id"),
        LOGIN_USER_ID("login_user_id"),

        LOGIN_USER("login_user"),
        COURSE_APPLICATIONS("course_applications"),
        USER_USERNAME("user_username"),
        APPLIED_COURSES("applied_courses"),
        SELECTED_COURSE_APPL_FLOW_NO("selected_course_appl_flow_no"),
        APPL_ITEMS_WITH_GIVEN_APPROVER("APPL_ITEMS_WITH_GIVEN_APPROVER"),
        SELECTED_APPL_ITEM("selected_appl_item"),
        SELECTED_APPL_ITEM_DETAIL("selected_appl_item_detail");

        public final String name;
    }


    @AllArgsConstructor
    public enum ReqParam {
        USER_ID("userId"),
        RAW_PASSWORD("rawPassword"),
        USER_TYPE("user_type"),
        UPDATED_COURSES("updated_courses"),
        // FLOW_NO("flow_no"),
        ADD_APPRO_COURSE("add_appro_course"),

        SELECTED_COURSE_APPL_FLOW_NO("selected_course_appl_flow_no"),

        SELECTED_APPL_ITEM_FLOW_NO("selected_appl_item_flow_no"),
        APPL_REMARK("appl_remark"),
        APPL_ITEM_CONFIRM("appl_item_confirm")
        ;



        public final String name;
    }

    public static class WebServletValue {
        public final static String MODIFY_COURSE_APPL = "/modify_course_appl";
        public final static String COURSE_APPL_DETAIL = "/course_appl_detail";
        public final static String GET_APPROS = "/get_appros";
        public final static String GET_APPL_ITEM_DETAIL = "/get_appl_item_detail";

    }
}
