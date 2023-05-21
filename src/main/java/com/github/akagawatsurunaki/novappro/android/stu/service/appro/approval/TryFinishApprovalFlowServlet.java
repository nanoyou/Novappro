package com.github.akagawatsurunaki.novappro.android.stu.service.appro.approval;

import com.alibaba.fastjson2.JSON;
import com.github.akagawatsurunaki.novappro.constant.Constant;
import com.github.akagawatsurunaki.novappro.service.appro.ApprovalService;
import com.github.akagawatsurunaki.novappro.util.ResponseUtil;
import lombok.val;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "TryFinishApprovalFlowServlet", value = "/android/approvalService/tryFinishApprovalFlow")
public class TryFinishApprovalFlowServlet extends HttpServlet {

    private static final ApprovalService APPROVAL_SERVICE = ApprovalService.getInstance();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        try {
            request.setCharacterEncoding("UTF-8");
            val confirm = request.getParameter("confirm");
            val flowNo = request.getParameter("flowNo");
            val serviceMessage = APPROVAL_SERVICE.tryFinishApprovalFlow(flowNo, confirm);
            ResponseUtil.setBody(response, JSON.toJSONString(serviceMessage));
        } catch (Exception e) {
            e.printStackTrace();
            ResponseUtil.setBody(response, JSON.toJSONString(Constant.exceptionServiceMessage));
        }
    }
}
