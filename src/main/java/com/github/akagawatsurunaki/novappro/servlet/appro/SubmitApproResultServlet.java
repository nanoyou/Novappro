package com.github.akagawatsurunaki.novappro.servlet.appro;

import com.github.akagawatsurunaki.novappro.constant.SC;
import com.github.akagawatsurunaki.novappro.model.frontend.ServiceMessage;
import com.github.akagawatsurunaki.novappro.service.appro.ApprovalService;
import lombok.AllArgsConstructor;
import lombok.val;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 提交审批(Appro)结果, 持久化保存
 */
@WebServlet(name = "SubmitApproResultServlet", value = "/submit_appro_ret")
public class SubmitApproResultServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        try {
            request.setCharacterEncoding("UTF-8");

            String flowNo = request.getParameter(SC.ReqParam.SELECTED_APPL_ITEM_FLOW_NO.name);
            String applRemark = request.getParameter(SC.ReqParam.APPL_REMARK.name);
            String applItemConfirm = request.getParameter(SC.ReqParam.APPL_ITEM_CONFIRM.name);

            val saveApproResultServiceResult
                    = ApprovalService.getInstance().saveApproResult(flowNo, applRemark,
                    applItemConfirm);

            if (saveApproResultServiceResult.getLeft().getMessageLevel() == ServiceMessage.Level.SUCCESS) {
                response.sendRedirect("get_appros");
                return;
            }

            request.setAttribute(ReqAttr.SAVE_APPRO_RESULT_SERVICE_RESULT.value, saveApproResultServiceResult);
            request.getRequestDispatcher("get_appl_item_detail").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @AllArgsConstructor
    public enum ReqAttr {
        SAVE_APPRO_RESULT_SERVICE_RESULT("save_appro_result_service_result");

        public final String value;
    }
}
