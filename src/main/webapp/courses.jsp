<%@ page import="java.util.List" %>
<%@ page import="com.github.akagawatsurunaki.novappro.model.database.course.Course" %>
<%@ page import="com.github.akagawatsurunaki.novappro.util.ZhFieldUtil" %>
<%@ page import="com.github.akagawatsurunaki.novappro.constant.SC" %>
<%@ page import="com.github.akagawatsurunaki.novappro.model.frontend.ServiceMessage" %>
<%@ page import="org.apache.commons.lang3.tuple.Pair" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>学生申请课程系统 - 课程表</title>
</head>
<script>
    function courseAppl() {
        location.href = "${pageContext.request.contextPath}/apply_courses"
    }
</script>
<style>
    body {
        font-size: 14px;
        margin: 0;
        padding: 0;
    }

    h1 {
        font-size: 32px;
        font-weight: 700;
        line-height: 1.2;
        margin-bottom: 20px;

    }

    p {
        margin-bottom: 10px;
    }

    table {
        border-collapse: collapse;
        width: 100%;
    }

    th, td {
        border: 1px solid #ccc;
        padding: 8px;
        text-align: center;
    }

    tr:nth-child(even) {
        background-color: #f2f2f2;
    }

    tr:hover {
        background-color: #ddd;
    }

    input[type=radio] {
        margin-right: 5px;
    }

    label, input[type=file] {
        display: block;
        margin-bottom: 10px;
    }

    input[type=text], input[type=file], input[type=button], input[type=submit] {
        border: none;
        border-radius: 5px;
        font-size: 16px;
        padding: 10px;
    }

    input[type=button], input[type=submit] {
        background-color: #007bff;
        color: #fff;
        cursor: pointer;
        transition: background-color .3s ease;
    }

    input[type=button]:hover, input[type=submit]:hover {
        background-color: #0056b3;
    }

</style>
<body>
<h1>当前课程表</h1>

<%
    Pair<ServiceMessage, List<Course>> courses = (Pair<ServiceMessage, List<Course>>) request.getAttribute(SC.ReqAttr.COURSES_CAN_BE_APPLIED.name);
%>
<p>
    <%=courses.getLeft().getMessage()%>
</p>
<form action="${pageContext.request.contextPath}/apply_courses" method="post" enctype="multipart/form-data">
    <table border="1">查询到的课程
        <%-- 表头 --%>
        <tr>
            <th>序号</th>
            <th><%=ZhFieldUtil.getZhValue(Course.class, Course.Fields.code)%>
            </th>
            <th><%=ZhFieldUtil.getZhValue(Course.class, Course.Fields.name)%>
            </th>
            <th><%=ZhFieldUtil.getZhValue(Course.class, Course.Fields.credit)%>
            </th>
            <th><%=ZhFieldUtil.getZhValue(Course.class, Course.Fields.serialNumber)%>
            </th>
            <th><%=ZhFieldUtil.getZhValue(Course.class, Course.Fields.teachers)%>
            </th>
            <th><%=ZhFieldUtil.getZhValue(Course.class, Course.Fields.onlineContactWay)%>
            </th>
            <th><%=ZhFieldUtil.getZhValue(Course.class, Course.Fields.comment)%>
            </th>
            <th>
                操作: 选择此课程
            </th>
        </tr>

        <%
            int index = 1;

            if (courses.getLeft().getMessageLevel().equals(ServiceMessage.Level.SUCCESS)) {
                for (Course c :
                        courses.getRight()) {
        %>

        <tr>
            <td><%=index++%>
            </td>
            <td><%=c.getCode()%>
            </td>
            <td><%=c.getName()%>
            </td>
            <td><%=c.getCredit()%>
            </td>
            <td><%=c.getSerialNumber()%>
            </td>
            <td><%=c.getTeachers()%>
            </td>
            <td><%=c.getOnlineContactWay()%>
            </td>
            <td><%=c.getComment()%>
            </td>
            <td>
                <label>
                    <input type="radio" name="selected_course[]" value="<%= c.getCode()%>"/>
                </label>
            </td>
        </tr>

        <%
                }
            }
        %>
    </table>
    <label>
        <input name="remark" type="text" placeholder="请输入申请原因">
    </label>
    <label>
        <input name="upload_img" type="file" alt="">
    </label>
    <label>
        <input id="confirm_btn" type="submit" value="确认申请课程"/>
    </label>

</form>
<label>
    <input id="course_appl_btn" type="button" value="查看我的课程申请" onclick="courseAppl()"/>
</label>
</body>
</html>
