package com.github.akagawatsurunaki.novappro.android.stu.service.approval;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "TryFinishApprovalFlowServlet", value = "/android/approvalService/tryFinishApprovalFlow")
public class TryFinishApprovalFlowServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        // TODO(尚未实现)
    }
}
