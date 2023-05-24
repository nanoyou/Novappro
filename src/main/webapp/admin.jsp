<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>管理员界面</title>
</head>
<body>
    <h1>管理员界面</h1>
    <form action="${pageContext.request.contextPath}/userManage" method="get">
        <input type="submit" value="用户管理">
    </form>
    <form action="${pageContext.request.contextPath}/courseManage" method="get">
        <input type="submit" value="课程管理">
    </form>
    <form action="${pageContext.request.contextPath}/approvalFlowManage" method="get">
        <input type="submit" value="审批流管理">
    </form>
    <form action="${pageContext.request.contextPath}/approvalAuthorityManage" method="get">
        <input type="submit" value="审批权限管理">
    </form>
</body>
</html>
