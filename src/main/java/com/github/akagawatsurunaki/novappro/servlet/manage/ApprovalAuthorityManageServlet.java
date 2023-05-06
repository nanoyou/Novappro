package com.github.akagawatsurunaki.novappro.servlet.manage;

import com.github.akagawatsurunaki.novappro.annotation.Field;
import com.github.akagawatsurunaki.novappro.constant.SC;
import com.github.akagawatsurunaki.novappro.service.manage.ApprovalAuthorityService;
import lombok.val;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;


@WebServlet(name = "ApprovalAuthorityManageServlet",
        value = {
        "/" + ApprovalAuthorityManageServlet.GET,
        SC.WebServletValue.UPDATE_APPRO_AUTHO_ITEMS})
public class ApprovalAuthorityManageServlet extends HttpServlet {

    public static final String GET = "ApprovalAuthorityManageServlet";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        val approvalAuthorityItems =
                ApprovalAuthorityService.getInstance().getApprovalAuthorityItems();
        request.setAttribute(SC.ReqAttr.ALL_APPROVAL_AUTHORITY_ITEMS.name, approvalAuthorityItems);
        request.getRequestDispatcher(SC.JSPResource.APPROVAL_AUTHORITY_MANAGE.name).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        val approverIdParam = request.getParameter("approverId");
        val appproWeightParam = request.getParameter("appproWeight");
        val courseCodeParam = request.getParameter("courseCode");
        val updatedApproAuthosParam = request.getParameterValues(SC.ReqParam.UPDATED_APPRO_AUTHO.name);

        val serviceMessage =
                ApprovalAuthorityService.getInstance()
                        .update(updatedApproAuthosParam, approverIdParam, appproWeightParam, courseCodeParam);

        request.setAttribute(SC.ReqAttr.UPDATE_MESSAGE.name, serviceMessage);

        doGet(request, response);

    }
}
