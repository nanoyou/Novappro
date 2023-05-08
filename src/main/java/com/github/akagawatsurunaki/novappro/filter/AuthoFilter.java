package com.github.akagawatsurunaki.novappro.filter;

import cn.hutool.core.util.StrUtil;
import com.github.akagawatsurunaki.novappro.constant.SC;
import com.github.akagawatsurunaki.novappro.model.database.User;
import lombok.val;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "AuthoFilter",
        urlPatterns = {
                "/get_appros",
        }
)
public class AuthoFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException,
            ServletException {
        // 获取登录用户
        val loginUser = (User) req.getSession().getAttribute(SC.ReqAttr.LOGIN_USER.name);

        val servletValue = StrUtil.removePrefix(req.getRequestURI(), req.getContextPath());

        switch (servletValue) {
            case "/get_appros" -> {
                if (loginUser == null) {
                    res.sendRedirect("index.jsp");
                }
                chain.doFilter(req, res);
            }

            default -> {
                res.sendRedirect("index.jsp");
            }
        }

    }
}
