package com.github.akagawatsurunaki.novappro.servlet.appro;

import com.github.akagawatsurunaki.novappro.constant.SC;
import com.github.akagawatsurunaki.novappro.model.database.User;
import com.github.akagawatsurunaki.novappro.service.appro.ApprovalService;
import com.github.akagawatsurunaki.novappro.servlet.base.LoginServlet;
import lombok.AllArgsConstructor;
import lombok.val;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ApproServlet", value = "/get_appros")
public class ApproServlet extends HttpServlet {

    private static final ApprovalService APPROVAL_SERVICE = ApprovalService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            IOException {
        try {
            request.setCharacterEncoding("UTF-8");
            // 获取登录用户
            val loginUser = (User) request.getSession().getAttribute(LoginServlet.ReqAttr.LOGIN_USER.value);
            // 过滤器已经过滤掉未登录用户的请求, 这里的用户不应该为null
            assert loginUser != null;
            // 获取该用户名下的所有ApplItem
            val getApplItemsServiceMessage = APPROVAL_SERVICE.getApplItems(loginUser.getId());
            request.setAttribute(ReqAttr.GET_APPL_ITEMS_SERVICE_MESSAGE.value, getApplItemsServiceMessage);
            // 转发到页面
            request.getRequestDispatcher(SC.JSPResource.GET_APPROS.name).forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            response.sendRedirect("error.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        doGet(request, response);
    }

    @AllArgsConstructor
    public enum ReqAttr {

        GET_APPL_ITEMS_SERVICE_MESSAGE("get_appl_items_service_message");

        public final String value;
    }
}
