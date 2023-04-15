package com.github.akagawatsurunaki.novappro.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class Table {

    @AllArgsConstructor
    public enum HasApproval{


        USER_ID("user_id"),
        APPROVAL_PROCESS_NODE_CODE("approval_process_node_code");

        public final String field;
        public static final String TABLE_NAME ="has_approval";
    }
}
