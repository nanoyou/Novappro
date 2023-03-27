package com.github.akagawatsurunaki.novappro.servlet;

import com.alibaba.fastjson2.JSONObject;
import com.github.akagawatsurunaki.novappro.enumeration.VerifyCode;
import com.github.akagawatsurunaki.novappro.model.User;
import com.github.akagawatsurunaki.novappro.service.LoginService;
import com.github.akagawatsurunaki.novappro.test.UserTable;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet(name = "ServletLogin", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        func(request, response);
    }

//    void func(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
//        // 测试用例: http://localhost:8080/Novappro_war_exploded/login?username=1&rawPassword=12345678
//
//        // 在没有数据库的情况下使用, 之后将不会使用这段代码
//        UserTable.init();
//
//        Integer userId = Integer.valueOf(request.getParameter("userId"));
//        String rawPassword = request.getParameter("rawPassword");
//
//        if (rawPassword == null) {
//            return;
//        }
//
//        Map<String, Object> loginResult = LoginService.getInstance().tryLoginWithUserId(userId, rawPassword);
//
//        final String VERIFY_CODE = VerifyCode.class.getName();
//        final String USER = User.class.getName();
//
//        if (loginResult.get(VERIFY_CODE) == VerifyCode.OK) {
//            User user = (User) loginResult.get(USER);
//
//            List<Cookie> cookies = new ArrayList<>();
//            cookies.add(new Cookie("user_username", user.getUsername()));
//            cookies.add(new Cookie("user_type", user.getType().getChineseName()));
//            cookies.forEach(response::addCookie);
//
//            response.sendRedirect("welcome_cookie.jsp");
//        } else {
//            request.getRequestDispatcher("error.jsp").forward(request, response);
//        }
//    }


    void func(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 测试用例: http://localhost:8080/Novappro_war_exploded/login?userId=1&rawPassword=12345678

        // 在没有数据库的情况下使用, 之后将不会使用这段代码
        UserTable.init();

        Integer userId = Integer.valueOf(request.getParameter("userId"));
        String rawPassword = request.getParameter("rawPassword");

        if (rawPassword == null) {
            return;
        }

        Map<String, Object> loginResult = LoginService.getInstance().tryLoginWithUserId(userId, rawPassword);

        final String VERIFY_CODE = VerifyCode.class.getName();
        final String USER = User.class.getName();

        if (loginResult.get(VERIFY_CODE) == VerifyCode.OK) {
            request.setAttribute(USER, loginResult.get(USER));
            request.getRequestDispatcher("welcome.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        func(request, response);
    }
}
