package com.github.akagawatsurunaki.novappro.util;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.EnumUtil;
import cn.hutool.db.Entity;
import com.github.akagawatsurunaki.novappro.annotation.Table;
import lombok.NonNull;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class EntityUtil {

    public static <T> String getTableName(Object obj){
        var tClass = obj.getClass();

        // 是否拥有@Table注解
        if (!tClass.isAnnotationPresent(Table.class)) {
            return null;
        }

        return tClass.getAnnotation(Table.class).table();
    }

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

    public static <T> T parseEntity(@NonNull Class<T> tClass,
                                                       @NonNull Entity entity) {

        try {

            Object obj = tClass.getConstructor().newInstance();

            var fields = tClass.getDeclaredFields();

            // 是否拥有@Table注解
            if (!tClass.isAnnotationPresent(Table.class)) {
                return null;
            }

            for (Field field : fields) {
                if (field.isAnnotationPresent(com.github.akagawatsurunaki.novappro.annotation.Field.class)) {
                    // 将字段设为Accessible的
                    field.setAccessible(true);

                    var annotation = field.getAnnotation(com.github.akagawatsurunaki.novappro.annotation.Field.class);

                    // 如果是枚举类则转化
                    if (EnumUtil.isEnum(field.getType())) {

                        Object o = entity.get(annotation.field());
                        Object[] enumConstants = field.getType().getEnumConstants();

                        for (var enumConstant : enumConstants) {
                            if (enumConstant.toString().equals(o.toString())) {
                                field.set(obj, enumConstant);
                            }
                        }
                        continue;

                    }

                    Object value = entity.get(annotation.field());
                    field.set(obj, Convert.convert(field.getType(), value));
                }
            }
            return Convert.convert(tClass, obj);

        } catch (IllegalAccessException e) {
            return null;
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
