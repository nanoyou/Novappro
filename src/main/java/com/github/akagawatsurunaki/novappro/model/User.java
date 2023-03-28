package com.github.akagawatsurunaki.novappro.model;

import com.alibaba.fastjson2.annotation.JSONType;
import lombok.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

        public static Type getType(String chineseName) {
            for (var t: Type.values()){
                if (t.getChineseName().equals(chineseName)){
                    return t;
                }
            }
            throw new IllegalArgumentException(Type.class.getName() + "错误");
        }
    }

    public static List<User> parseResultSet(ResultSet rs)  {
        List<User> result = new ArrayList<>();

        try{
            while (rs.next()){
                Integer id = rs.getInt("id");
                String username = rs.getString("username");
                String rawPassword = rs.getString("raw_password");
                // TODO: 测试这里的type转换是否正常
                System.out.println(rs.getString("type"));
                String strType = rs.getString("type");
                Type type = Type.getType(strType);

                User user = new User(id, username, rawPassword, type);
                result.add(user);
            }
        } catch (Exception ignored){;
        }
        return result;
    }

}
