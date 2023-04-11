package com.github.akagawatsurunaki.novappro.servlet;

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

import static com.github.akagawatsurunaki.novappro.constant.ServletConstant.GET_COURSE_SERVLET;
import static com.github.akagawatsurunaki.novappro.service.CourseService.VerifyCode.*;

@WebServlet(name = "GetCoursesServlet", urlPatterns = GET_COURSE_SERVLET)
public class GetCoursesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Pair<CourseService.VerifyCode, List<Course>> resultPair = CourseService.getInstance().getAllCourses();
        CourseService.VerifyCode verifyCode = resultPair.getLeft();
        if (verifyCode == SERVICE_OK) {
            resultPair.getRight().forEach(System.out::println);
        } else if (verifyCode == SERVICE_OK_BUT_EMPTY_COURSES_LIST) {
            System.out.println("Empty courses");
        } else if (verifyCode == SERVICE_ERROR) {
            System.out.println("ERROR!!!!");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
