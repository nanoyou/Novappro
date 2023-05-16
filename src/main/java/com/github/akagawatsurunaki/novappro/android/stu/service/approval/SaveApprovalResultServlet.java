package com.github.akagawatsurunaki.novappro.android.stu.service.approval;

import com.alibaba.fastjson2.JSON;
import com.github.akagawatsurunaki.novappro.constant.SC;
import com.github.akagawatsurunaki.novappro.service.appro.ApprovalService;
import com.github.akagawatsurunaki.novappro.util.ResponseUtil;
import lombok.val;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "SaveApprovalResultServlet",
        value = "/android/approvalService/saveApprovalResult")
public class SaveApprovalResultServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        request.setCharacterEncoding("UTF-8");

        String flowNo = request.getParameter("flowNo");
        String applRemark = request.getParameter("remark");
        String applItemConfirm = request.getParameter("confirm");

        val saveApproResultServiceResult
                = ApprovalService.getInstance().saveApproResult(flowNo, applRemark,
                applItemConfirm);
        val jsonString = JSON.toJSONString(saveApproResultServiceResult);
        ResponseUtil.setBody(response, jsonString);
    }
}
