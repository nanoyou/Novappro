package com.github.akagawatsurunaki.novappro.android.stu.service.stu.courseappldetail;

import com.alibaba.fastjson2.JSONObject;
import com.github.akagawatsurunaki.novappro.constant.SC;
import com.github.akagawatsurunaki.novappro.service.stu.CourseApplDetailService;
import com.github.akagawatsurunaki.novappro.util.ResponseUtil;
import lombok.val;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "GetAppliedCoursesServlet", value = "/android/courseApplicationDetailService/getAppliedCourses")
public class GetAppliedCoursesServlet extends HttpServlet {

    private static final CourseApplDetailService COURSE_APPL_DETAIL_SERVICE = CourseApplDetailService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        // 获取流水号
        val flowNo = request.getParameter("flowNo");
        // 获取该流水号下的申请的课程 (可能有多个)
        val getAppliedCoursesServiceResult = COURSE_APPL_DETAIL_SERVICE.getAppliedCourses(flowNo);
        val jsonString = JSONObject.toJSONString(getAppliedCoursesServiceResult);
        ResponseUtil.setBody(response, jsonString);
        // val getApprovalFlowDetailsServiceResult = APPROVAL_SERVICE.getApprovalFlowDetails(flowNo);
    }
}
