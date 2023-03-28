package com.github.akagawatsurunaki.novappro.servlet;

import com.alibaba.fastjson2.JSONObject;
import com.github.akagawatsurunaki.novappro.config.DatabaseConfig;
import com.github.akagawatsurunaki.novappro.mapper.UserMapper;
import com.github.akagawatsurunaki.novappro.mapper.impl.UserMapperImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebServlet(name = "InitServlet", value = "/InitServlet")
public class InitServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 测试用例: http://localhost:8080/Novappro_war_exploded/InitServlet
        Connection connection = initDatabase();
        UserMapperImpl.getInstance().setConnection(connection);
        String s = JSONObject.toJSONString(UserMapperImpl.getInstance().getUser(UserMapper.By.ID, 1));
        System.out.println(s);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private Connection initDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(
                    DatabaseConfig.getURL(),
                    DatabaseConfig.getUSER(),
                    DatabaseConfig.getPASSWORD());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
