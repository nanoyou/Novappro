<%@ page import="com.github.akagawatsurunaki.novappro.model.approval.ApprovalProcessQueue" %>
<%@ page import="com.github.akagawatsurunaki.novappro.model.approval.ApprovalProcessNode" %>
<%@ page import="com.github.akagawatsurunaki.novappro.model.User" %>
<%@ page import="com.github.akagawatsurunaki.novappro.model.approval.ApplicationEntity" %><%--
  Created by IntelliJ IDEA.
  User: 96514
  Date: 2023/4/12
  Time: 22:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>成功</title>
</head>
<body>
<% ApprovalProcessQueue queue = ((ApprovalProcessQueue) request.getAttribute("queue")); %>

<h2>
    当前的审批流程
</h2>
<%
    int index = 1;
    for (ApprovalProcessNode node : queue.getQueue()) {

        StringBuilder sb = new StringBuilder();
        String splitter = ", ";
        for (User user : node.getApprovers()) {
            sb.append(user.getUsername()).append(splitter);
        }
        String approvers = sb.substring(0, sb.length() - splitter.length());

        ApprovalProcessNode.Type type = node.getProcessType();
        String typeStr = type.getChineseFieldNameAnnotation(type.getClass(), type.name()).value();
        ApprovalProcessNode.Status currentStatus = node.getCurrentStatus();
        String currentStatusStr = currentStatus.getChineseFieldNameAnnotation(currentStatus.getClass(), currentStatus.name()).value();
        ApplicationEntity.ApplicationType applicationType = node.getApplicationEntity().getApplicationType();
        String applicationTypeStr = applicationType.getChineseFieldNameAnnotation(applicationType.getClass(), applicationType.name()).value();
        Integer applicantId = node.getApplicationEntity().getApplicantId();
%>
----------- <%=index++%>
审批人: <%= approvers %>
审批类型: <%= typeStr %>
审批状态: <%= currentStatusStr%>
--
申请类型: <%= applicationTypeStr %>
申请人ID: <%= applicantId %>
<%}%>
</body>
</html>
