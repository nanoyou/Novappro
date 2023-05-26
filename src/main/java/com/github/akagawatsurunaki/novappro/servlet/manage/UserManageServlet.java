package com.github.akagawatsurunaki.novappro.servlet.manage;

import com.github.akagawatsurunaki.novappro.constant.JSPResource;
import com.github.akagawatsurunaki.novappro.constant.SC;
import com.github.akagawatsurunaki.novappro.service.manage.UserManageService;
import lombok.AllArgsConstructor;
import lombok.val;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "ManagementServlet", value = {"/userManage"})
public class UserManageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        try {
            val getAllUsersServiceResult = UserManageService.getInstance().getAllUsers();
            request.setAttribute(ReqAttr.ALL_USERS.value, getAllUsersServiceResult);
            request.getRequestDispatcher(JSPResource.USER_MANAGE.value).forward(request, response);
            request.removeAttribute(ReqAttr.ALL_USERS.value);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
//        try {
//            val userIds = request.getParameterValues(SC.ReqParam.UPDATED_USERS.name);
//            val pair = UserManageService.getInstance().updateAllUsers(userIds);
//            doGet(request, response);
//        } catch (Exception e) {
//            e.printStackTrace();
//            response.sendRedirect("error.jsp");
//        }
        doGet(request, response);
    }

    @AllArgsConstructor
    public enum ReqAttr {
        ALL_USERS("all_users");

        public final String value;
    }
}
