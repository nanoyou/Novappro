<%@ page import="com.github.akagawatsurunaki.novappro.constant.Constant" %><%--
  Created by IntelliJ IDEA.
  User: 96514
  Date: 2023/4/9
  Time: 15:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<html>
<head>
    <title>学生申请课程系统 - 注册用户</title>
</head>
<body>
<form action="./signup" method="post">
    <h1>注册</h1>
    <label>
        姓名
        <input id="username_input" name="username" type="text" minlength="<%= Constant.MIN_LEN_USERNAME %>"
               maxlength="<%= Constant.MAX_LEN_USERNAME %>">
    </label>
    <label>
        密码
        <input id="password_input" name="rawPassword" type="password" minlength="<%= Constant.MIN_LEN_PASSWORD %>"
               maxlength="<%= Constant.MAX_LEN_PASSWORD %>">
    </label>
    <label>
        确认密码
        <input id="confirmed_password_input" name="confirmedRawPassword" type="password"
               minlength="<%= Constant.MIN_LEN_PASSWORD %>" maxlength="<%= Constant.MAX_LEN_PASSWORD %>">
    </label>
    <label>
        <input id="signup_btn" type="submit" value="注册"/>
    </label>
</form>
</body>
</html>
