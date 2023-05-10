package com.github.akagawatsurunaki.novappro.servlet.appro;

import com.github.akagawatsurunaki.novappro.constant.JSPResource;
import com.github.akagawatsurunaki.novappro.constant.SC;
import com.github.akagawatsurunaki.novappro.service.appro.ApprovalService;
import lombok.AllArgsConstructor;
import lombok.val;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ApplItemServlet", value = "/ApplItemServlet")
public class ApplItemServlet extends HttpServlet {

    private static final ApprovalService APPROVAL_SERVICE = ApprovalService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        // 获取单号
        val flowNo = request.getParameter(SC.ReqParam.SELECTED_COURSE_APPL_FLOW_NO.name);
        // 获取服务结果
        val getApplItemServiceResult = APPROVAL_SERVICE.getApplItem(flowNo);
        // 增加属性
        request.setAttribute(ReqAttr.GET_APPL_ITEM_SERVICE_RESULT.value, getApplItemServiceResult);
        // 加载界面
        request.getRequestDispatcher(JSPResource.GET_CRS_APPL_ITEM.value).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        doGet(request, response);
    }

    @AllArgsConstructor
    public enum ReqAttr {
        GET_APPL_ITEM_SERVICE_RESULT("get_appl_item_service_result");

        public final String value;
    }


}
