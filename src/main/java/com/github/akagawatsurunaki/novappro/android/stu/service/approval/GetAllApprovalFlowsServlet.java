package com.github.akagawatsurunaki.novappro.android.stu.service.approval;

import com.alibaba.fastjson2.JSON;
import com.github.akagawatsurunaki.novappro.service.appro.ApprovalService;
import com.github.akagawatsurunaki.novappro.util.ResponseUtil;
import lombok.val;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "GetAllApprovalFlowsServlet", value = "/android/approvalService/getAllApprovalFlows")
public class GetAllApprovalFlowsServlet extends HttpServlet {

    private static final ApprovalService APPROVAL_SERVICE = ApprovalService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        val getApplItemServiceResult = APPROVAL_SERVICE.getAllApprovalFlows();
        val jsonString = JSON.toJSONString(getApplItemServiceResult);
        ResponseUtil.setBody(response, jsonString);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        doGet(request, response);
    }
}
