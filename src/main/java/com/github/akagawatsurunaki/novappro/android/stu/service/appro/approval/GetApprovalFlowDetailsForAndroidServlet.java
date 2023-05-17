package com.github.akagawatsurunaki.novappro.android.stu.service.appro.approval;

import com.alibaba.fastjson2.JSON;
import com.github.akagawatsurunaki.novappro.constant.SC;
import com.github.akagawatsurunaki.novappro.service.appro.ApprovalService;
import com.github.akagawatsurunaki.novappro.util.ResponseUtil;
import lombok.val;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "GetApprovalFlowDetailsForAndroidServlet", value = "/android/approvalService/getApprovalFlowDetails")
public class GetApprovalFlowDetailsForAndroidServlet extends HttpServlet {

    private static final ApprovalService APPROVAL_SERVICE = ApprovalService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        request.setCharacterEncoding("UTF-8");
        // 获取流水号
        val flowNo = request.getParameter("flowNo");
        // 获取该流水号下的申请的课程 (可能有多个)
        val getApprovalFlowDetailsServiceResult = APPROVAL_SERVICE.getApprovalFlowDetails(flowNo);
        val jsonString = JSON.toJSONString(getApprovalFlowDetailsServiceResult);
        ResponseUtil.setBody(response, jsonString);
    }
}
