package com.github.akagawatsurunaki.novappro.interfase;

import com.github.akagawatsurunaki.novappro.annotation.ChineseFieldName;
import lombok.NonNull;

import java.lang.reflect.Field;

public interface HasChineseField {

    default  <T> ChineseFieldName getChineseFieldNameAnnotation(Class<T> clazz, @NonNull String fieldName) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            // 如果一个Field上既有注解又和指定的名称相同, 则返回注解
            if (field.isAnnotationPresent(ChineseFieldName.class)) {
                if (fieldName.equals(field.getName())){
                    return field.getAnnotation(ChineseFieldName.class);
                }
            }
        }
        return null;
    }
}
