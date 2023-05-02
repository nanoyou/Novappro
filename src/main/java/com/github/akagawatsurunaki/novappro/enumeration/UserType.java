package com.github.akagawatsurunaki.novappro.enumeration;

import com.github.akagawatsurunaki.novappro.annotation.ZhField;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldNameConstants;

@AllArgsConstructor
@FieldNameConstants
public enum UserType {
    ADMIN("管理员"),
    LECTURE_TEACHER("课程主讲教师"),
    SUPERVISOR_TEACHER("课程主管教师"),
    STUDENT("学生");

    public final String chinese;

}
