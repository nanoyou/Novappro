package com.github.akagawatsurunaki.novappro.servlet;

import com.github.akagawatsurunaki.novappro.constant.ServletConstant;
import com.github.akagawatsurunaki.novappro.constant.VerifyCode;
import com.github.akagawatsurunaki.novappro.service.CourseService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.github.akagawatsurunaki.novappro.constant.LiteralConstant.COURSES;
import static com.github.akagawatsurunaki.novappro.constant.ServletConstant.GET_COURSE_SERVLET;

@WebServlet(name = "GetCoursesServlet", urlPatterns = GET_COURSE_SERVLET)
public class GetCoursesServlet extends HttpServlet {

    public static final String JSP_RESOURCE = "courses.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        var resultPair = CourseService.getInstance().getAllCourses();

        var verifyCode = resultPair.getLeft();
        var courses = resultPair.getRight();

        if (verifyCode == VerifyCode.Service.OK) {
            request.setAttribute(ServletConstant.INFO, "成功查询到" + courses.size() + "门课程。");
        } else {
            request.setAttribute(ServletConstant.INFO, "查询服务故障，请联系管理员。");
        }

        request.setAttribute(verifyCode.getClass().getSimpleName(), verifyCode);
        request.setAttribute(COURSES, courses);
        request.getRequestDispatcher(JSP_RESOURCE).forward(request, response);
    }

}
