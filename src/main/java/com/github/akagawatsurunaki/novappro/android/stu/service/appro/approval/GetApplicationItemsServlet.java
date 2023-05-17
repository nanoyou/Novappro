package com.github.akagawatsurunaki.novappro.android.stu.service.appro.approval;

import com.alibaba.fastjson2.JSON;
import com.github.akagawatsurunaki.novappro.model.database.User;
import com.github.akagawatsurunaki.novappro.service.appro.ApprovalService;
import com.github.akagawatsurunaki.novappro.servlet.appro.ApproServlet;
import com.github.akagawatsurunaki.novappro.servlet.base.LoginServlet;
import com.github.akagawatsurunaki.novappro.util.ResponseUtil;
import lombok.val;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "GetApplicationItemsServlet", value = "/android/approvalService/getApplicationItems")
public class GetApplicationItemsServlet extends HttpServlet {

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
        // 获取登录用户
        val loginUser = (User) request.getSession().getAttribute(LoginServlet.ReqAttr.LOGIN_USER.value);
        // 过滤器已经过滤掉未登录用户的请求, 这里的用户不应该为null
        assert loginUser != null;
        // 获取该用户名下的所有ApplItem
        val getApplItemsServiceMessage = APPROVAL_SERVICE.getApplItems(loginUser.getId());
        val jsonString = JSON.toJSONString(getApplItemsServiceMessage);
        ResponseUtil.setBody(response, jsonString);
    }
}
