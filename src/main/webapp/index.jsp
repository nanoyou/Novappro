<%@ page import="com.github.akagawatsurunaki.novappro.constant.Constant" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<form action="http://localhost:8080/Novappro_war_exploded/login">
    <h1 style="text-align: center">登录</h1>
    <label>
        学号/工号
        <input type="text" minlength="<%= Constant.MIN_LEN_USER_ID %>" maxlength="<%= Constant.MAX_LEN_USER_ID %>" name="userId">
    </label>
    <label>
        密码
        <input type="password" minlength="<%= Constant.MIN_LEN_PASSWORD %>" maxlength="<%= Constant.MAX_LEN_PASSWORD %>" name="rawPassword">
    </label>

    <label>
        <input type="submit" name="submit" value="登录">
    </label>

    <label>
        <input type="button" value="注册" onclick='location.href=("/Novappro_war_exploded/signup.jsp")'/>
    </label>
</form>

</body>
</html>