package com.github.akagawatsurunaki.novappro.servlet.appro;

import com.github.akagawatsurunaki.novappro.constant.SC;
import com.github.akagawatsurunaki.novappro.constant.VC;
import com.github.akagawatsurunaki.novappro.service.appro.ApprovalService;

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {

        request.setCharacterEncoding("UTF-8");

        var loginUserId = (Integer) request.getSession().getAttribute(SC.ReqAttr.LOGIN_USER_ID.name);

        // TODO: DEBUG NEED

        // 如果用户已经登录
        if (loginUserId != null) {

            // 获取该用户名下的所有ApplItem s.

            var applItemList = APPROVAL_SERVICE.getApplItems(loginUserId);

            request.setAttribute(SC.ReqAttr.APPL_ITEMS_WITH_GIVEN_APPROVER.name, applItemList);
            request.getRequestDispatcher(SC.JSPResource.GET_APPROS.name).forward(request, response);
            return;

        }
        request.getRequestDispatcher(SC.JSPResource.ERROR.name).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {

    }
}
