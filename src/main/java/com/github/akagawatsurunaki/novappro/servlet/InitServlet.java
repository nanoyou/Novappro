package com.github.akagawatsurunaki.novappro.servlet;

import com.alibaba.fastjson2.JSONObject;
import com.github.akagawatsurunaki.novappro.init.DatabaseManager;
import com.github.akagawatsurunaki.novappro.mapper.UserMapper;
import com.github.akagawatsurunaki.novappro.mapper.impl.UserMapperImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.PreparedStatement;

@WebServlet(name = "InitServlet", value = "/InitServlet")
public class InitServlet extends HttpServlet {

    private final static DatabaseManager DATABASE_MANAGER = new DatabaseManager();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 测试用例: http://localhost:8080/Novappro_war_exploded/InitServlet

        // 初始化数据库
        DATABASE_MANAGER.init();
        String s = JSONObject.toJSONString(UserMapperImpl.getInstance().getUserById(1));
        System.out.println(s);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }


}
