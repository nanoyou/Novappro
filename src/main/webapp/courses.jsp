<%@ page import="java.util.List" %>
<%@ page import="com.github.akagawatsurunaki.novappro.model.course.Course" %>
<%@ page import="com.github.akagawatsurunaki.novappro.constant.LiteralConstant" %><%--
  Created by IntelliJ IDEA.
  User: 96514
  Date: 2023/4/11
  Time: 11:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>课程表</title>
</head>
<body>
<h1>当前课程表</h1>


<form action="${pageContext.request.contextPath}/apply_courses" method="post">
    <table border="1">查询到的课程
        <%-- 表头 --%>
        <tr>
            <%
                final Course tempCourse = new Course();
            %>
            <th>序号</th>
            <th><%=tempCourse.getChineseFieldNameAnnotation(Course.class, Course.Fields.code).value()%>
            </th>
            <th><%=tempCourse.getChineseFieldNameAnnotation(Course.class, Course.Fields.name).value()%>
            </th>
            <th><%=tempCourse.getChineseFieldNameAnnotation(Course.class, Course.Fields.credit).value()%>
            </th>
            <th><%=tempCourse.getChineseFieldNameAnnotation(Course.class, Course.Fields.serialNumber).value()%>
            </th>
            <th><%=tempCourse.getChineseFieldNameAnnotation(Course.class, Course.Fields.teachers).value()%>
            </th>
            <th><%=tempCourse.getChineseFieldNameAnnotation(Course.class, Course.Fields.onlineContactWay).value()%>
            </th>
            <th><%=tempCourse.getChineseFieldNameAnnotation(Course.class, Course.Fields.comment).value()%>
            </th>
            <th>
                操作: 选择此课程
            </th>
        </tr>

        <%
            List<Course> courses = (List<Course>) request.getAttribute(LiteralConstant.COURSES);
            int index = 1;
            for (Course c :
                    courses) {
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
                    <input type="checkbox" name="selected_course[]" value="<%= c.getCode()%>"/>
                </label>
            </td>
        </tr>

        <%
            }
        %>
    </table>
    <label>
        <input id="confirm_btn" type="submit" value="确认申请"/>
    </label>
</form>

</body>
</html>
