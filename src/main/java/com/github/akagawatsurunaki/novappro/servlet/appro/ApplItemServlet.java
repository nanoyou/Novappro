package com.github.akagawatsurunaki.novappro.servlet.appro;

import com.github.akagawatsurunaki.novappro.constant.SC;
import com.github.akagawatsurunaki.novappro.constant.VerifyCode;
import com.github.akagawatsurunaki.novappro.service.appro.ApprovalService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ApplItemServlet", value = "/ApplItemServlet")
public class ApplItemServlet extends HttpServlet {

    private static final ApprovalService APPROVAL_SERVICE = ApprovalService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {

        String flowNo = request.getParameter(SC.RequestParam.SELECTED_COURSE_APPL_FLOW_NO.name);

        if (flowNo == null || flowNo.isBlank()) {
            // 空号
            return;
        }

        var vc_applItem = APPROVAL_SERVICE.getApplItem(flowNo);
        if (vc_applItem.getLeft() == VerifyCode.Service.OK) {
            var selectedApplItem = vc_applItem.getRight();
            request.setAttribute(SC.RequestAttr.SELECTED_APPL_ITEM.name, selectedApplItem);
            request.getRequestDispatcher(SC.JSPResource.GET_CRS_APPL_ITEM.name).forward(request, response);
            return;
        }
        response.sendRedirect(SC.JSPResource.ERROR.name);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        doGet(request, response);
    }
}
