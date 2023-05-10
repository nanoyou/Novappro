<%@ page import="com.github.akagawatsurunaki.novappro.model.frontend.ServiceMessage" %>
<%@ page import="com.github.akagawatsurunaki.novappro.model.frontend.ApprovalAuthorityItem" %>
<%@ page import="java.util.List" %>
<%@ page import="org.apache.commons.lang3.tuple.Pair" %>
<%@ page import="com.github.akagawatsurunaki.novappro.constant.SC" %>
<%@ page import="com.github.akagawatsurunaki.novappro.util.ZhFieldUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>管理员系统 - 审批权限管理系统</title>
</head>
<%
    Pair<ServiceMessage, List<ApprovalAuthorityItem>> allApprovalAuthorityItems = (Pair<ServiceMessage, List<ApprovalAuthorityItem>>) request.getAttribute(SC.ReqAttr.ALL_APPROVAL_AUTHORITY_ITEMS.name);
%>
<script>
    function removeItem(id) {
        let elem = document.getElementById("aai-" + id);
        elem.parentNode.removeChild(elem);
        elem = document.getElementById("input-aai-" + id);
        elem.parentNode.removeChild(elem);
    }
</script>
<body>
<h1>审批权限管理系统</h1>
<form action="${pageContext.request.contextPath}/update_appro_autho_items" method="post">
    <%=allApprovalAuthorityItems.getLeft().getMessage()%>
    <table border="1">
        <tr>
            <th><%=ZhFieldUtil.getZhValue(ApprovalAuthorityItem.class, ApprovalAuthorityItem.Fields.approverId)%>
            </th>
            <th><%=ZhFieldUtil.getZhValue(ApprovalAuthorityItem.class, ApprovalAuthorityItem.Fields.approverName)%>
            </th>
            <th><%=ZhFieldUtil.getZhValue(ApprovalAuthorityItem.class, ApprovalAuthorityItem.Fields.userType)%>
            </th>
            <th><%=ZhFieldUtil.getZhValue(ApprovalAuthorityItem.class, ApprovalAuthorityItem.Fields.approWeight)%>
            </th>
            <th><%=ZhFieldUtil.getZhValue(ApprovalAuthorityItem.class, ApprovalAuthorityItem.Fields.courseCode)%>
            </th>
            <th><%=ZhFieldUtil.getZhValue(ApprovalAuthorityItem.class, ApprovalAuthorityItem.Fields.courseName)%>
            </th>
            <th>操作
            </th>
        </tr>
        <%
            if (allApprovalAuthorityItems.getLeft().getMessageLevel().equals(ServiceMessage.Level.SUCCESS)) {
                for (ApprovalAuthorityItem item : allApprovalAuthorityItems.getRight()) {
        %>
        <tr id="aai-<%=item.getApproverId()%>-<%=item.getCourseCode()%>">
            <input id="input-aai-<%=item.getApproverId()%>-<%=item.getCourseCode()%>" style="display: none"
                   name="<%=SC.ReqParam.UPDATED_APPRO_AUTHO.name%>"
                   value="<%=item.getApproverId()%>-<%=item.getCourseCode()%>">
            <td><%=item.getApproverId()%>
            </td>
            <td><%=item.getApproverName()%>
            </td>
            <td><%=item.getUserType().chinese%>
            </td>
            <td><%=item.getApproWeight()%>
            </td>
            <td><%=item.getCourseCode()%>
            </td>
            <td><%=item.getCourseName()%>
            </td>
            <td>
                <button type="button" onclick="removeItem('<%=item.getApproverId()%>-<%=item.getCourseCode()%>')">
                    删除
                </button>
            </td>
        </tr>
        <%
                }
            }
        %>
    </table>
    <tr>
        增加一个新的审批权限关系
        <label>
            <input name="approverId" type="number" placeholder="输入教师工号">
        </label>
        <label>
            <input name="appproWeight" type="number" placeholder="输入审批权重">
        </label>
        <label>
            <input name="courseCode" type="text" placeholder="输入课程代码">
        </label>
    </tr>
    <input type="submit" value="确认">
</form>

</body>
</html>
