package com.github.akagawatsurunaki.novappro.servlet.stu;

import com.github.akagawatsurunaki.novappro.constant.SC;
import com.github.akagawatsurunaki.novappro.constant.VerifyCode;
import com.github.akagawatsurunaki.novappro.service.stu.ApplyCourseService;

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

    private static final ApplyCourseService APPLY_COURSE_SERVICE = ApplyCourseService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        request.setCharacterEncoding("UTF-8");

        // 获取登录人ID
        Integer id = (Integer) request.getSession().getAttribute("login_user_id");

        // 获取选取的课程列表
        String[] selectedCourseCodes = request.getParameterValues("selected_course[]");

        if (selectedCourseCodes != null) {
            if (selectedCourseCodes.length > 0) {

                List<String> selectedCourseCodeList = Arrays.stream(selectedCourseCodes).toList();
                // 申请这些课程
                APPLY_COURSE_SERVICE.apply(id, selectedCourseCodeList);
            }
        }

        // 获取这些课程
        var vc_cal_asl = APPLY_COURSE_SERVICE.getCourseApplsByUserId(id);

        if (vc_cal_asl.getLeft()== VerifyCode.Service.OK){
            var courseApplications = vc_cal_asl.getMiddle();
            var approStatusList = vc_cal_asl.getRight();

            // 设置到 Request 中
            request.setAttribute(SC.ReqAttr.COURSE_APPLICATIONS.name, courseApplications);
            request.setAttribute(SC.ReqAttr.APPRO_STATUS_LIST.name, approStatusList);

            // 跳转页面
            request.getRequestDispatcher(SC.JSPResource.GET_APPLIED_COURSES.name).forward(request, response);
        }


    }
}
