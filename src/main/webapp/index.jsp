<%@ page import="com.github.akagawatsurunaki.novappro.model.User" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>

登录

<form action="http://localhost:8080/Novappro_war_exploded/login">

    <label>
        学号/工号
        <input type="text" maxlength="10" name="userId">
    </label>
    <label>
        密码
        <input type="password" maxlength="18" name="rawPassword">
    </label>

    <input type="submit" name="submit" value="登录">
</form>

</body>
</html>