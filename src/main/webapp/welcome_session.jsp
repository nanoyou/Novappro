<%@ page import="java.util.Date" %>
<%--
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
        <%! String userUsername = ""; %>
        <%! String userType = ""; %>
        <%
            userUsername = (String) session.getAttribute("user_username");
            userType = (String) session.getAttribute("user_type");
        %>
        <%= userType %>
        <%= userUsername %>
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
