package com.github.akagawatsurunaki.novappro.servlet.stu;

import com.github.akagawatsurunaki.novappro.constant.JSPResource;
import com.github.akagawatsurunaki.novappro.model.frontend.ServiceMessage;
import com.github.akagawatsurunaki.novappro.service.appro.ApprovalService;
import lombok.AllArgsConstructor;
import lombok.val;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "StudentConfirmApprovalFlowServlet", value = "/StudentConfirmApprovalFlowServlet")
public class StudentConfirmApprovalFlowServlet extends HttpServlet {

    private static final ApprovalService APPROVAL_SERVICE = ApprovalService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        try {
            val confirm = request.getParameter(ReqParam.CONFIRM.value);
            val flowNo = request.getParameter(ReqParam.FLOW_NO.value);
            val serviceMessage = APPROVAL_SERVICE.tryFinishApprovalFlow(flowNo, confirm);
            if (serviceMessage.getMessageLevel().equals(ServiceMessage.Level.SUCCESS)){
                request.getRequestDispatcher("apply_courses").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            response.sendRedirect(JSPResource.ERROR.value);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        doGet(request, response);
    }

    @AllArgsConstructor
    public enum ReqParam {

        CONFIRM("confirm"),
        FLOW_NO("flow_no");

        public final String value;
    }

}
