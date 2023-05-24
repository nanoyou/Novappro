package com.github.akagawatsurunaki.novappro.android.stu.service.base;

import com.alibaba.fastjson2.JSON;
import com.github.akagawatsurunaki.novappro.service.base.SearchService;
import com.github.akagawatsurunaki.novappro.util.ResponseUtil;
import lombok.val;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "StudentSearchServlet", value = "/android/searchService/getPageAsStudentView")
public class StudentSearchServlet extends HttpServlet {

    private static final SearchService SEARCH_SERVICE = SearchService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        try {
            request.setCharacterEncoding("UTF-8");
            val page = request.getParameter("page");
            val search = request.getParameter("search");
            val userId = request.getParameter("userId");
            val result = SEARCH_SERVICE.getPageAsStudentView(page, search, userId);
            ResponseUtil.setBody(response, JSON.toJSONString(result));
        } catch (Exception e) {
            e.printStackTrace();
            ResponseUtil.setErrBody(response);
        }
    }
}
