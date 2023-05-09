package com.github.akagawatsurunaki.novappro.servlet.stu;

import com.github.akagawatsurunaki.novappro.constant.SC;
import com.github.akagawatsurunaki.novappro.service.appro.ApprovalService;
import com.github.akagawatsurunaki.novappro.service.stu.CourseApplDetailService;
import lombok.AllArgsConstructor;
import lombok.val;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用来查看一个申请实体内的详细内容, 这里的申请是课程类型的申请.
 */
@WebServlet(name = "CourseApplDetailServlet", value = "/course_appl_detail")
public class CourseApplDetailServlet extends HttpServlet {

    private final static CourseApplDetailService COURSE_APPL_DETAIL_SERVICE = new CourseApplDetailService();

    private final static ApprovalService APPROVAL_SERVICE = ApprovalService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        // 获取流水号
        val flowNo = request.getParameter(SC.ReqParam.SELECTED_COURSE_APPL_FLOW_NO.name);
        // 获取该流水号下的申请的课程 (可能有多个)
        val getAppliedCoursesServiceResult = COURSE_APPL_DETAIL_SERVICE.getAppliedCourses(flowNo);
        val getApprovalFlowDetailsServiceResult = APPROVAL_SERVICE.getApprovalFlowDetails(flowNo);

        request.setAttribute(ReqAttr.SELECTED_COURSE_APPL_FLOW_NO.value, flowNo);
        request.setAttribute(ReqAttr.GET_APPLIED_COURSES_SERVICE_RESULT.value, getAppliedCoursesServiceResult);
        request.setAttribute(ReqAttr.GET_APPROVAL_FLOW_DETAILS_SERVICE_RESULT.value, getApprovalFlowDetailsServiceResult);

        request.getRequestDispatcher(SC.JSPResource.COURSE_APPL_DETAIL.name).forward(request, response);
    }

    @AllArgsConstructor
    public enum ReqAttr {
        GET_APPLIED_COURSES_SERVICE_RESULT("get_applied_courses_service_result"),
        SELECTED_COURSE_APPL_FLOW_NO("selected_course_appl_flow_no"),
        GET_APPROVAL_FLOW_DETAILS_SERVICE_RESULT("get_approval_flow_details_service_result")
        ;

        public final String value;
    }
}
