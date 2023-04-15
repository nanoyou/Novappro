package com.github.akagawatsurunaki.novappro.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "GetAppliedCoursesServlet", value = "/GetAppliedCoursesServlet")
public class GetAppliedCoursesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取已经申请的课程
    }


}
