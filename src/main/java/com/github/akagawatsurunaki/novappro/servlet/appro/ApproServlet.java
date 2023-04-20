package com.github.akagawatsurunaki.novappro.servlet.appro;

import com.github.akagawatsurunaki.novappro.constant.ServletConstant;
import com.github.akagawatsurunaki.novappro.constant.VerifyCode;
import com.github.akagawatsurunaki.novappro.service.appro.ApprovalService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ApproServlet", value = ServletConstant.WebServletValue.GET_APPROS)
public class ApproServlet extends HttpServlet {

    private static final ApprovalService APPROVAL_SERVICE = ApprovalService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {

        request.setCharacterEncoding("UTF-8");

        var loginUserId = (Integer) request.getSession().getAttribute(ServletConstant.RequestAttr.LOGIN_USER_ID.name);


        // 如果用户已经登录
        if (loginUserId != null) {

            // 获取该用户名下的所有ApplItem s.

            var vc_applItems = APPROVAL_SERVICE.getApplItems(loginUserId);

            if (vc_applItems.getLeft() == VerifyCode.Service.OK) {

                var applItems = vc_applItems.getRight();
                request.setAttribute(ServletConstant.RequestAttr.APPL_ITEMS_WITH_GIVEN_APPROVER.name, applItems);
                request.getRequestDispatcher(ServletConstant.JSPResource.GET_APPROS.name).forward(request, response);
                return;
            }

        }
        request.getRequestDispatcher(ServletConstant.JSPResource.ERROR.name).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {

    }
}
