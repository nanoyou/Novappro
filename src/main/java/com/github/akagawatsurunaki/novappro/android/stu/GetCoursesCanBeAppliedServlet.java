package com.github.akagawatsurunaki.novappro.android.stu;

import com.alibaba.fastjson2.JSON;
import com.github.akagawatsurunaki.novappro.service.stu.CourseService;
import com.github.akagawatsurunaki.novappro.util.ResponseUtil;
import lombok.val;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "GetCoursesCanBeAppliedServlet", value = "/android/getCoursesCanBeApplied")
public class GetCoursesCanBeAppliedServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        val getCoursesCanBeAppliedServiceResult = CourseService.getInstance().getCoursesCanBeApplied();
        val jsonString = JSON.toJSONString(getCoursesCanBeAppliedServiceResult);
        ResponseUtil.setBody(response, jsonString);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
