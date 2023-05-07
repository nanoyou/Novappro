<%@ page import="com.github.akagawatsurunaki.novappro.constant.Constant" %>
<%@ page import="com.github.akagawatsurunaki.novappro.constant.SC" %>
<%@ page import="com.github.akagawatsurunaki.novappro.servlet.base.LoginServlet" %>
<%@ page import="com.github.akagawatsurunaki.novappro.model.frontend.ServiceMessage" %>
<%@ page import="com.github.akagawatsurunaki.novappro.model.database.User" %>
<%@ page import="org.apache.commons.lang3.tuple.Pair" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<style>
    html,
    body {
        margin: 0;
        font-family: "PingFang SC", "Microsoft Yahei", sans-serif;
    }

    .container {
        width: 100vw;
        height: 100vh;
        display: flex;
        align-items: center;
        justify-content: center;
        /*background: url("http://localhost:8080/Novappro_war_exploded/img/index.png") fixed no-repeat;*/
        background: url("${pageContext.request.contextPath}/img/index.png") fixed no-repeat;
        background-size: cover;
    }

    .login-form {
        width: 240px;
        height: 250px;
        display: flex;
        flex-direction: column;
        padding: 40px;
        text-align: center;
        position: relative;
        z-index: 100;
        /*background: inherit;*/
        border-radius: 18px;
        background-color: rgba(255, 255, 255, 0.7);
        overflow: hidden; /* 隐藏多余的模糊效果*/
    }

    .login-form h2 {
        font-size: 24px;
        font-weight: 400;
        color: #3d5245;
    }

    .login-form input,
    .login-form button {
        margin: 6px 0;
        height: 36px;
        border: none;
        background-color: rgba(255, 255, 255, 0.5);
        border-radius: 4px;
        padding: 0 14px;
        color: #3d5245;

    }

    .login-form button:hover {
        cursor: pointer;
    }

    .login-form a {
        text-align: right;
        padding-left: 156px;
        font-size: 10px;
        text-decoration: none;
        color: rgb(73, 73, 73);
    }

    .err-msg {
        position: relative;
        font-size: 10px;
        float: top;
        color: rgb(255, 0, 0);
        /* 错误提示闪烁 */
        animation: blink 1.5s linear 3;
        -webkit-animation: blink 1.5s linear 3;
        -moz-animation: blink 1.5s linear 3;
        -ms-animation: blink 1.5s linear 3;
        -o-animation: blink 1.5s linear 3;
    }


    @keyframes blink {
        0% {
            color: red;
        }

        50% {
            color: transparent;
        }

        100% {
            color: red;
        }
    }

    /*去除在火狐浏览器中数字型输入框的上下小箭头*/
    input[type=number] {
        -moz-appearance: textfield;
    }

    /*去除在Chrome中type=number的input输入框中的上下小箭头*/
    input::-webkit-outer-spin-button, input::-webkit-inner-spin-button {
        -webkit-appearance: none;
    }
</style>
<script type="text/javascript">
    function redirect(flag) {
        if (flag) {
            location.href = "${pageContext.request.contextPath}/get_appros";
        }
    }
</script>
<body>
<div class="container">
    <form action="${pageContext.request.contextPath}/login"
          method="get"
          class="login-form">
        <h2> NOVAPPRO </h2>

        <label>
            <input id="user_id" name="userId" type="number"
                   placeholder="工号/学号"
                   oninput="if(value.length><%= Constant.MAX_LEN_USER_ID %>) {
                           value=value.slice(0,<%= Constant.MAX_LEN_USER_ID %>)
                           }"
                   minlength="<%= Constant.MIN_LEN_USER_ID %>"
                   maxlength="<%= Constant.MAX_LEN_USER_ID %>">
        </label>

        <label>
            <input id="password_input" name="rawPassword" type="password"
                   placeholder="密码"
                   minlength="<%= Constant.MIN_LEN_PASSWORD %>"
                   maxlength="<%= Constant.MAX_LEN_PASSWORD %>">
        </label>

        <label>
            <%
                Pair<ServiceMessage, User> loginServiceResult =
                        (Pair<ServiceMessage, User>) request.getAttribute(LoginServlet.ReqAttr.LOGIN_SERVICE_RESULT.value);
                if (loginServiceResult != null) {
                    if (loginServiceResult.getLeft().getMessageLevel() != ServiceMessage.Level.SUCCESS) {
            %>
            <p class="err-msg">
                <%=loginServiceResult.getLeft().getMessage()%>
            </p>
            <% }
            }%>
        </label>

        <label>
            <a href="${pageContext.request.contextPath}/<%=SC.JSPResource.SIGN_UP.name%>">注册</a>
        </label>

        <label>
            <button id="login_btn" name="submit" type="submit" value="登录" onclick="redirect()">
                登录
            </button>
        </label>


    </form>
</div>


</body>
</html>