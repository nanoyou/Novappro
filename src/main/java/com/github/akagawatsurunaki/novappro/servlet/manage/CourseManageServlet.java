package com.github.akagawatsurunaki.novappro.servlet.manage;

import com.github.akagawatsurunaki.novappro.service.stu.CourseService;
import lombok.AllArgsConstructor;
import lombok.val;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "CourseManageServlet", value = "/course_manage")
public class CourseManageServlet extends HttpServlet {

    private static final CourseService COURSE_SERVICE = CourseService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {

            val getAllCoursesServiceResult = COURSE_SERVICE.getAllCourses();
            request.setAttribute(ReqAttr.GET_ALL_COURSES_SERVICE_RESULT.value, getAllCoursesServiceResult);
            request.getRequestDispatcher("courses_manage.jsp").forward(request, response);

        } catch (Exception e){
            e.printStackTrace();
        }
        finally {
            response.sendRedirect("error.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @AllArgsConstructor
    public enum ReqAttr {

        GET_ALL_COURSES_SERVICE_RESULT("get_all_courses_service_result");

        public final String value;
    }
}
