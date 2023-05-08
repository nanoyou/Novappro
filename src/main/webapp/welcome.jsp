<%@ page import="java.util.Date" %>
<%@ page import="com.github.akagawatsurunaki.novappro.model.database.User" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>欢迎回来</title>
    <h1>欢迎回来</h1>
    <p>
        <% User user = (User) request.getAttribute(User.class.getName()); %>
        <%= user.getType().chinese %>
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
