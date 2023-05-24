package com.github.akagawatsurunaki.novappro.servlet.base;

import com.github.akagawatsurunaki.novappro.model.database.User;
import com.github.akagawatsurunaki.novappro.service.base.RegisterService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "SignUpServlet", value = "/signup")
public class SignUpServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            // 防止中文变成乱码, 必须添加在首部
            request.setCharacterEncoding("UTF-8");
            Object username = request.getParameter("username");
            Object rawPassword = request.getParameter("rawPassword");
            Object confirmedRawPassword = request.getParameter("confirmedRawPassword");

            var verifyCodeUserPair = RegisterService.getInstance().trySignUp(username, rawPassword,
                    confirmedRawPassword);

            User user = verifyCodeUserPair.getRight();
            request.setAttribute("user", user);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
