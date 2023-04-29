package com.github.akagawatsurunaki.novappro.servlet.stu;

import com.github.akagawatsurunaki.novappro.constant.SC;
import com.github.akagawatsurunaki.novappro.constant.VerifyCode;
import com.github.akagawatsurunaki.novappro.service.stu.CourseApplDetailService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@WebServlet(name = "ModifyCourseApplServlet", value = SC.WebServletValue.MODIFY_COURSE_APPL)
public class ModifyCourseApplServlet extends HttpServlet {

    private static final CourseApplDetailService COURSE_APPL_DETAIL_SERVICE = CourseApplDetailService.getInstance();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        request.setCharacterEncoding("UTF-8");
        var flowNo = request.getParameter(SC.ReqParam.SELECTED_COURSE_APPL_FLOW_NO.name);

        if (flowNo == null) {
            request.getRequestDispatcher(SC.JSPResource.COURSE_APPL_DETAIL.name).forward(request, response);

        }

        var s = request.getParameterValues(SC.ReqParam.UPDATED_COURSES.name);

        // 校验是否为空
        var updatedCourseCodes = new java.util.ArrayList<>(Arrays.stream(s).toList());

        if (updatedCourseCodes.isEmpty()) {
            request.getRequestDispatcher(SC.JSPResource.COURSE_APPL_DETAIL.name).forward(request, response);

        }

        // 清空空白字符
        updatedCourseCodes.removeIf(String::isBlank);

        var vc_courses = COURSE_APPL_DETAIL_SERVICE.updateAppliedCourses(flowNo, updatedCourseCodes);

        if (vc_courses.getLeft()!= VerifyCode.Service.OK) {
            request.getRequestDispatcher(SC.JSPResource.COURSE_APPL_DETAIL.name).forward(request, response);

        }

        // 删除要更新的课程参数
        request.removeAttribute(SC.ReqParam.UPDATED_COURSES.name);
        request.setAttribute(SC.ReqParam.SELECTED_COURSE_APPL_FLOW_NO.name, flowNo);
        request.setAttribute(SC.ReqAttr.APPLIED_COURSES.name, vc_courses.getRight());

        // 刷新页面
        request.getRequestDispatcher(SC.JSPResource.COURSE_APPL_DETAIL.name).forward(request, response);
    }
}
