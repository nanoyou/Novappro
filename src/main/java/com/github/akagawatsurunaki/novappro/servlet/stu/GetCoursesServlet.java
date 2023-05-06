package com.github.akagawatsurunaki.novappro.servlet.stu;

import com.github.akagawatsurunaki.novappro.constant.SC;
import com.github.akagawatsurunaki.novappro.service.stu.CourseService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.github.akagawatsurunaki.novappro.constant.SC.GET_COURSE_SERVLET;

@WebServlet(name = "GetCoursesServlet", urlPatterns = GET_COURSE_SERVLET)
public class GetCoursesServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        var resultPair = CourseService.getInstance().getCoursesCanBeApplied();
        request.setAttribute(SC.ReqAttr.COURSES_CAN_BE_APPLIED.name, resultPair);
        request.getRequestDispatcher("courses.jsp").forward(request, response);
    }

}
