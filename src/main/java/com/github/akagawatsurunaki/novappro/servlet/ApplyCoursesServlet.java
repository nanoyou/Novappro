package com.github.akagawatsurunaki.novappro.servlet;

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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String[] selectedCourseCodes = request.getParameterValues("selected_course[]");
        if (selectedCourseCodes == null) {
            return;
        }
        List<String> selectedCourseCodeList = Arrays.stream(selectedCourseCodes).toList();
        if (selectedCourseCodeList.isEmpty()) {
            return;
        }

        Integer id = (Integer) request.getSession().getAttribute("login_user_id");

        APPLY_COURSE_SERVICE.apply(id, selectedCourseCodeList);



    }
}
