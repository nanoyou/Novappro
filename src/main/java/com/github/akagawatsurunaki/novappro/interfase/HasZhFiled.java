package com.github.akagawatsurunaki.novappro.interfase;

import com.github.akagawatsurunaki.novappro.annotation.ZhField;
import com.github.akagawatsurunaki.novappro.model.frontend.ApplItem;
import lombok.NonNull;

import java.lang.reflect.Field;

public interface HasZhFiled {
    static String getZhValue(@NonNull String fieldName){
        Field[] fields = ApplItem.class.getDeclaredFields();
        for (Field field : fields) {
            // 如果一个Field上既有注解又和指定的名称相同, 则返回注解
            if (field.isAnnotationPresent(ZhField.class)) {
                if (fieldName.equals(field.getName())){
                    return field.getAnnotation(ZhField.class).value();
                }
            }
        }
        return null;
    }
}
