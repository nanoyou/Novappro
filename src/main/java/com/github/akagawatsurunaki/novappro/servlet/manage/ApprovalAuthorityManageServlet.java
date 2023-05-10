package com.github.akagawatsurunaki.novappro.servlet.manage;

import com.github.akagawatsurunaki.novappro.constant.JSPResource;
import com.github.akagawatsurunaki.novappro.constant.SC;
import com.github.akagawatsurunaki.novappro.service.manage.ApprovalAuthorityService;
import lombok.AllArgsConstructor;
import lombok.val;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "ApprovalAuthorityManageServlet",
        value = {
        "/" + ApprovalAuthorityManageServlet.GET,
        "/update_appro_autho_items"})
public class ApprovalAuthorityManageServlet extends HttpServlet {

    public static final String GET = "ApprovalAuthorityManageServlet";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        val approvalAuthorityItems =
                ApprovalAuthorityService.getInstance().getApprovalAuthorityItems();
        request.setAttribute(SC.ReqAttr.ALL_APPROVAL_AUTHORITY_ITEMS.name, approvalAuthorityItems);
        request.getRequestDispatcher(JSPResource.APPROVAL_AUTHORITY_MANAGE.value).forward(request, response);
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

        request.setAttribute(ReqAttr.UPDATE_MESSAGE.value, serviceMessage);

        doGet(request, response);

    }

    @AllArgsConstructor
    public enum ReqAttr {
        UPDATE_MESSAGE("update_message");

        public final String value;
    }


}
