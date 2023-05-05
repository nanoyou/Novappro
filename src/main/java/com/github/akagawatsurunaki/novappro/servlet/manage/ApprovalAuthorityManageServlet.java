package com.github.akagawatsurunaki.novappro.servlet.manage;

import com.github.akagawatsurunaki.novappro.constant.SC;
import com.github.akagawatsurunaki.novappro.service.manage.ApprovalAuthorityService;
import lombok.val;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

import static com.github.akagawatsurunaki.novappro.constant.SC.ReqAttr.ALL_APPROVAL_AUTHORITY_ITEMS;

@WebServlet(name = "ApprovalAuthorityManageServlet", value = "/ApprovalAuthorityManageServlet")
public class ApprovalAuthorityManageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        val approvalAuthorityItems = ApprovalAuthorityService.getInstance().getApprovalAuthorityItems();
        request.setAttribute(ALL_APPROVAL_AUTHORITY_ITEMS.name, approvalAuthorityItems);
        request.getRequestDispatcher(SC.JSPResource.APPROVAL_AUTHORITY_MANAGE.name).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // TODO： 对更新后的权限列表进行刷新，只能每次增加一条，然后刷新本页面
        doGet(request, response);

    }
}
