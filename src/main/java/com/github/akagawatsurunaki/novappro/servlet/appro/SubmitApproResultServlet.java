package com.github.akagawatsurunaki.novappro.servlet.appro;

import com.github.akagawatsurunaki.novappro.constant.SC;
import com.github.akagawatsurunaki.novappro.service.appro.ApprovalService;

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

        request.setCharacterEncoding("UTF-8");
        String applRemark = request.getParameter(SC.ReqParam.APPL_REMARK.name);
        String applItemConfirm = request.getParameter(SC.ReqParam.APPL_ITEM_CONFIRM.name);
        String flowNo = request.getParameter(SC.ReqParam.SELECTED_APPL_ITEM_FLOW_NO.name);

        if (applItemConfirm == null) {
            return;
        }

        if (applItemConfirm.isBlank()) {
            return;
        }

        if (applRemark == null) {
            applRemark = "";
        }

        if ("同意审批".equals(applItemConfirm)) {
            ApprovalService.getInstance().saveApproResult(flowNo, applRemark, true);
        } else if ("驳回审批".equals(applItemConfirm)) {
            ApprovalService.getInstance().saveApproResult(flowNo, applRemark, false);
        }

        response.sendRedirect("get_appros");
    }
}
