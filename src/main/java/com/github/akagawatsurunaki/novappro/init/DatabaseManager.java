package com.github.akagawatsurunaki.novappro.init;

import com.github.akagawatsurunaki.novappro.annotation.Database;
import com.github.akagawatsurunaki.novappro.config.DatabaseConfig;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Set;

public class DatabaseManager {

    private Connection connection;

    public void init() {
        if (!initDatabase()) {
            System.out.println(Connection.class.getName() + "是null, 数据库连接初始化失败.");
            return;
        }
        String packageName = "com.github.akagawatsurunaki.novappro.mapper.impl";
        scanAnnotations(packageName);
    }

    private void scanAnnotations(String packageName) {

        Reflections f = new Reflections(
                new ConfigurationBuilder()
                        .setUrls(ClasspathHelper.forPackage(packageName))
                        .setScanners(Scanners.FieldsAnnotated)
        );

        Set<Field> fields = f.getFieldsAnnotatedWith(Database.class);

        if (!fields.isEmpty()){
            fields.forEach(field -> {
                if (field != null) {
                    try {
                        field.setAccessible(true);
                        field.set(Connection.class, connection);
                    } catch (IllegalAccessException e) {
                        System.out.println("不能注入" + Connection.class.getName());
                        throw new RuntimeException(e);
                    }
                }
            });
        }


    }

    private boolean initDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    DatabaseConfig.getURL(),
                    DatabaseConfig.getUSER(),
                    DatabaseConfig.getPASSWORD());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
