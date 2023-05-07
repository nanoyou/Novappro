<%@ page import="java.util.Date" %>
<%@ page import="com.github.akagawatsurunaki.novappro.model.database.User" %>
<%@ page import="com.github.akagawatsurunaki.novappro.constant.SC" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<style>
    body {
        background-color: #f2f2f2;
        font-size: 16px;
        margin: 0;
        padding: 0;
    }

    h1 {
        color: #333;
        font-size: 32px;
        margin-bottom: 20px;
    }

    p {
        color: #666;
        font-size: 18px;
        padding: 10px;
        margin: 0;
    }

    .container {
        margin: 50px auto;
        max-width: 600px;
        padding: 30px;
        text-align: center;
        background-color: #fff;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
    }

    .btn {
        display: inline-block;
        padding: 10px 20px;
        background-color: #007bff;
        color: #fff;
        font-size: 18px;
        text-decoration: none;
        border-radius: 5px;
        transition: background-color .3s ease;
        margin: 100px;
    }

    .btn:hover {
        background-color: #0056b3;
    }

</style>
<head>
    <title>欢迎回来</title>
</head>
<body class="container">
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
<div>
    <a class="btn" href="${pageContext.request.contextPath}/get_courses">我要申请课程捏</a>
</div>
</body>
</html>
