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

@WebServlet(name = "ApplItemDetailServlet", value = ServletConstant.WebServletValue.GET_APPL_ITEM_DETAIL)
public class ApplItemDetailServlet extends HttpServlet {

    private final static ApprovalService APPROVAL_SERVICE = ApprovalService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        var flowNo = request.getParameter(ServletConstant.RequestParam.SELECTED_APPL_ITEM_FLOW_NO.name);

        var vc_aid = APPROVAL_SERVICE.getDetail(flowNo);

        if (vc_aid.getLeft() == VerifyCode.Service.OK) {
            var aid = vc_aid.getRight();
            request.setAttribute(ServletConstant.RequestAttr.SELECTED_APPL_ITEM_DETAIL.name, aid);
            request.getRequestDispatcher(ServletConstant.JSPResource.GET_CRS_APPL_ITEM.name).forward(request, response);
            // request.removeAttribute(ServletConstant.RequestAttr.SELECTED_APPL_ITEM_DETAIL.name);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        doGet(request, response);
    }
}
