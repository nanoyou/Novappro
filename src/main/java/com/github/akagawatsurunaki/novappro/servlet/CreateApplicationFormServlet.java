package com.github.akagawatsurunaki.novappro.servlet;

import com.github.akagawatsurunaki.novappro.mapper.impl.CourseMapperImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "CreateApplicationFormServlet", urlPatterns = "/createApplicationForm")
public class CreateApplicationFormServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CourseMapperImpl.getInstance().selectAllCourses();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
