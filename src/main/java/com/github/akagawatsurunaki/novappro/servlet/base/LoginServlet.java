package com.github.akagawatsurunaki.novappro.servlet.base;

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

        // 获取用户的ID
        var userId = request.getParameter(SC.ReqParam.USER_ID.name);

        // 获取用户的明文密码
        var rawPassword = request.getParameter(SC.ReqParam.RAW_PASSWORD.name);

        // 尝试利用密码登陆
        val loginServiceResult = LOGIN_SERVICE.login(userId, rawPassword);

        request.setAttribute(ReqAttr.LOGIN_SERVICE_RESULT.value, loginServiceResult);

        if (loginServiceResult.getLeft().getMessageLevel() == ServiceMessage.Level.SUCCESS) {
            // 获取登录对象
            val user = loginServiceResult.getRight();
            request.getSession().setAttribute(ReqAttr.LOGIN_USER.value, user);
            switch (user.getType()) {
                case STUDENT -> response.sendRedirect(SC.JSPResource.WELCOME_SESSION.name);
                case LECTURE_TEACHER, SUPERVISOR_TEACHER -> response.sendRedirect("get_appros");
                case ADMIN -> throw new NotImplementedException("管理员界面实现中...");
            }
            return;
        }

        // 登录失败, 返回主页
        request.getRequestDispatcher(SC.JSPResource.INDEX.name).forward(request, response);

//            switch (vc_user.getLeft()) {
//
//                case TOO_LONG_PASSWORD ->
//                        request.setAttribute(SC.ReqAttr.ERROR_MESSAGE.name, VC.Service.TOO_LONG_PASSWORD.message);
//                case PASSWORD_ERROR ->
//                        request.setAttribute(SC.ReqAttr.ERROR_MESSAGE.name, VC.Service.PASSWORD_ERROR.message);
//                case NO_SUCH_USER ->
//                        request.setAttribute(SC.ReqAttr.ERROR_MESSAGE.name, VC.Service.NO_SUCH_USER.message);
//                case OK -> {
//
//                    // 安全地取出用户对象
//                    var user = vc_user.getRight();
//
//                    request.getSession().setAttribute(SC.ReqAttr.LOGIN_USER_ID.name, user.getId());
//                    request.getSession().setAttribute(SC.ReqAttr.USER_USERNAME.name, user.getUsername());
//                    request.getSession().setAttribute(SC.ReqParam.USER_TYPE.name, user.getType().name());
//                    request.getSession().setAttribute(SC.ReqAttr.LOGIN_USER.name, user);
//                    request.setAttribute(SC.ReqAttr.LOGIN_USER.name, user);
//
//                    // 根据不同的身份发送到不同的页面
//                    switch (user.getType()) {
//                        case STUDENT -> response.sendRedirect(SC.JSPResource.WELCOME_SESSION.name);
//                        case LECTURE_TEACHER, SUPERVISOR_TEACHER -> response.sendRedirect("get_appros");
//                        case ADMIN -> System.out.println("尚未实现");
//                    }
//                    return;
//                }
//            }

//        request.getRequestDispatcher(SC.JSPResource.INDEX.name).forward(request, response);
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
