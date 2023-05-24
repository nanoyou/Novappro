package com.github.akagawatsurunaki.novappro.servlet.manage;

import com.github.akagawatsurunaki.novappro.service.stu.CourseService;
import lombok.AllArgsConstructor;
import lombok.val;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CourseManageServlet", value = "/courseManage")
public class CourseManageServlet extends HttpServlet {

    private static final CourseService COURSE_SERVICE = CourseService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        try {
            val getAllCoursesServiceResult = COURSE_SERVICE.getAllCourses();
            request.setAttribute(ReqAttr.GET_ALL_COURSES_SERVICE_RESULT.value, getAllCoursesServiceResult);
            request.getRequestDispatcher("courses_manage.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            response.sendRedirect("error.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        doGet(request, response);
    }

    @AllArgsConstructor
    public enum ReqAttr {

        GET_ALL_COURSES_SERVICE_RESULT("get_all_courses_service_result");

        public final String value;
    }
}
