package com.github.akagawatsurunaki.novappro.servlet.manage;

import com.github.akagawatsurunaki.novappro.service.appro.ApprovalService;
import lombok.AllArgsConstructor;
import lombok.val;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ApprovalFlowManageServlet", value = "/approvalFlowManage")
public class ApprovalFlowManageServlet extends HttpServlet {
    private static final ApprovalService APPROVAL_SERVICE = ApprovalService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        try {
            val getAllApprovalFlowsServiceResult = APPROVAL_SERVICE.getAllApprovalFlows();
            request.setAttribute(ReqAttr.GET_ALL_APPROVAL_FLOWS_SERVICE_RESULT.value, getAllApprovalFlowsServiceResult);
            request.getRequestDispatcher("approval_flow_manage.jsp").forward(request, response);
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

        GET_ALL_APPROVAL_FLOWS_SERVICE_RESULT("get_all_approval_flows_service_result");

        public final String value;
    }
}
