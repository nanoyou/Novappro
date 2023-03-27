package com.github.akagawatsurunaki.novappro.test;

import com.github.akagawatsurunaki.novappro.model.User;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class UserTable {

    @Getter
    private static List<User> users;

    @Getter
    private static boolean isInited = false;

    public static void init() {
        if (isInited) {
            return;
        }
        isInited = true;
        users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            users.add(
                    new User(i,
                            Integer.toString(i),
                            Integer.toString(12345678),
                            User.Type.STUDENT)
            );
        }
        users.add(new User(11, "张三", "12345678", User.Type.STUDENT));
        users.add(new User(12, "李四", "12345678", User.Type.TEACHER));
    }
}
