<%@ page import="java.util.Date" %>
<%@ page import="com.github.akagawatsurunaki.novappro.model.database.User" %><%--
  Created by IntelliJ IDEA.
  User: 96514
  Date: 2023/3/26
  Time: 17:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>欢迎回来</title>
    <h1>欢迎回来</h1>
    <p>
        <% User user = (User) request.getAttribute(User.class.getName()); %>
        <%= user.getType().getChineseName() %>
        <%= user.getUsername() %>
    </p>
    成功登录于
    <%=
    (new Date()).toString()
    %>
    <form action="./courses.jsp" method="get">
        <input type="button" value="我要申请课程" onclick="">
    </form>


</head>
<body>

</body>
</html>
