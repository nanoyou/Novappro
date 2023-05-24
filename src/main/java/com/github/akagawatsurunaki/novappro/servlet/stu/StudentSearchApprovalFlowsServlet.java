package com.github.akagawatsurunaki.novappro.servlet.stu;

import com.github.akagawatsurunaki.novappro.model.database.User;
import com.github.akagawatsurunaki.novappro.service.base.SearchService;
import lombok.val;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "StudentSearchApprovalFlowsServlet", value = "/studentSearch")
public class StudentSearchApprovalFlowsServlet extends HttpServlet {

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
            val loginUserId = ((User) request.getSession().getAttribute("login_user")).getId();
            val getPageServiceResult = SEARCH_SERVICE.getPageAsStudentView(page, search, loginUserId.toString());
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
