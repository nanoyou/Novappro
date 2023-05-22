package com.github.akagawatsurunaki.novappro.android.stu.service.manage;

import com.alibaba.fastjson2.JSON;
import com.github.akagawatsurunaki.novappro.model.database.User;
import com.github.akagawatsurunaki.novappro.service.manage.UserManageService;
import com.github.akagawatsurunaki.novappro.util.RequestUtil;
import com.github.akagawatsurunaki.novappro.util.ResponseUtil;
import lombok.val;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UpdateUserServlet", value = "/android/userManageService/updateUser")
public class UpdateUserServlet extends HttpServlet {

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
//            val user = RequestUtil.<User>parse(request);
            val id = request.getParameter("id");
            val username = request.getParameter("username");
            val type = request.getParameter("type");

            val serviceResult = USER_MANAGE_SERVICE.updateUser(id, username, type);
            ResponseUtil.setBody(response, JSON.toJSONString(serviceResult));
        } catch (Exception e) {
            e.printStackTrace();
            ResponseUtil.setErrBody(response);
        }
    }
}
