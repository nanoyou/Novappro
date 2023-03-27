<%@ page import="java.util.Date" %>
<%@ page import="com.github.akagawatsurunaki.novappro.model.User" %>
<%@ page import="com.alibaba.fastjson2.JSONObject" %>
<%@ page import="com.alibaba.fastjson2.JSONReader" %>
<%@ page import="java.util.Arrays" %><%--
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
        <%! Cookie[] cookies = null; %>
        <%! String userUsername = ""; %>
        <%! String userType = ""; %>
        <%
            cookies = request.getCookies();

            for (Cookie c : cookies) {
                if (c.getName().equals("user_username")) {
                    userUsername = c.getValue();
                } else if (c.getName().equals("user_type")) {
                    userType = c.getValue();
                }
            }

        %>
        <%= userType %>
        <%= userUsername %>
    </p>
    成功登录于
    <%=
    (new Date()).toString()
    %>
</head>
<body>

</body>
</html>
