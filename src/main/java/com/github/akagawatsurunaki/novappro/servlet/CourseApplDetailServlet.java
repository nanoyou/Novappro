package com.github.akagawatsurunaki.novappro.servlet;

import com.github.akagawatsurunaki.novappro.constant.ServletConstant;
import com.github.akagawatsurunaki.novappro.service.CourseApplDetailService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "CourseApplDetailServlet", value = "/course_appl_detail")
public class CourseApplDetailServlet extends HttpServlet {

    private final static CourseApplDetailService instance = new CourseApplDetailService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {

        var flowCode = request.getParameter(ServletConstant.RequestParam.SELECTED_COURSE_APPL_FLOW_NO.name);
        // 获取该流水号下的申请的课程 (可能有多个)
        var courses = instance.getAppliedCourses(flowCode);
        request.setAttribute(ServletConstant.RequestAttr.SELECTED_COURSE_APPL_FLOW_NO.name, flowCode);
        request.setAttribute(ServletConstant.RequestAttr.APPLIED_COURSES.name, courses);
        request.getRequestDispatcher(ServletConstant.JSPResource.COURSE_APPL_DETAIL.name).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {

    }
}
