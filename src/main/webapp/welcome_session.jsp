<%@ page import="java.util.Date" %>
<%@ page import="com.github.akagawatsurunaki.novappro.model.database.User" %>
<%@ page import="com.github.akagawatsurunaki.novappro.constant.SC" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>欢迎回来</title>
    <h1>欢迎回来</h1>
    <p>
        <%
            User loginUser = (User) session.getAttribute(SC.ReqAttr.LOGIN_USER.name);
        %>
        <%= loginUser.getType().chinese %>
        <%= loginUser.getUsername() %>
    </p>
    成功登录于
    <%=
    (new Date()).toString()
    %>

    <input onclick="location.href=('${pageContext.request.contextPath}/get_courses')" value="我要申请课程捏">
</head>
<body>

</body>
</html>
