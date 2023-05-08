<%@ page import="java.util.Date" %>

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
