package com.github.akagawatsurunaki.novappro.servlet;

import com.github.akagawatsurunaki.novappro.constant.ServletConstant;
import com.github.akagawatsurunaki.novappro.model.course.Course;
import com.github.akagawatsurunaki.novappro.service.CourseService;
import org.apache.commons.lang3.tuple.Pair;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.github.akagawatsurunaki.novappro.constant.LiteralConstant.COURSES;
import static com.github.akagawatsurunaki.novappro.constant.ServletConstant.GET_COURSE_SERVLET;
import static com.github.akagawatsurunaki.novappro.service.CourseService.VerifyCode.*;

@WebServlet(name = "GetCoursesServlet", urlPatterns = GET_COURSE_SERVLET)
public class GetCoursesServlet extends HttpServlet {

    public static final String JSP_RESOURCE = "courses.jsp";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Pair<CourseService.VerifyCode, List<Course>> resultPair = CourseService.getInstance().getAllCourses();
        CourseService.VerifyCode verifyCode = resultPair.getLeft();
        List<Course> courses = resultPair.getRight();
        if (verifyCode == SERVICE_OK) {
            request.setAttribute(ServletConstant.INFO, "成功查询到" + courses.size() + "门课程。");
        } else if (verifyCode == SERVICE_OK_BUT_EMPTY_COURSES_LIST) {
            request.setAttribute(ServletConstant.INFO, "没有查询到课程。");
        } else if (verifyCode == SERVICE_ERROR) {
            request.setAttribute(ServletConstant.INFO, "查询服务故障，请联系管理员。");
        }
        request.setAttribute(verifyCode.getClass().getSimpleName(), verifyCode);
        request.setAttribute(COURSES, courses);
        request.getRequestDispatcher(JSP_RESOURCE).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
