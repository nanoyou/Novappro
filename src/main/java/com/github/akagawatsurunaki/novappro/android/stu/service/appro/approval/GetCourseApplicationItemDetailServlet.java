package com.github.akagawatsurunaki.novappro.android.stu.service.appro.approval;

import com.alibaba.fastjson2.JSON;
import com.github.akagawatsurunaki.novappro.constant.SC;
import com.github.akagawatsurunaki.novappro.model.database.User;
import com.github.akagawatsurunaki.novappro.service.appro.ApprovalService;
import com.github.akagawatsurunaki.novappro.util.ResponseUtil;
import lombok.val;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "GetCourseApplicationItemDetailServlet",
        value = "/android/approvalService/getCourseApplicationItemDetail")
public class GetCourseApplicationItemDetailServlet extends HttpServlet {

    private static final ApprovalService APPROVAL_SERVICE = ApprovalService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        // 获取参数
        val flowNo = request.getParameter("flowNo");
        val approver = (User) request.getSession().getAttribute(SC.ReqAttr.LOGIN_USER.name);
        // 获取课程申请明细项目
        val getCourseAppItemDetailServiceResult = APPROVAL_SERVICE.getCourseAppItemDetail(flowNo, approver.getId());
        val jsonString = JSON.toJSONString(getCourseAppItemDetailServiceResult);
        ResponseUtil.setBody(response, jsonString);
    }
}
