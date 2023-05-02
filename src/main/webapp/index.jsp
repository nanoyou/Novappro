<%@ page import="com.github.akagawatsurunaki.novappro.constant.Constant" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<style>
    /* 控制元素排列居中 */
    form {
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
    }

    /* 登录标题样式 */
    h1 {
        font-size: 24px;
        font-weight: bold;
        margin-bottom: 30px;
    }

    /* 输入框样式 */
    input[type="number"], input[type="password"] {
        width: 300px;
        height: 40px;
        border: 1px solid #ccc;
        border-radius: 5px;
        padding: 10px;
        margin-bottom: 20px;
        font-size: 16px;
    }

    /* 按钮样式 */
    input[type="submit"], input[type="button"] {
        width: 180px;
        height: 40px;
        border: none;
        border-radius: 5px;
        background-color: #0074D9;
        color: #fff;
        font-size: 16px;
        cursor: pointer;
        margin-bottom: 20px;
    }

    /* 鼠标悬停样式 */
    input[type="submit"]:hover, input[type="button"]:hover {
        background-color: #005DAB;
    }

    /* 取消数字输入的上下箭头 */
    /* google、safari */
    input::-webkit-outer-spin-button,
    input::-webkit-inner-spin-button{
        -webkit-appearance: none !important;
        margin: 0;
    }
    /* 火狐 */
    input[type="number"]{
        -moz-appearance: textfield;
    }

</style>
<script type="text/javascript">
    function checkForm() {
        const password = document.getElementById("password_input").value;
        const username = document.getElementById("user_id").value;

        if (username === "" || password === "") {
            alert("学号或密码不能为空！");
            return false;
        }

        // 如果都不为空，执行提交操作
        return true;
    }
</script>
<body>
<form action="http://localhost:8080/Novappro_war_exploded/login" method="post" onsubmit="checkForm()">
    <h1 style="text-align: center">登录</h1>
    <label>
        学号/工号
        <input id="user_id" type="number" minlength="<%= Constant.MIN_LEN_USER_ID %>" maxlength="<%=
        Constant.MAX_LEN_USER_ID %>" name="userId">
    </label>
    <label>
        密码
        <input id="password_input" type="password" minlength="<%= Constant.MIN_LEN_PASSWORD %>" maxlength="<%= Constant.MAX_LEN_PASSWORD %>" name="rawPassword">
    </label>

    <label>
        <input id="login_btn" type="submit" name="submit" value="登录">
    </label>

    <label>
        <input type="button" value="注册" onclick='location.href=("/Novappro_war_exploded/signup.jsp")'/>
    </label>
</form>

</body>
</html>