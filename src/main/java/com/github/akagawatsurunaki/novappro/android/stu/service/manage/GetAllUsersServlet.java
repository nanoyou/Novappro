package com.github.akagawatsurunaki.novappro.android.stu.service.manage;

import com.alibaba.fastjson2.JSON;
import com.github.akagawatsurunaki.novappro.service.manage.UserManageService;
import com.github.akagawatsurunaki.novappro.util.ResponseUtil;
import lombok.val;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "GetAllUsersServlet", value = "/android/userManageService/getAllUsers")
public class GetAllUsersServlet extends HttpServlet {

    private static final UserManageService USER_MANAGE_SERVICE = UserManageService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        try {
            request.setCharacterEncoding("UTF-8");
            val serviceMessage = USER_MANAGE_SERVICE.getAllUsers();
            ResponseUtil.setBody(response, JSON.toJSONString(serviceMessage));
        } catch (Exception e) {
            e.printStackTrace();
            ResponseUtil.setErrBody(response);
        }
    }
}
