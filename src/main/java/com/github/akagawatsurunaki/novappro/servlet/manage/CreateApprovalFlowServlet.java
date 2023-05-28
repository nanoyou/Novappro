package com.github.akagawatsurunaki.novappro.servlet.manage;

import com.github.akagawatsurunaki.novappro.model.database.User;
import com.github.akagawatsurunaki.novappro.service.manage.ApprovalFlowService;
import lombok.val;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CreateApprovalFlowServlet", value = "/createApprovalFlow")
public class CreateApprovalFlowServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        try {
            val loginUser = (User) request.getSession().getAttribute("login_user");
            if (loginUser == null) {
                response.sendRedirect("index.jsp");
                return;
            }
            val courseCode = request.getParameter("addCourseCode");
            val approverId = request.getParameter("approverId");
            val serviceMessage = ApprovalFlowService.getInstance().apply(loginUser.getId(), approverId, courseCode);
            request.setAttribute("createApprovalFlowServiceMessage", serviceMessage);
            request.getRequestDispatcher("/approvalFlowManage").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("index.jsp");
        }
    }
}
