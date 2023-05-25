package com.github.akagawatsurunaki.novappro.servlet.base;

import com.github.akagawatsurunaki.novappro.constant.JSPResource;
import com.github.akagawatsurunaki.novappro.constant.SC;
import com.github.akagawatsurunaki.novappro.model.frontend.ServiceMessage;
import com.github.akagawatsurunaki.novappro.service.base.LoginService;
import lombok.AllArgsConstructor;
import lombok.val;
import org.apache.commons.lang3.NotImplementedException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LoginServlet", value = {"/login"})
public class LoginServlet extends HttpServlet {

    private static final LoginService LOGIN_SERVICE = LoginService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        loginBySession(request, response);
    }

    void loginBySession(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 测试用例: http://localhost:8080/Novappro_war_exploded/login?username=1&rawPassword=12345678
        try {
            // 获取用户的ID
            var userId = request.getParameter("userId");
            // 获取用户的明文密码
            var rawPassword = request.getParameter("rawPassword");

            // 尝试利用密码登陆
            val loginServiceResult = LOGIN_SERVICE.login(userId, rawPassword);

            request.setAttribute(ReqAttr.LOGIN_SERVICE_RESULT.value, loginServiceResult);

            if (loginServiceResult.getLeft().getMessageLevel() == ServiceMessage.Level.SUCCESS) {
                // 获取登录对象
                val user = loginServiceResult.getRight();
                request.getSession().setAttribute(ReqAttr.LOGIN_USER.value, user);
                switch (user.getType()) {
                    case STUDENT -> response.sendRedirect(JSPResource.WELCOME_SESSION.value);
                    case LECTURE_TEACHER, SUPERVISOR_TEACHER -> response.sendRedirect("get_appros");
                    case ADMIN -> response.sendRedirect("admin.jsp");
                }
                return;
            }

            // 登录失败, 返回主页
            request.getRequestDispatcher(JSPResource.INDEX.value).forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        doGet(request, response);
    }


    @AllArgsConstructor
    public enum ReqAttr {
        LOGIN_SERVICE_RESULT("login_service_result"),
        LOGIN_USER("login_user");

        public final String value;
    }
}
