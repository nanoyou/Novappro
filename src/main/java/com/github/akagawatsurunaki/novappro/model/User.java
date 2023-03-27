package com.github.akagawatsurunaki.novappro.model;

import com.alibaba.fastjson2.annotation.JSONType;
import lombok.*;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JSONType(ignores = {"rawPassword"})
public class User {
    Integer id;
    String username;
    String rawPassword;
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
    }
}
