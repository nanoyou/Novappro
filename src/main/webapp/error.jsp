<%--
  Created by IntelliJ IDEA.
  User: 96514
  Date: 2023/3/27
  Time: 18:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
    function init() {
        const elem = document.getElementById('sec');
        let time = 10;
        setInterval(function () {
            elem.innerHTML = --time;
            if (time === 0) {
                location.href = '${pageContext.request.contextPath}/index.jsp';
            }
        }, 1000)
    }
</script>
<html>
<head>
    <title>错误 - 服务器内部可能发生了错误</title>
</head>
<body onload="init()">
<h1 style="color: red">哦，看起来我们遇到了一些问题，审批并非一日可成，喝杯咖啡休息一下吧！</h1>
<p>
    我们将在<span id="sec">10</span>秒后返回主页。
    <a href="${pageContext.request.contextPath}/index.jsp">点击这里</a>立即返回主页。
</p>
</body>
</html>
