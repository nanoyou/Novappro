package com.github.akagawatsurunaki.novappro.servlet.stu;

import com.github.akagawatsurunaki.novappro.constant.SC;
import com.github.akagawatsurunaki.novappro.constant.VC;
import com.github.akagawatsurunaki.novappro.service.stu.CourseApplDetailService;
import lombok.val;

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
        var updatedCourseCodes = request.getParameterValues(SC.ReqParam.UPDATED_COURSES.name);
        var updateAppliedCoursesServiceResult = COURSE_APPL_DETAIL_SERVICE.updateAppliedCourses(flowNo, updatedCourseCodes);
//        // 删除要更新的课程参数
//        request.removeAttribute(SC.ReqParam.UPDATED_COURSES.name);
        request.setAttribute(SC.ReqParam.SELECTED_COURSE_APPL_FLOW_NO.name, flowNo);
        request.setAttribute(SC.ReqAttr.UPDATE_APPLIED_COURSES_SERVICE_RESULT.name, updateAppliedCoursesServiceResult);
        // 刷新页面
        request.getRequestDispatcher(SC.JSPResource.COURSE_APPL_DETAIL.name).forward(request, response);
    }
}
