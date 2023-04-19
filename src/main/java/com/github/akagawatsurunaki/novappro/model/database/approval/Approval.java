package com.github.akagawatsurunaki.novappro.model.database.approval;

import com.github.akagawatsurunaki.novappro.annotation.Field;
import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Approval {
    @Field(field = "flow_no")
    String flowNo;

    @Field(field = "add_user_id")
    Integer addUserId;

    @Field(field = "add_time")
    Date addTime;
}
