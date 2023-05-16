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

@WebServlet(name = "GetApplicationItemServlet", value = "/android/applyCourseService/getApplicationItem")
public class GetApplicationItemServlet extends HttpServlet {

    private static final ApprovalService APPROVAL_SERVICE = ApprovalService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        // 获取单号
        val flowNo = request.getParameter("flowNo");
        // 获取服务结果
        val getApplItemServiceResult = APPROVAL_SERVICE.getApplItem(flowNo);
        val jsonString = JSON.toJSONString(getApplItemServiceResult);
        ResponseUtil.setBody(response, jsonString);
    }
}
