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
</head>
<script>
    function detail(flowNo) {
        location.href =
            '${pageContext.request.contextPath}<%=SC.WebServletValue.GET_APPL_ITEM_DETAIL%>' + '?<%=SC.ReqParam.SELECTED_APPL_ITEM_FLOW_NO.name%>=' + flowNo;
    }
</script>
<style>
    body {
        font-size: 14px;
        margin: 0;
        padding: 0;
    }

    h1 {
        font-size: 32px;
        font-weight: 700;
        line-height: 1.2;
        margin-bottom: 20px;

    }

    p {
        margin-bottom: 10px;
    }

    table {
        border-collapse: collapse;
        width: 100%;
    }

    th, td {
        border: 1px solid #ccc;
        padding: 8px;
        text-align: center;
    }

    tr:nth-child(even) {
        background-color: #f2f2f2;
    }

    tr:hover {
        background-color: #ddd;
    }

    input[type=radio] {
        margin-right: 5px;
    }

    label, input[type=file] {
        display: block;
        margin-bottom: 10px;
    }

    input[type=text], input[type=file], input[type=button], input[type=submit] {
        border: none;
        border-radius: 5px;
        font-size: 16px;
        padding: 10px;
    }

    input[type=button], input[type=submit] {
        background-color: #007bff;
        color: #fff;
        cursor: pointer;
        transition: background-color .3s ease;
    }

    input[type=button]:hover, input[type=submit]:hover {
        background-color: #0056b3;
    }

</style>
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

</body>
</html>
