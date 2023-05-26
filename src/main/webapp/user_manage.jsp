<%@ page import="com.github.akagawatsurunaki.novappro.model.frontend.ServiceMessage" %>
<%@ page import="com.github.akagawatsurunaki.novappro.model.database.User" %>
<%@ page import="java.util.List" %>
<%@ page import="org.apache.commons.lang3.tuple.Pair" %>
<%@ page import="com.github.akagawatsurunaki.novappro.constant.SC" %>
<%@ page import="com.github.akagawatsurunaki.novappro.util.ZhFieldUtil" %>
<%@ page import="com.github.akagawatsurunaki.novappro.servlet.manage.UserManageServlet" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户管理</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/table_common.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/err_msg.css">
</head>
<body>
<%
    Pair<ServiceMessage, List<User>> allUsers = (Pair<ServiceMessage, List<User>>) request.getAttribute(UserManageServlet.ReqAttr.ALL_USERS.value);
%>
<h1 class="page-title" style="text-align: center">用户管理系统</h1>
<h2 style="text-align: center">
    <%=allUsers.getLeft().getMessage()%>
</h2>
<table border="1">
    <tr>
        <th><%=ZhFieldUtil.getZhValue(User.class, User.Fields.id)%>
        </th>
        <th><%=ZhFieldUtil.getZhValue(User.class, User.Fields.username)%>
        </th>
        <th><%=ZhFieldUtil.getZhValue(User.class, User.Fields.type)%>
        </th>
        <th>操作
        </th>
    </tr>
    <%
        if (allUsers.getLeft().getMessageLevel().equals(ServiceMessage.Level.SUCCESS)) {
            for (User user : allUsers.getRight()) {
    %>
    <tr id="user-<%=user.getId()%>">
        <input id="input-user-<%=user.getId()%>" style="display: none" name="<%=SC.ReqParam.UPDATED_USERS.name%>"
               value="<%=user.getId()%>">
        <td><%=user.getId()%>
        </td>
        <td><%=user.getUsername()%>
        </td>
        <td><%=user.getType().chinese%>
        </td>
        <td>
            <form action="${pageContext.request.contextPath}/delUser" method="post">
                <input type="hidden" name="delUserId" value="<%=user.getId()%>">
                <input type="submit" value="删除">
            </form>
        </td>
    </tr>
    <%
            }
        }
    %>
</table>

</body>
</html>
