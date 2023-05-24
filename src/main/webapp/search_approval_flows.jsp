<%@ page import="com.github.akagawatsurunaki.novappro.util.ZhFieldUtil" %>
<%@ page import="com.github.akagawatsurunaki.novappro.model.database.approval.ApprovalFlow" %>
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
<body>

<h1>查询审批流</h1>

<form action="${pageContext.request.contextPath}/teacherSearch" method="post">
    <%
        String currentPage = (String) request.getAttribute("page");
        String search = (String) request.getAttribute("search");
        if (search == null) {
            search = "";
        }
    %>
    当前页码
    <label>
        <input name="page" type="number" value="<%=currentPage%>">
    </label>
    输入搜索内容
    <label>
        <input name="search" type="text" value="<%=search%>">
    </label>
    输入页码和搜索内容后可以单击确认按钮
    <label>
        <input type="submit" value="确认">
    </label>
</form>


<%
    Pair<ServiceMessage, List<ApprovalFlow>> getPageServiceResult = (Pair<ServiceMessage, List<ApprovalFlow>>) request.getAttribute("getPageServiceResult");

    if (getPageServiceResult == null) {
        return;
    }

    if (getPageServiceResult.getLeft().getMessageLevel().equals(ServiceMessage.Level.SUCCESS)) {

%>
<%=getPageServiceResult.getLeft().getMessage()%>
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
        List<ApprovalFlow> approvalFlows = getPageServiceResult.getRight();
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
        }
    %>
</table>
</body>
</html>
