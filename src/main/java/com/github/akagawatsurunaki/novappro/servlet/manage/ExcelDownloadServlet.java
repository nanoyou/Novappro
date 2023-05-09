package com.github.akagawatsurunaki.novappro.servlet.manage;

import com.alibaba.excel.EasyExcel;
import com.github.akagawatsurunaki.novappro.converter.ApprovalStatusConverter;
import com.github.akagawatsurunaki.novappro.converter.BusTypeConverter;
import com.github.akagawatsurunaki.novappro.model.database.approval.ApprovalFlow;
import com.github.akagawatsurunaki.novappro.model.frontend.ServiceMessage;
import com.github.akagawatsurunaki.novappro.service.appro.ApprovalService;
import lombok.val;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet(name = "ExcelDownloadServlet", value = "/download/approval_flow_sheet")
public class ExcelDownloadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {

        val allApprovalFlows = ApprovalService.getInstance().getAllApprovalFlows();

        if (allApprovalFlows.getLeft().getMessageLevel().equals(ServiceMessage.Level.SUCCESS)) {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("审批流", StandardCharsets.UTF_8).replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            System.out.println("allApprovalFlows.getRight() = " + allApprovalFlows.getRight());

            EasyExcel.write(response.getOutputStream(), ApprovalFlow.class)
                    .registerConverter(new ApprovalStatusConverter())
                    .registerConverter(new BusTypeConverter())
                    .sheet("所有审批流一览表")
                    .doWrite(allApprovalFlows.getRight());

        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        doGet(request, response);
    }
}
