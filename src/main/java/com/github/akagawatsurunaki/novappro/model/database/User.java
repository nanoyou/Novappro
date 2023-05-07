package com.github.akagawatsurunaki.novappro.model.database;

import com.github.akagawatsurunaki.novappro.annotation.Field;
import com.github.akagawatsurunaki.novappro.annotation.Table;
import com.github.akagawatsurunaki.novappro.annotation.ZhField;
import com.github.akagawatsurunaki.novappro.enumeration.UserType;
import lombok.*;
import lombok.experimental.FieldNameConstants;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
@Table(value = "user")
public class User {

    @ZhField(value = "学号/工号")
    @Field(value = "id")
    Integer id;

    @ZhField(value = "姓名")
    @Field(value = "username")
    String username;

    @ZhField(value = "明文密码")
    @Field(value = "raw_password")
    String rawPassword;

    @ZhField(value = "用户类型")
    @Field(value = "type")
    UserType type;

}
