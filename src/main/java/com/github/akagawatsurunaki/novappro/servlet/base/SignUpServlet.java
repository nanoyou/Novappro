package com.github.akagawatsurunaki.novappro.servlet.base;

import com.github.akagawatsurunaki.novappro.model.database.User;
import com.github.akagawatsurunaki.novappro.service.base.RegisterService;
import com.github.akagawatsurunaki.novappro.util.EnumUtil;
import org.apache.commons.lang3.tuple.Pair;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "SignUpServlet", value = "/signup")
public class SignUpServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // 防止中文变成乱码, 必须添加在首部
        request.setCharacterEncoding("UTF-8");
        Object username = request.getParameter("username");
        Object rawPassword = request.getParameter("rawPassword");
        Object confirmedRawPassword = request.getParameter("confirmedRawPassword");

        var verifyCodeUserPair = RegisterService.getInstance().trySignUp(username, rawPassword, confirmedRawPassword);

        // TODO: 注册后的提示界面
        // RegisterService.INFO verifyCode = verifyCodeUserPair.getLeft();

//        if (verifyCode != RegisterService.INFO.OK) {
//            Pair<String, String> pair = EnumUtil.parseVerifyCode(verifyCode);
//            String value = pair.getLeft();
//            String description = pair.getRight();
//
//            request.setAttribute("verifyCode_value", value);
//            request.setAttribute("verifyCode_description", description);
//
//        }

        User user = verifyCodeUserPair.getRight();
        request.setAttribute("user", user);
    }
}
