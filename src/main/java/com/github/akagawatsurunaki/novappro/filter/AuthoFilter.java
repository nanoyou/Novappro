package com.github.akagawatsurunaki.novappro.filter;

import com.github.akagawatsurunaki.novappro.constant.SC;
import com.github.akagawatsurunaki.novappro.enumeration.UserType;
import com.github.akagawatsurunaki.novappro.model.database.User;
import lombok.val;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "AuthoFilter",
urlPatterns = {

}
)
public class AuthoFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException,
            ServletException {
        // 获取登录用户
        val loginUser = (User) req.getSession().getAttribute(SC.ReqAttr.LOGIN_USER.name);
        switch (loginUser.getType()) {
            case ADMIN -> {

                chain.doFilter(req, res);
            }
            case LECTURE_TEACHER, SUPERVISOR_TEACHER ->
            {

            }
            case STUDENT -> {

            }
        }

    }
}
