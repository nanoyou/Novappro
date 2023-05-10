package com.github.akagawatsurunaki.novappro.servlet.stu;

import com.github.akagawatsurunaki.novappro.constant.JSPResource;
import com.github.akagawatsurunaki.novappro.constant.SC;
import com.github.akagawatsurunaki.novappro.service.stu.CourseApplDetailService;
import lombok.val;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ModifyCourseApplServlet", value = "/modify_course_appl")
public class ModifyCourseApplServlet extends HttpServlet {

    private static final CourseApplDetailService COURSE_APPL_DETAIL_SERVICE = CourseApplDetailService.getInstance();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        request.setCharacterEncoding("UTF-8");
        val flowNo = request.getParameter(SC.ReqParam.SELECTED_COURSE_APPL_FLOW_NO.name);
        val updatedCourseCodes = request.getParameterValues(SC.ReqParam.UPDATED_COURSES.name);
        val updateAppliedCoursesServiceResult = COURSE_APPL_DETAIL_SERVICE.updateAppliedCourses(flowNo, updatedCourseCodes);
        request.setAttribute(SC.ReqParam.SELECTED_COURSE_APPL_FLOW_NO.name, flowNo);
        request.setAttribute(SC.ReqAttr.UPDATE_APPLIED_COURSES_SERVICE_RESULT.name, updateAppliedCoursesServiceResult);
        // 刷新页面
        request.getRequestDispatcher(JSPResource.COURSE_APPL_DETAIL.value).forward(request, response);
    }
}
