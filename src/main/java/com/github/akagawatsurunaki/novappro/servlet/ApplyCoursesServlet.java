package com.github.akagawatsurunaki.novappro.servlet;

import cn.hutool.json.JSONUtil;
import com.github.akagawatsurunaki.novappro.service.ApplyService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "ApplyCoursesServlet", value = "/apply_courses")
public class ApplyCoursesServlet extends HttpServlet {

    private static final ApplyService APPLY_SERVICE = ApplyService.getInstance();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String[] selectedCourseCodes = request.getParameterValues("selected_course[]");
        if (selectedCourseCodes == null) {
            return;
        }
        List<String> selectedCourseCodeList = Arrays.stream(selectedCourseCodes).toList();
        if (selectedCourseCodeList.isEmpty()) {
            return;
        }

        Integer id = (Integer) request.getSession().getAttribute("login_user_id");
        var pair = APPLY_SERVICE.applyCourses(
                id,
                selectedCourseCodeList
        );

        var verifyCode = pair.getLeft();
        System.out.println("verifyCode = " + verifyCode);
        var queue = pair.getRight();
        System.out.println(queue);
        System.out.println(JSONUtil.toJsonStr(queue));

        request.setAttribute("queue", queue);
        request.getRequestDispatcher("approval_success.jsp").forward(request, response);

    }
}
