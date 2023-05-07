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
        INDEX("index.jsp"),
        SIGN_UP("signup.jsp"),
        WELCOME_SESSION("welcome_session.jsp"),
        WELCOME_COOKIE("welcome_cookie.jsp"),
        ERROR("error.jsp"),
        COURSE_APPL_DETAIL("course_appl_detail.jsp"),
        GET_APPLIED_COURSES("get_applied_courses.jsp"),
        GET_APPROS("get_appros.jsp"),
        GET_CRS_APPL_ITEM("get_crs_appl_item.jsp")
        ,
        APPROVAL_AUTHORITY_MANAGE("approval_authority_manage.jsp")
        ;

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
        UPDATE_APPLIED_COURSES_SERVICE_RESULT("updateAppliedCoursesServiceResult"),
        SELECTED_COURSE_APPL_FLOW_NO("selected_course_appl_flow_no"),
        APPL_ITEMS_WITH_GIVEN_APPROVER("APPL_ITEMS_WITH_GIVEN_APPROVER"),
        SELECTED_APPL_ITEM("selected_appl_item"),
        SELECTED_APPL_ITEM_DETAIL("selected_appl_item_detail"),
        APPRO_STATUS_LIST("appro_status_list"),
        ERROR_MESSAGE("error_message"),
        ALL_USERS("all_users"),
        ALL_APPROVAL_AUTHORITY_ITEMS("all_approval_authority_items"),
        UPDATE_MESSAGE("update_message"),
        COURSES_CAN_BE_APPLIED("courses_can_be_applied")
        ;

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
        APPL_ITEM_CONFIRM("appl_item_confirm"),
        UPDATED_USERS("updated_users"),
        UPDATED_APPRO_AUTHO("updated_appro_autho[]")
        ;



        public final String name;
    }

    public static class WebServletValue {
        public final static String SIGNUP = "/signup";
        public final static String LOGIN = "/login";
        public final static String MODIFY_COURSE_APPL = "/modify_course_appl";
        public final static String COURSE_APPL_DETAIL = "/course_appl_detail";
        public final static String GET_APPROS = "/get_appros";
        public final static String GET_APPL_ITEM_DETAIL = "/get_appl_item_detail";
        public final static String APPLY_COURSES = "/apply_courses";
        public final static String APPL_ITEM = "/ApplItemServlet";
        public final static String UPDATE_APPRO_AUTHO_ITEMS = "/update_appro_autho_items";

    }
}
