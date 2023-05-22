package com.github.akagawatsurunaki.novappro.android.stu.service.manage;

import com.alibaba.fastjson2.JSON;
import com.github.akagawatsurunaki.novappro.service.manage.UserManageService;
import com.github.akagawatsurunaki.novappro.util.ResponseUtil;
import lombok.val;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ResetUserPasswordServlet", value = "/android/userManageService/resetUserPassword")
public class ResetUserPasswordServlet extends HttpServlet {

    private static final UserManageService USER_MANAGE_SERVICE = UserManageService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            request.setCharacterEncoding("UTF-8");
            val userId = request.getParameter("userId");
            val serviceResult = USER_MANAGE_SERVICE.resetUserPassword(userId);
            ResponseUtil.setBody(response, JSON.toJSONString(serviceResult));
        } catch (Exception e) {
            e.printStackTrace();
            ResponseUtil.setErrBody(response);
        }
    }
}
