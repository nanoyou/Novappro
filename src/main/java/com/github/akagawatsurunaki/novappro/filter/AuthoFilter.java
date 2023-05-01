package com.github.akagawatsurunaki.novappro.filter;

import com.github.akagawatsurunaki.novappro.constant.SC;
import com.github.akagawatsurunaki.novappro.enumeration.UserType;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "AuthoFilter",
urlPatterns = {
        SC.WebServletValue.APPL_ITEM,
}
)
public class AuthoFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException,
            ServletException {
        // 权限

        var uType = req.getSession().getAttribute(SC.ReqParam.USER_TYPE.name);

        if (uType!=null) {
            if (((String)uType).equals(UserType.STUDENT.name())) {
                res.sendRedirect("no_permission.html");
            }
        }

    }
}
