<%@ page import="com.github.akagawatsurunaki.novappro.constant.SC" %>
<%@ page import="com.github.akagawatsurunaki.novappro.model.frontend.ApplItem" %>
<%@ page import="java.util.List" %>
<%@ page import="com.github.akagawatsurunaki.novappro.util.ZhFieldUtil" %>
<%@ page import="com.github.akagawatsurunaki.novappro.model.database.User" %>
<%@ page import="com.github.akagawatsurunaki.novappro.model.frontend.ServiceMessage" %>
<%@ page import="org.apache.commons.lang3.tuple.Pair" %>
<%@ page import="com.github.akagawatsurunaki.novappro.servlet.appro.ApproServlet" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>教师审批系统 - 我的审批</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/table_common.css">
</head>
<script>
    function detail(flowNo) {
        location.href =
            '${pageContext.request.contextPath}/get_appl_item_detail' + '?<%=SC.ReqParam.SELECTED_APPL_ITEM_FLOW_NO.name%>=' + flowNo;
    }
</script>
<body>
<%
    Pair<ServiceMessage, List<ApplItem>> applItemList =
            (Pair<ServiceMessage, List<ApplItem>>) request.getAttribute(ApproServlet.ReqAttr.GET_APPL_ITEMS_SERVICE_MESSAGE.value);

    User loginUser = (User) request.getSession().getAttribute(SC.ReqAttr.LOGIN_USER.name);
%>
<h1>我的审批</h1>
尊敬的<%=loginUser.getUsername()%> <%=loginUser.getType().chinese%>，您好！
<table border="1">
    <tr>
        <th>
            <%=
            ZhFieldUtil.getZhValue(ApplItem.class, ApplItem.Fields.flowNo)
            %>
        </th>
        <th>
            <%=
            ZhFieldUtil.getZhValue(ApplItem.class, ApplItem.Fields.title)
            %>
        </th>
        <th>
            <%=
            ZhFieldUtil.getZhValue(ApplItem.class, ApplItem.Fields.applicantName)
            %>
        </th>
        <th>
            <%=
            ZhFieldUtil.getZhValue(ApplItem.class, ApplItem.Fields.approverName)
            %>
        </th>
        <th>
            <%=
            ZhFieldUtil.getZhValue(ApplItem.class, ApplItem.Fields.addTime)
            %>
        </th>
        <th>
            <%=
            ZhFieldUtil.getZhValue(ApplItem.class, ApplItem.Fields.approvalStatus)
            %>
        </th>
        <th>
            操作
        </th>

    </tr>

    <%
        if (applItemList != null) {

    %>
    <p><%=applItemList.getLeft().getMessage()%>
    </p>
    <%
        if (applItemList.getLeft().getMessageLevel().equals(ServiceMessage.Level.SUCCESS)) {
            for (ApplItem applItem : applItemList.getRight()) {
    %>
    <tr>
        <td>
            <%=applItem.getFlowNo()%>
        </td>
        <td>
            <%=applItem.getTitle()%>
        </td>
        <td>
            <%=applItem.getApplicantName()%>
        </td>
        <td>
            <%=applItem.getApproverName()%>
        </td>
        <td>
            <%=applItem.getAddTime()%>
        </td>
        <td>
            <%=applItem.getApprovalStatus().chinese%>
        </td>
        <td>
            <input type="button" value="查看详情" onclick="detail('<%=applItem.getFlowNo()%>')">
        </td>
    </tr>
    <%
                }
            }
        }
    %>
</table>
<form action="${pageContext.request.contextPath}/teacherSearch">
    <input type="submit" value="高级搜索功能">
</form>
</body>
</html>
