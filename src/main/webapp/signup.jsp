<%@ page import="com.github.akagawatsurunaki.novappro.constant.Constant" %>
<%@ page import="org.apache.commons.lang3.tuple.Pair" %>
<%@ page import="com.github.akagawatsurunaki.novappro.model.frontend.ServiceMessage" %>
<%@ page import="com.github.akagawatsurunaki.novappro.model.database.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<html>
<head>
    <title>学生申请课程系统 - 注册用户</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/table_common.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/err_msg.css">
</head>
<body>
<%
    Pair<ServiceMessage, User> signUpServiceResult = (Pair<ServiceMessage, User>)
            request.getAttribute("signUpServiceResult");

%>
<form action="${pageContext.request.contextPath}/signup" method="post">
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
    <label style="text-align: center">
        <input id="signup_btn" type="submit" value="注册"/>
    </label>
</form>

<%

    if (signUpServiceResult == null) {
        return;
    }

    if (signUpServiceResult.getLeft().getMessageLevel().equals(ServiceMessage.Level.SUCCESS)) {
%>
<p>
    <%=    signUpServiceResult.getLeft().getMessage()%>
</p>
<%
} else {
%>

<p class="err-msg">
    <%=    signUpServiceResult.getLeft().getMessage()%>
</p>

<%
    }

%>

</body>
</html>
