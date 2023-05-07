package com.github.akagawatsurunaki.novappro.servlet.stu;

import com.github.akagawatsurunaki.novappro.constant.SC;
import com.github.akagawatsurunaki.novappro.service.stu.CourseApplDetailService;
import lombok.AllArgsConstructor;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

/**
 * 用来查看一个申请实体内的详细内容, 这里的申请是课程类型的申请.
 */
@WebServlet(name = "CourseApplDetailServlet", value = SC.WebServletValue.COURSE_APPL_DETAIL)
public class CourseApplDetailServlet extends HttpServlet {

    private final static CourseApplDetailService COURSE_APPL_DETAIL_SERVICE = new CourseApplDetailService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        // 获取流水号
        var flowCode = request.getParameter(SC.ReqParam.SELECTED_COURSE_APPL_FLOW_NO.name);
        // 获取该流水号下的申请的课程 (可能有多个)
        var getAppliedCoursesServiceResult = COURSE_APPL_DETAIL_SERVICE.getAppliedCourses(flowCode);
        request.setAttribute(ReqAttr.SELECTED_COURSE_APPL_FLOW_NO.value, flowCode);
        request.setAttribute(ReqAttr.GET_APPLIED_COURSES_SERVICE_RESULT.value, getAppliedCoursesServiceResult);
        request.getRequestDispatcher(SC.JSPResource.COURSE_APPL_DETAIL.name).forward(request, response);
    }

    @AllArgsConstructor
    public enum ReqAttr {
        GET_APPLIED_COURSES_SERVICE_RESULT("get_applied_courses_service_result"),
        SELECTED_COURSE_APPL_FLOW_NO("selected_course_appl_flow_no");

        public final String value;
    }
}
