package com.github.akagawatsurunaki.novappro.servlet.init;

import com.alibaba.fastjson2.JSONObject;
import com.github.akagawatsurunaki.novappro.annotation.Database;
import com.github.akagawatsurunaki.novappro.config.DatabaseConfig;
import com.github.akagawatsurunaki.novappro.config.InitSequenceConfig;
import com.github.akagawatsurunaki.novappro.mapper.impl.UserMapperImpl;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Set;

@WebServlet(name = "InitDatabaseServlet", urlPatterns = {"/initDatabase"}, loadOnStartup = InitSequenceConfig.INIT_DATABASE)
public class InitDatabaseServlet extends HttpServlet {

    private final static DatabaseManager DATABASE_MANAGER = new DatabaseManager();

    @Override
    public void init() throws ServletException {
        // 测试用例: http://localhost:8080/Novappro_war_exploded/InitServlet

        // 初始化数据库
        DATABASE_MANAGER.init();
        String s = JSONObject.toJSONString(UserMapperImpl.getInstance().selectUserById(1));
        System.out.println(s);
    }
}

class DatabaseManager {

    private Connection connection;

    public void init() {
        if (!initDatabase()) {
            System.out.println(Connection.class.getName() + "是null, 数据库连接初始化失败.");
            return;
        }
        // 将被注入的类
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

        if (!fields.isEmpty()) {
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