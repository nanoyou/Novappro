<%@ page import="com.github.akagawatsurunaki.novappro.constant.SC" %>
<%@ page import="com.github.akagawatsurunaki.novappro.model.frontend.ApplItem" %>
<%@ page import="java.util.List" %>
<%@ page import="com.github.akagawatsurunaki.novappro.util.ZhFieldUtil" %>
<%@ page import="com.github.akagawatsurunaki.novappro.model.database.User" %>
<%@ page import="com.github.akagawatsurunaki.novappro.enumeration.UserType" %><%--
  Created by IntelliJ IDEA.
  User: 96514
  Date: 2023/4/19
  Time: 17:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>我的审批</title>
</head>
<script>
    function detail(flowNo) {
        location.href =
            '${pageContext.request.contextPath}<%=SC.WebServletValue.GET_APPL_ITEM_DETAIL%>' + '?<%=SC.ReqParam.SELECTED_APPL_ITEM_FLOW_NO.name%>=' + flowNo;
    }

    function init(){
        location.href ="${pageContext.request.contextPath}/get_appros";
    }

</script>
<body onload="init()">
<%
    List<ApplItem> applItemList =
            (List<ApplItem>) request.getAttribute(SC.ReqAttr.APPL_ITEMS_WITH_GIVEN_APPROVER.name);

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
            for (ApplItem applItem : applItemList) {
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
    %>
</table>

</body>
</html>
