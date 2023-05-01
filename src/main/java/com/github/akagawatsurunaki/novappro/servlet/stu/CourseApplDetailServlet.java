package com.github.akagawatsurunaki.novappro.servlet.stu;

import com.github.akagawatsurunaki.novappro.constant.SC;
import com.github.akagawatsurunaki.novappro.service.stu.CourseApplDetailService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

/**
 * 用来查看一个申请实体内的详细内容, 这里的申请是课程类型的申请.
 */
@WebServlet(name = "CourseApplDetailServlet", value = SC.WebServletValue.COURSE_APPL_DETAIL)
public class CourseApplDetailServlet extends HttpServlet {

    private final static CourseApplDetailService instance = new CourseApplDetailService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        var flowCode = request.getParameter(SC.ReqParam.SELECTED_COURSE_APPL_FLOW_NO.name);
        // 获取该流水号下的申请的课程 (可能有多个)
        var courses = instance.getAppliedCourses(flowCode);
        request.setAttribute(SC.ReqAttr.SELECTED_COURSE_APPL_FLOW_NO.name, flowCode);
        request.setAttribute(SC.ReqAttr.APPLIED_COURSES.name, courses);
        request.getRequestDispatcher(SC.JSPResource.COURSE_APPL_DETAIL.name).forward(request, response);
    }
}
