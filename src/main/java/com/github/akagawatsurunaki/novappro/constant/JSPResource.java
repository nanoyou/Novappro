package com.github.akagawatsurunaki.novappro.constant;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum JSPResource {
    INDEX("index.jsp"),
    WELCOME_SESSION("welcome_session.jsp"),
    SIGN_UP("signup.jsp"),
    COURSE_APPL_DETAIL("course_appl_detail.jsp"),
    GET_APPROS("get_appros.jsp"),
    GET_APPLIED_COURSES("get_applied_courses.jsp"),
    APPROVAL_AUTHORITY_MANAGE("approval_authority_manage.jsp"),
    GET_CRS_APPL_ITEM("get_crs_appl_item.jsp"),
    USER_MANAGE("user_manage.jsp")
    ;

    public final String value;
}
