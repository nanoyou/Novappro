package com.github.akagawatsurunaki.novappro.enumeration;

import com.github.akagawatsurunaki.novappro.annotation.ZhField;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldNameConstants;

@AllArgsConstructor
@FieldNameConstants
public enum UserType {
    @ZhField(value = "管理员")
    ADMIN,
    @ZhField(value = "教师")
    TEACHER,
    @ZhField(value = "学生")
    STUDENT


}
