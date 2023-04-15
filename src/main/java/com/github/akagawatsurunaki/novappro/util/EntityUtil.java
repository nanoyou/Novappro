package com.github.akagawatsurunaki.novappro.util;

import cn.hutool.core.util.EnumUtil;
import cn.hutool.db.Entity;
import com.github.akagawatsurunaki.novappro.annotation.Table;

import java.lang.reflect.Field;

public class EntityUtil {
    public static <T> Entity getEntity(Object obj) throws IllegalAccessException {

        var tClass = obj.getClass();
        var fields = tClass.getDeclaredFields();

        var entity = Entity.create();

        // 是否拥有@Table注解
        if (!tClass.isAnnotationPresent(Table.class)) {
            return null;
        }

        // 设置表名
        entity.setTableName(tClass.getAnnotation(Table.class).table());

        for (Field field : fields) {

            if (field.isAnnotationPresent(com.github.akagawatsurunaki.novappro.annotation.Field.class)) {
                var annotation =
                        field.getAnnotation(com.github.akagawatsurunaki.novappro.annotation.Field.class);

                // 将字段设为Accessible的
                field.setAccessible(true);

                // 如果是枚举类则转化为String类型
                if (EnumUtil.isEnum(field.getType())) {
                    entity.set(annotation.field(), field.get(obj).toString());
                    continue;
                }

                // 如果是Primitive类型则直接设置
                entity.set(annotation.field(), field.get(obj));

            }
        }
        return entity;
    }
}
