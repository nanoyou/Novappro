package com.github.akagawatsurunaki.novappro.servlet.base;

import com.github.akagawatsurunaki.novappro.constant.SC;
import com.github.akagawatsurunaki.novappro.constant.VC;
import com.github.akagawatsurunaki.novappro.service.base.LoginService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        loginBySession(request, response);
    }
    private static final LoginService LOGIN_SERVICE = LoginService.getInstance();
    void loginBySession(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 测试用例: http://localhost:8080/Novappro_war_exploded/login?username=1&rawPassword=12345678

        // 获取用户的ID
        var userId = Optional.ofNullable(request.getParameter(SC.ReqParam.USER_ID.name));

        // 获取用户的明文密码
        var rawPassword = Optional.ofNullable(request.getParameter(SC.ReqParam.RAW_PASSWORD.name));

        // 如果用户的ID和明文密码都存在
        if (userId.isPresent() && rawPassword.isPresent()) {

            // 若用户ID为空
            if (userId.get().isBlank()) {
                request.setAttribute(SC.ReqAttr.ERROR_MESSAGE.name, VC.Service.USER_ID_NAN.message);
                response.sendRedirect(SC.JSPResource.INDEX.name);
                return;
            }

            // 若密码为空
            if (rawPassword.get().isBlank()) {
                request.setAttribute(SC.ReqAttr.ERROR_MESSAGE.name, VC.Service.BLANK_PASSWORD.message);
                response.sendRedirect(SC.JSPResource.INDEX.name);
                return;
            }

            try {

                // 尝试利用密码登陆
                var vc_user = LOGIN_SERVICE.tryLoginWithUserId(userId.map(Integer::parseInt).get(), rawPassword.get());

                switch (vc_user.getLeft()) {

                    case TOO_LONG_PASSWORD ->
                            request.setAttribute(SC.ReqAttr.ERROR_MESSAGE.name, VC.Service.TOO_LONG_PASSWORD.message);
                    case PASSWORD_ERROR ->
                            request.setAttribute(SC.ReqAttr.ERROR_MESSAGE.name, VC.Service.PASSWORD_ERROR.message);
                    case NO_SUCH_USER ->
                            request.setAttribute(SC.ReqAttr.ERROR_MESSAGE.name, VC.Service.NO_SUCH_USER.message);
                    case OK -> {

                        // 安全地取出用户对象
                        var user = vc_user.getRight();

                        request.getSession().setAttribute(SC.ReqAttr.LOGIN_USER_ID.name, user.getId());
                        request.getSession().setAttribute(SC.ReqAttr.USER_USERNAME.name, user.getUsername());
                        request.getSession().setAttribute(SC.ReqParam.USER_TYPE.name, user.getType().name());
                        request.getSession().setAttribute(SC.ReqAttr.LOGIN_USER.name, user);
                        request.setAttribute(SC.ReqAttr.LOGIN_USER.name, user);

                        // 根据不同的身份发送到不同的页面
                        switch (user.getType()) {
                            case STUDENT -> response.sendRedirect(SC.JSPResource.WELCOME_SESSION.name);
                            case LECTURE_TEACHER, SUPERVISOR_TEACHER ->
                                response.sendRedirect(SC.JSPResource.GET_APPROS.name);
                            case ADMIN -> System.out.println("尚未实现");
                        }
                        return;
                    }
                }
            } catch (NumberFormatException e) {
                request.setAttribute(SC.ReqAttr.ERROR_MESSAGE.name, VC.Service.USER_ID_NAN.message);
            }
        }
        request.getRequestDispatcher(SC.JSPResource.INDEX.name).forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        doGet(request, response);
    }
}
