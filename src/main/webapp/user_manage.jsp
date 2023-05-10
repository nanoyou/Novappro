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
</head>
<script>
    function init() {
        location.href = "${pageContext.request.contextPath}/get_users"
    }

    function removeUser(id) {
        let elem = document.getElementById("user-" + id);
        elem.parentNode.removeChild(elem);
        elem = document.getElementById("input-user-" + id);
        elem.parentNode.removeChild(elem);
    }
</script>
<body>
<%
    Pair<ServiceMessage, List<User>> allUsers = (Pair<ServiceMessage, List<User>>) request.getAttribute(UserManageServlet.ReqAttr.ALL_USERS.value);
%>
<h1>用户管理系统</h1>
<form action="${pageContext.request.contextPath}/update_users" method="post">
    <%=allUsers.getLeft().getMessage()%>
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
            <input id="input-user-<%=user.getId()%>" style="display: none" name="<%=SC.ReqParam.UPDATED_USERS.name%>" value="<%=user.getId()%>">
            <td><%=user.getId()%>
            </td>
            <td><%=user.getUsername()%>
            </td>
            <td><%=user.getType().chinese%>
            </td>
            <td>
                <button type="button" onclick="removeUser(<%=user.getId()%>)">
                    删除
                </button>
            </td>
        </tr>
        <%
                }
            }
        %>
        <input type="submit" value="保存">
    </table>

</form>
</body>
</html>
