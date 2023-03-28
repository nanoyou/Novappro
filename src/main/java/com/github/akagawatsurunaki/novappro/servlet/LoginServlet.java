package com.github.akagawatsurunaki.novappro.servlet;

import com.github.akagawatsurunaki.novappro.enumeration.VerifyCode;
import com.github.akagawatsurunaki.novappro.model.User;
import com.github.akagawatsurunaki.novappro.service.LoginService;
import com.github.akagawatsurunaki.novappro.test.UserTable;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet(name = "ServletLogin", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    static final By CURRENT_LOGIN_MODE = By.SESSION;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        login(CURRENT_LOGIN_MODE, request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        login(CURRENT_LOGIN_MODE, request, response);
    }

    enum By {
        SESSION,
        COOKIE,
        URL
    }

    void login(By by, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        switch (by) {
            case URL:
                loginByURL(request, response);
                break;
            case COOKIE:
                loginByCookie(request, response);
                break;
            case SESSION:
                loginBySession(request, response);
                break;
        }

    }

    void loginBySession(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 测试用例: http://localhost:8080/Novappro_war_exploded/login?username=1&rawPassword=12345678

        // 在没有数据库的情况下使用, 之后将不会使用这段代码
        UserTable.init();

        request.getRemoteAddr();

        Integer userId = Integer.valueOf(request.getParameter("userId"));
        String rawPassword = request.getParameter("rawPassword");

        if (rawPassword == null) {
            return;
        }

        Map<String, Object> loginResult = LoginService.getInstance().tryLoginWithUserId(userId, rawPassword);

        final String VERIFY_CODE = VerifyCode.class.getName();
        final String USER = User.class.getName();

        if (loginResult.get(VERIFY_CODE) == VerifyCode.OK) {

            User user = (User) loginResult.get(USER);

            request.getSession().setAttribute("user_username", user.getUsername());
            request.getSession().setAttribute("user_type", user.getType().getChineseName());

            response.sendRedirect("welcome_session.jsp");
        } else {
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    void loginByCookie(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 测试用例: http://localhost:8080/Novappro_war_exploded/login?username=1&rawPassword=12345678

        // 在没有数据库的情况下使用, 之后将不会使用这段代码
        UserTable.init();

        Integer userId = Integer.valueOf(request.getParameter("userId"));
        String rawPassword = request.getParameter("rawPassword");

        if (rawPassword == null) {
            return;
        }

        Map<String, Object> loginResult = LoginService.getInstance().tryLoginWithUserId(userId, rawPassword);

        final String VERIFY_CODE = VerifyCode.class.getName();
        final String USER = User.class.getName();

        if (loginResult.get(VERIFY_CODE) == VerifyCode.OK) {
            User user = (User) loginResult.get(USER);

            List<Cookie> cookies = new ArrayList<>();
            cookies.add(new Cookie("user_username", user.getUsername()));
            cookies.add(new Cookie("user_type", user.getType().getChineseName()));
            cookies.forEach(response::addCookie);

            response.sendRedirect("welcome_cookie.jsp");
        } else {
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    void loginByURL(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 测试用例: http://localhost:8080/Novappro_war_exploded/login?userId=1&rawPassword=12345678

        // 在没有数据库的情况下使用, 之后将不会使用这段代码
        UserTable.init();

        Integer userId = Integer.valueOf(request.getParameter("userId"));
        String rawPassword = request.getParameter("rawPassword");

        if (rawPassword == null) {
            return;
        }

        Map<String, Object> loginResult = LoginService.getInstance().tryLoginWithUserId(userId, rawPassword);

        final String VERIFY_CODE = VerifyCode.class.getName();
        final String USER = User.class.getName();

        if (loginResult.get(VERIFY_CODE) == VerifyCode.OK) {
            request.setAttribute(USER, loginResult.get(USER));
            request.getRequestDispatcher("welcome.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }


}
