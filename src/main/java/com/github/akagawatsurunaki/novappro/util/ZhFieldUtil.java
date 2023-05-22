package com.github.akagawatsurunaki.novappro.util;

import com.github.akagawatsurunaki.novappro.annotation.ZhField;
import com.github.akagawatsurunaki.novappro.exception.NoZhFieldAnnotation;
import lombok.NonNull;
import lombok.val;

import java.lang.reflect.Field;

public final class ZhFieldUtil {

    public static <T> String getZhValue(Class<T> cls) {
        val annotation = cls.getAnnotation(ZhField.class);
        if (annotation == null) {
            return null;
        }
        return annotation.value();
    }

    public static <T> String getZhValue(Class<T> cls, @NonNull String fieldName) {
        // 获取所有字段
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            // 如果一个Field上既有注解又和指定的名称相同, 则返回注解
            if (field.isAnnotationPresent(ZhField.class)) {
                if (fieldName.equals(field.getName())) {
                    return field.getAnnotation(ZhField.class).value();
                }
            }
        }
        throw new NoZhFieldAnnotation();
    }

    public static <T> String getZhDesc(Class<T> cls, @NonNull String fieldName) {
        // 获取所有字段
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            // 如果一个Field上既有注解又和指定的名称相同, 则返回注解
            if (field.isAnnotationPresent(ZhField.class)) {
                if (fieldName.equals(field.getName())) {
                    return field.getAnnotation(ZhField.class).desc();
                }
            }
        }
        throw new NoZhFieldAnnotation();
    }
}
