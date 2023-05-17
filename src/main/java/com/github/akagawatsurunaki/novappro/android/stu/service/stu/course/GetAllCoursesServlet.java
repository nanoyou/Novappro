package com.github.akagawatsurunaki.novappro.android.stu.service.stu.course;

import com.alibaba.fastjson2.JSON;
import com.github.akagawatsurunaki.novappro.service.stu.CourseService;
import com.github.akagawatsurunaki.novappro.util.ResponseUtil;
import lombok.val;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "GetAllCoursesServlet", value = "/android/courseService/getAllCourses")
public class GetAllCoursesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        val getAllCoursesServiceResult = CourseService.getInstance().getAllCourses();
        val jsonString = JSON.toJSONString(getAllCoursesServiceResult);
        ResponseUtil.setBody(response, jsonString);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
