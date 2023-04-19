package com.github.akagawatsurunaki.novappro.model.database;

import com.alibaba.fastjson2.annotation.JSONType;
import com.github.akagawatsurunaki.novappro.annotation.ZhField;
import lombok.*;
import lombok.experimental.FieldNameConstants;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
@JSONType(ignores = {"rawPassword"})
public class User {

    @ZhField(value = "学号/工号")
    Integer id;

    @ZhField(value = "姓名")
    String username;

    @ZhField(value = "明文密码")
    String rawPassword;

    @ZhField(value = "用户类型")
    Type type;

    @AllArgsConstructor
    @ToString
    public enum Type {

        ADMIN(233, "管理员"),
        TEACHER(10, "教师"),
        STUDENT(3, "学生");

        @Getter
        public final int value;
        @Getter
        public final String chineseName;

        public static Type getType(String chineseName) {
            for (var t: Type.values()){
                if (t.getChineseName().equals(chineseName)){
                    return t;
                }
            }
            throw new IllegalArgumentException(Type.class.getName() + "无法解析的错误");
        }
    }
}
