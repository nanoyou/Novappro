package com.github.akagawatsurunaki.novappro.servlet;

import com.github.akagawatsurunaki.novappro.constant.ServletConstant;
import com.github.akagawatsurunaki.novappro.service.ApplyCourseService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "ApplyCoursesServlet", value = "/apply_courses")
public class ApplyCoursesServlet extends HttpServlet {

    private static final ApplyCourseService APPLY_COURSE_SERVICE = ApplyCourseService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        request.setCharacterEncoding("UTF-8");

        // 获取登录人ID
        Integer id = (Integer) request.getSession().getAttribute("login_user_id");

        // 获取选取的课程列表
        String[] selectedCourseCodes = request.getParameterValues("selected_course[]");

        if (selectedCourseCodes != null) {
            if (selectedCourseCodes.length > 0) {

                List<String> selectedCourseCodeList = Arrays.stream(selectedCourseCodes).toList();
                // 申请这些课程
                APPLY_COURSE_SERVICE.apply(id, selectedCourseCodeList);
            }
        }

        // 获取这些课程
        var courseApplications = APPLY_COURSE_SERVICE.getAppliedCourses(id).getRight();

        // 设置到 Request 中
        request.setAttribute(ServletConstant.RequestAttr.COURSE_APPLICATIONS.name, courseApplications);

        // 跳转页面
        request.getRequestDispatcher("get_applied_courses.jsp").forward(request, response);
    }
}
