package com.github.akagawatsurunaki.novappro.servlet.manage;

import com.github.akagawatsurunaki.novappro.constant.SC;
import com.github.akagawatsurunaki.novappro.service.manage.UserManageService;
import lombok.val;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

import static com.github.akagawatsurunaki.novappro.constant.ServletValue.GET_USERS;
import static com.github.akagawatsurunaki.novappro.constant.ServletValue.UPDATE_USERS;

@WebServlet(name = "ManagementServlet", value = {"/ManagementServlet",
        "/" + GET_USERS, "/" + UPDATE_USERS})
public class UserManageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        val pair = UserManageService.getInstance().getAllUsers();
        request.setAttribute(SC.ReqAttr.ALL_USERS.name, pair);
        request.getRequestDispatcher("user_manage.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.printf(UPDATE_USERS);
        // TODO: 2023年5月5日 10点47分 此处进行更新操作


    }
}
