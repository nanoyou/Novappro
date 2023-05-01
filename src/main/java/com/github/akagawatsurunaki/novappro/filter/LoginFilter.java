package com.github.akagawatsurunaki.novappro.filter;

import com.github.akagawatsurunaki.novappro.constant.SC;
import com.github.akagawatsurunaki.novappro.constant.VerifyCode;
import com.github.akagawatsurunaki.novappro.enumeration.UserType;
import com.github.akagawatsurunaki.novappro.model.database.User;
import com.github.akagawatsurunaki.novappro.service.base.LoginService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "LoginFilter",
        urlPatterns = {
        "/login",
                SC.WebServletValue.GET_APPROS,
                SC.WebServletValue.GET_APPL_ITEM_DETAIL,
                SC.WebServletValue.MODIFY_COURSE_APPL,
                SC.WebServletValue.COURSE_APPL_DETAIL,
                SC.WebServletValue.APPL_ITEM,
                SC.WebServletValue.APPLY_COURSES,
                SC.WebServletValue.SUBMIT_APPRO_RET,
                "/welcome.jsp"
        })
public class LoginFilter extends HttpFilter {
    private String loginSessionKey = null;

    @Override
    public void init() throws ServletException {

    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException,
            ServletException {

        var uid = req.getSession().getAttribute(SC.ReqAttr.LOGIN_USER_ID.name);

        System.out.println("req.getRequestURL() = " + req.getRequestURL());


        if ("http://localhost:8080/Novappro_war_exploded/login".equals(req.getRequestURL().toString())) {
            loginBySession(req, res);
            return;
        }

        // 是否登录
        if (uid == null) {
            res.sendRedirect(SC.JSPResource.INDEX.name);
            return;
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
}
