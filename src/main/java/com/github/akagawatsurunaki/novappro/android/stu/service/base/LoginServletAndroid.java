package com.github.akagawatsurunaki.novappro.android.stu.service.base;

import cn.hutool.extra.servlet.ServletUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.github.akagawatsurunaki.novappro.model.database.User;
import com.github.akagawatsurunaki.novappro.model.frontend.ServiceMessage;
import com.github.akagawatsurunaki.novappro.model.frontend.ServiceResult;
import com.github.akagawatsurunaki.novappro.service.base.LoginService;
import com.github.akagawatsurunaki.novappro.util.ResponseUtil;
import lombok.val;
import org.apache.commons.lang3.tuple.Pair;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

@WebServlet(name = "LoginServletAndroid", value = "/android/login")
public class LoginServletAndroid extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // http://localhost:8080/Novappro_war_exploded/android/login?userId=20210002&rawPassword=1234567890
        val userId = request.getParameter("userId");
        val rawPassword = request.getParameter("rawPassword");
        val loginServiceResult = LoginService.getInstance().login(userId, rawPassword);
        request.getSession().setAttribute("login_user", loginServiceResult.getRight());
        
        val jsonString = JSON.toJSONString(loginServiceResult);
        ResponseUtil.setBody(response, jsonString);
    }
}
