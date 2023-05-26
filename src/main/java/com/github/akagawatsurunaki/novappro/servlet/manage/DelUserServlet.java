package com.github.akagawatsurunaki.novappro.servlet.manage;

import com.alibaba.fastjson2.JSON;
import com.github.akagawatsurunaki.novappro.service.manage.UserManageService;
import lombok.val;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DelUserServlet", value = "/delUser")
public class DelUserServlet extends HttpServlet {

    private static final UserManageService USER_MANAGE_SERVICE = UserManageService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        try {
            USER_MANAGE_SERVICE.deleteUser(request.getParameter("delUserId"));
            val serviceResult = USER_MANAGE_SERVICE.getAllUsers();
            request.setAttribute("all_users", serviceResult);
            request.getRequestDispatcher("user_manage.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        doGet(request, response);
    }
}
