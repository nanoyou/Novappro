package com.github.akagawatsurunaki.novappro.servlet;

import com.github.akagawatsurunaki.novappro.constant.ServletConstant;
import com.github.akagawatsurunaki.novappro.service.ApplyCourseService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "GetAppliedCoursesServlet", value = ServletConstant.WebServletValue.GET_APPLIED_COURSES)
public class GetAppliedCoursesServlet extends HttpServlet {

    private static final ApplyCourseService APPLY_COURSE_SERVICE = ApplyCourseService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取已经申请的课程
        request.setCharacterEncoding("UTF-8");
        var userId = request.getSession().getAttribute(ServletConstant.RequestAttr.LOGIN_USER_ID.name);

       // var vc_courses = APPLY_COURSE_SERVICE.getAppliedCourses((Integer) userId);

//        if (vc_courses.getLeft() == VerifyCode.Service.OK) {
//            request.setAttribute(ServletConstant.RequestAttr.APPLIED_COURSES.name, vc_courses.getRight());
//            System.out.println(vc_courses.getRight());
//
//        }

       // request.getRequestDispatcher("get_applied_courses.jsp").forward(request, response);

    }



}
