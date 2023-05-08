package com.github.akagawatsurunaki.novappro.servlet.appro;

import com.github.akagawatsurunaki.novappro.constant.SC;
import com.github.akagawatsurunaki.novappro.constant.VC;
import com.github.akagawatsurunaki.novappro.model.database.User;
import com.github.akagawatsurunaki.novappro.service.appro.ApprovalService;
import lombok.AllArgsConstructor;
import lombok.val;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ApplItemDetailServlet", value = "/get_appl_item_detail")
public class ApplItemDetailServlet extends HttpServlet {

    private final static ApprovalService APPROVAL_SERVICE = ApprovalService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        // 获取参数
        val flowNo = request.getParameter(SC.ReqParam.SELECTED_APPL_ITEM_FLOW_NO.name);
        val approver = (User) request.getSession().getAttribute(SC.ReqAttr.LOGIN_USER.name);
        // 获取课程申请明细项目
        val getCourseAppItemDetailServiceResult = APPROVAL_SERVICE.getCourseAppItemDetail(flowNo, approver.getId());
        // 为Request赋予参数
        request.setAttribute(ReqAttr.GET_COURSE_APP_ITEM_DETAIL_SERVICE_RESULT.value, getCourseAppItemDetailServiceResult);
        // 转发页面
        request.getRequestDispatcher(SC.JSPResource.GET_CRS_APPL_ITEM.name).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        doGet(request, response);
    }

    @AllArgsConstructor
    public enum ReqAttr {
        GET_COURSE_APP_ITEM_DETAIL_SERVICE_RESULT("get_course_app_item_detail_service_result");

        public final String value;
    }

}
