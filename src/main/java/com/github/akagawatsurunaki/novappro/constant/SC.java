package com.github.akagawatsurunaki.novappro.constant;

import lombok.AllArgsConstructor;

/**
 * 即ServletConstant, 保存了一些防止硬编码的常量
 */
public class SC {

    @AllArgsConstructor
    public enum ReqAttr {
        LOGIN_USER_ID("login_user_id"),
        LOGIN_USER("login_user"),
        COURSE_APPLICATIONS("course_applications"),
        UPDATE_APPLIED_COURSES_SERVICE_RESULT("updateAppliedCoursesServiceResult"),
        SELECTED_COURSE_APPL_FLOW_NO("selected_course_appl_flow_no"),
        APPRO_STATUS_LIST("appro_status_list"),
        ALL_APPROVAL_AUTHORITY_ITEMS("all_approval_authority_items"),
        COURSES_CAN_BE_APPLIED("courses_can_be_applied");

        public final String name;
    }


    @AllArgsConstructor
    public enum ReqParam {
        UPDATED_COURSES("updated_courses"),
        SELECTED_COURSE_APPL_FLOW_NO("selected_course_appl_flow_no"),
        SELECTED_APPL_ITEM_FLOW_NO("selected_appl_item_flow_no"),
        APPL_REMARK("appl_remark"),
        APPL_ITEM_CONFIRM("appl_item_confirm"),
        UPDATED_USERS("updated_users"),
        UPDATED_APPRO_AUTHO("updated_appro_autho[]");

        public final String name;
    }
}
