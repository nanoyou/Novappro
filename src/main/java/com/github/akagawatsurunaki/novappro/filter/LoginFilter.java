package com.github.akagawatsurunaki.novappro.filter;

import com.github.akagawatsurunaki.novappro.constant.JSPResource;
import com.github.akagawatsurunaki.novappro.constant.SC;
import com.github.akagawatsurunaki.novappro.constant.VC;
import com.github.akagawatsurunaki.novappro.service.base.LoginService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebFilter(filterName = "LoginFilter",
        urlPatterns = {
                "/welcome.jsp"
        })
public class LoginFilter extends HttpFilter {

    private static final LoginService LOGIN_SERVICE = LoginService.getInstance();

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException,
            ServletException {

        var uid = req.getSession().getAttribute(SC.ReqAttr.LOGIN_USER_ID.name);

        // 是否登录
        if (uid == null) {
            res.sendRedirect(JSPResource.INDEX.value);
        }

    }

}
