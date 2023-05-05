<%@ page import="com.github.akagawatsurunaki.novappro.model.frontend.ServiceMessage" %>
<%@ page import="com.github.akagawatsurunaki.novappro.model.frontend.ApprovalAuthorityItem" %>
<%@ page import="java.util.List" %>
<%@ page import="com.github.akagawatsurunaki.novappro.model.database.User" %>
<%@ page import="org.apache.commons.lang3.tuple.Pair" %>
<%@ page import="com.github.akagawatsurunaki.novappro.constant.SC" %>
<%@ page import="com.github.akagawatsurunaki.novappro.util.ZhFieldUtil" %>
<%@ page import="static com.github.akagawatsurunaki.novappro.constant.SC.ReqParam.UPDATED_APPRO_AUTHO" %><%--
  Created by IntelliJ IDEA.
  User: 96514
  Date: 2023/5/5
  Time: 16:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>审批权限管理系统</title>
</head>
<%
    Pair<ServiceMessage, List<ApprovalAuthorityItem>> allApprovalAuthorityItems = (Pair<ServiceMessage, List<ApprovalAuthorityItem>>) request.getAttribute(SC.ReqAttr.ALL_APPROVAL_AUTHORITY_ITEMS.name);
%>
<script>
    function removeItem(id) {
        let elem = document.getElementById("aai-" + id);
        elem.parentNode.removeChild(elem);
    }
    function addItem(flowNo) {
        const crsTable = document.getElementById("courses_table");
        crsTable.insertRow();
        const input = document.createElement('input');

        crsTable.appendChild(input);
        refresh(flowNo)
    }

</script>
<body>
<h1>审批权限管理系统</h1>
<form action="" method="post" >
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
                   name="<%=UPDATED_APPRO_AUTHO%>" value="<%=item.getApproverId()%>-<%=item.getCourseCode()%>">
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
    <label>
        <input name="approverId" type="number" placeholder="输入教师工号">
    </label>
    <label>
        <input name="appproWeight" type="number" placeholder="输入审批权重">
    </label>
    <label>
        <input name="courseCode" type="text" placeholder="输入课程代码">
    </label>
</form>

</body>
</html>
