<%@ page import="com.github.akagawatsurunaki.novappro.annotation.ZhField" %>
<%@ page import="com.github.akagawatsurunaki.novappro.util.ZhFieldUtil" %>
<%@ page import="com.github.akagawatsurunaki.novappro.model.database.approval.ApprovalFlow" %>
<%@ page import="com.github.akagawatsurunaki.novappro.servlet.manage.ApprovalFlowManageServlet" %>
<%@ page import="com.github.akagawatsurunaki.novappro.model.frontend.ServiceMessage" %>
<%@ page import="java.util.List" %>
<%@ page import="org.apache.commons.lang3.tuple.Pair" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/table_common.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/err_msg.css">
</head>
<script>
    function winPrint() {
        window.print();
    }

    function download() {
        window.open("${pageContext.request.contextPath}/download/approval_flow_sheet");
    }
</script>
<body>

<%
    Pair<ServiceMessage, List<ApprovalFlow>> getAllApprovalFlowsServiceResult = (Pair<ServiceMessage, List<ApprovalFlow>>)
            request.getAttribute(ApprovalFlowManageServlet.ReqAttr.GET_ALL_APPROVAL_FLOWS_SERVICE_RESULT.value);

    if (getAllApprovalFlowsServiceResult == null) {
        return;
    }
%>
<h1>
    所有审批流一览表
</h1>
<button onclick="winPrint()">单击此处打印网页</button>
<button onclick="download()">单击此处导出表格</button>
<form>

    <%
        if (getAllApprovalFlowsServiceResult.getLeft().getMessageLevel() == ServiceMessage.Level.SUCCESS) {
    %>
    <table>
        <tr>
            <th>
                <%=ZhFieldUtil.getZhValue(ApprovalFlow.class, ApprovalFlow.Fields.flowNo)%>
            </th>
            <th>
                <%=ZhFieldUtil.getZhValue(ApprovalFlow.class, ApprovalFlow.Fields.busType)%>
            </th>
            <th>
                <%=ZhFieldUtil.getZhValue(ApprovalFlow.class, ApprovalFlow.Fields.title)%>
            </th>
            <th>
                <%=ZhFieldUtil.getZhValue(ApprovalFlow.class, ApprovalFlow.Fields.addTime)%>
            </th>
            <th>
                <%=ZhFieldUtil.getZhValue(ApprovalFlow.class, ApprovalFlow.Fields.addUserId)%>
            </th>
            <th>
                <%=ZhFieldUtil.getZhValue(ApprovalFlow.class, ApprovalFlow.Fields.approStatus)%>
            </th>
            <th>
                <%=ZhFieldUtil.getZhValue(ApprovalFlow.class, ApprovalFlow.Fields.remark)%>
            </th>
        </tr>

        <%
            List<ApprovalFlow> approvalFlows = getAllApprovalFlowsServiceResult.getRight();

            for (ApprovalFlow approvalFlow : approvalFlows) {
        %>

        <tr>
            <td>
                <%=approvalFlow.getFlowNo()%>
            </td>
            <td>
                <%=approvalFlow.getBusType()%>
            </td>
            <td>
                <%=approvalFlow.getTitle()%>
            </td>
            <td>
                <%=approvalFlow.getAddTime()%>
            </td>
            <td>
                <%=approvalFlow.getAddUserId()%>
            </td>
            <td>
                <%=approvalFlow.getApproStatus().chinese%>
            </td>
            <td>
                <%=approvalFlow.getRemark()%>
            </td>
        </tr>
        <%
            }
        %>
    </table>
    <% }
    %>
</form>
</body>
</html>
