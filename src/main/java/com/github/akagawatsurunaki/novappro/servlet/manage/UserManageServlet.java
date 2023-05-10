package com.github.akagawatsurunaki.novappro.servlet.manage;

import com.github.akagawatsurunaki.novappro.constant.JSPResource;
import com.github.akagawatsurunaki.novappro.constant.SC;
import com.github.akagawatsurunaki.novappro.service.manage.UserManageService;
import lombok.AllArgsConstructor;
import lombok.val;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;


@WebServlet(name = "ManagementServlet", value = {"/ManagementServlet",
        "/get_users", "/update_users"})
public class UserManageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        val getAllUsersServiceResult = UserManageService.getInstance().getAllUsers();
        request.setAttribute(ReqAttr.ALL_USERS.value, getAllUsersServiceResult);
        request.getRequestDispatcher(JSPResource.USER_MANAGE.value).forward(request, response);
        request.removeAttribute(ReqAttr.ALL_USERS.value);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        val userIds = request.getParameterValues(SC.ReqParam.UPDATED_USERS.name);
        val pair = UserManageService.getInstance().updateAllUsers(userIds);
        doGet(request, response);
    }

    @AllArgsConstructor
    public enum ReqAttr {
        ALL_USERS("all_users");

        public final String value;
    }
}
