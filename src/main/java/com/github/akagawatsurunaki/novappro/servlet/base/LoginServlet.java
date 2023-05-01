package com.github.akagawatsurunaki.novappro.servlet.base;

import com.github.akagawatsurunaki.novappro.constant.SC;
import com.github.akagawatsurunaki.novappro.constant.VerifyCode;
import com.github.akagawatsurunaki.novappro.enumeration.UserType;
import com.github.akagawatsurunaki.novappro.model.database.User;
import com.github.akagawatsurunaki.novappro.service.base.LoginService;
import com.github.akagawatsurunaki.novappro.util.ZhFieldUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ServletLogin", urlPatterns = SC.WebServletValue.LOGIN)
public class LoginServlet extends HttpServlet {

    private static final By CURRENT_LOGIN_MODE = By.SESSION;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        login(CURRENT_LOGIN_MODE, request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        login(CURRENT_LOGIN_MODE, request, response);
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

        Integer userId = Integer.valueOf(request.getParameter(SC.ReqParam.USER_ID.name));
        String rawPassword = request.getParameter(SC.ReqParam.RAW_PASSWORD.name);

        if (rawPassword == null) {
            return;
        }

        var verifyCodeUserPair = LoginService.getInstance().tryLoginWithUserId(userId,
                rawPassword);
        var verifyCode = verifyCodeUserPair.getLeft();
        User user = verifyCodeUserPair.getRight();

        if (verifyCode == VerifyCode.Service.OK) {

            request.getSession().setAttribute(SC.ReqAttr.LOGIN_USER_ID.name, user.getId());
            request.getSession().setAttribute(SC.ReqAttr.USER_USERNAME.name, user.getUsername());
            request.getSession().setAttribute(SC.ReqParam.USER_TYPE.name, user.getType().name());

            request.setAttribute(SC.ReqAttr.LOGIN_USER.name,user);

            // 根据不同的身份发送到不同的页面
            if (user.getType().equals(UserType.STUDENT)){
                response.sendRedirect(SC.JSPResource.WELCOME_SESSION.name);
            } else if (user.getType().equals(UserType.TEACHER)) {
                // ServletConstant.JSPResource.GET_APPROS.name
                request.getRequestDispatcher(SC.WebServletValue.GET_APPROS).forward(request, response);
              //  response.sendRedirect(ServletConstant.JSPResource.GET_APPROS.name);
            } else if(user.getType().equals(UserType.ADMIN)){
                return;
            }

        } else {
            request.getRequestDispatcher(SC.JSPResource.ERROR.name).forward(request, response);
        }
    }

    void loginByCookie(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 测试用例: http://localhost:8080/Novappro_war_exploded/login?username=1&rawPassword=12345678

        Integer userId = Integer.valueOf(request.getParameter(SC.ReqParam.USER_ID.name));
        String rawPassword = request.getParameter(SC.ReqParam.RAW_PASSWORD.name);

        if (rawPassword == null) {
            return;
        }

        var verifyCodeUserPair = LoginService.getInstance().tryLoginWithUserId(userId, rawPassword);
        var verifyCode = verifyCodeUserPair.getLeft();
        User user = verifyCodeUserPair.getRight();

        if (verifyCode == VerifyCode.Service.OK) {

            List<Cookie> cookies = new ArrayList<>();
            cookies.add(new Cookie(SC.ReqAttr.USER_USERNAME.name, user.getUsername()));
            cookies.add(new Cookie(SC.ReqParam.USER_TYPE.name,
                    ZhFieldUtil.getZhValue(User.class, user.getType().name())));
            cookies.forEach(response::addCookie);

            response.sendRedirect(SC.JSPResource.WELCOME_COOKIE.name);
        } else {
            request.getRequestDispatcher(SC.JSPResource.ERROR.name).forward(request, response);
        }
    }

    void loginByURL(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 测试用例: http://localhost:8080/Novappro_war_exploded/login?userId=1&rawPassword=12345678

        Integer userId = Integer.valueOf(request.getParameter(SC.ReqParam.USER_ID.name));
        String rawPassword = request.getParameter(SC.ReqParam.RAW_PASSWORD.name);

        if (rawPassword == null) {
            return;
        }

        var verifyCodeUserPair = LoginService.getInstance().tryLoginWithUserId(userId, rawPassword);
        var verifyCode = verifyCodeUserPair.getLeft();
        // User user = verifyCodeUserPair.getRight();

        if (verifyCode == VerifyCode.Service.OK) {
            request.getRequestDispatcher(SC.JSPResource.WELCOME.name).forward(request, response);
        } else {
            request.getRequestDispatcher(SC.JSPResource.ERROR.name).forward(request, response);
        }
    }

    enum By {
        SESSION,
        COOKIE,
        URL
    }

}
