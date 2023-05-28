package com.github.akagawatsurunaki.novappro.servlet.appro;

import com.github.akagawatsurunaki.novappro.service.base.SearchService;
import lombok.val;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "TeacherSearchApprovalFlowsServlet", value = "/teacherSearch")
public class TeacherSearchApprovalFlowsServlet extends HttpServlet {

    private static final SearchService SEARCH_SERVICE = SearchService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setCharacterEncoding("UTF-8");
            request.getRequestDispatcher("search_approval_flows.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setCharacterEncoding("UTF-8");
            val page = request.getParameter("page");
            val search = request.getParameter("search");
            val getPageServiceResult = SEARCH_SERVICE.getPageAsTeacherView(page, search);
            request.setAttribute("getPageServiceResult", getPageServiceResult);
            request.setAttribute("page", page);
            request.setAttribute("search", search);
            request.getRequestDispatcher("search_approval_flows.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}
