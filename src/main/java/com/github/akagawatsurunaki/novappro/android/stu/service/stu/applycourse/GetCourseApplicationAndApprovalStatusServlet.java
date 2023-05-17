package com.github.akagawatsurunaki.novappro.android.stu.service.stu.applycourse;

import com.alibaba.fastjson2.JSON;
import com.github.akagawatsurunaki.novappro.constant.SC;
import com.github.akagawatsurunaki.novappro.model.database.User;
import com.github.akagawatsurunaki.novappro.service.stu.ApplyCourseService;
import com.github.akagawatsurunaki.novappro.util.ResponseUtil;
import lombok.val;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "GetCourseApplicationAndApprovalStatusServlet", value =
        "/android/applyCourseService/getCourseApplications")
public class GetCourseApplicationAndApprovalStatusServlet extends HttpServlet {

    private static final ApplyCourseService APPLY_COURSE_SERVICE = ApplyCourseService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        val id = ((User) request.getSession().getAttribute(SC.ReqAttr.LOGIN_USER.name)).getId();
        // 获取这些课程
        val getCourseApplsByUserIdServiceResult
                = APPLY_COURSE_SERVICE.getCourseApplsByUserIdForAndroid(id);
        val jsonString = JSON.toJSONString(getCourseApplsByUserIdServiceResult);
        ResponseUtil.setBody(response, jsonString);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        doGet(request, response);
    }
}
