package com.github.akagawatsurunaki.novappro.servlet.stu;

import com.github.akagawatsurunaki.novappro.model.database.User;
import com.github.akagawatsurunaki.novappro.model.database.approval.ApprovalFlow;
import com.github.akagawatsurunaki.novappro.model.frontend.ServiceMessage;
import com.github.akagawatsurunaki.novappro.service.base.SearchService;
import lombok.val;
import org.apache.commons.lang3.tuple.ImmutablePair;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

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
            val loginUser = (User) request.getSession().getAttribute("login_user");
            if (loginUser == null){
                request.setAttribute("getPageServiceResult", ImmutablePair.of(
                        ServiceMessage.of(ServiceMessage.Level.ERROR, "用户未登录"),
                        new ArrayList<ApprovalFlow>()
                ));
                return;
            }

            if (page == null && search == null) {
                request.setAttribute("getPageServiceResult", ImmutablePair.of(
                        ServiceMessage.of(ServiceMessage.Level.INFO, "查询服务已就绪"),
                        new ArrayList<ApprovalFlow>()
                ));
                request.getRequestDispatcher("search_approval_flows.jsp").forward(request, response);
                return;
            }

            val loginUserId = loginUser.getId();
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
