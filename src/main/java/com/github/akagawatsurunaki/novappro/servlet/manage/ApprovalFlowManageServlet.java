package com.github.akagawatsurunaki.novappro.servlet.manage;

import com.github.akagawatsurunaki.novappro.service.appro.ApprovalService;
import lombok.AllArgsConstructor;
import lombok.val;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ApprovalFlowManageServlet", value = "/ApprovalFlowManageServlet")
public class ApprovalFlowManageServlet extends HttpServlet {
    private static final ApprovalService APPROVAL_SERVICE = ApprovalService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {

            val getAllApprovalFlowsServiceResult = APPROVAL_SERVICE.getAllApprovalFlows();
            request.setAttribute(ReqAttr.GET_ALL_APPROVAL_FLOWS_SERVICE_RESULT.value, getAllApprovalFlowsServiceResult);
            request.getRequestDispatcher("approval_flow_manage.jsp").forward(request, response);

        } catch (Exception e){
            e.printStackTrace();
        }
        finally {
            response.sendRedirect("error.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @AllArgsConstructor
    public enum ReqAttr {

        GET_ALL_APPROVAL_FLOWS_SERVICE_RESULT("get_all_approval_flows_service_result");

        public final String value;
    }
}
