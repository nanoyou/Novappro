<%@ page import="com.github.akagawatsurunaki.novappro.model.frontend.ServiceMessage" %>
<%@ page import="com.github.akagawatsurunaki.novappro.model.database.course.Course" %>
<%@ page import="java.util.List" %>
<%@ page import="org.apache.commons.lang3.tuple.Pair" %>
<%@ page import="com.github.akagawatsurunaki.novappro.servlet.manage.CourseManageServlet" %>
<%@ page import="com.github.akagawatsurunaki.novappro.util.ZhFieldUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>管理员界面 - 课程管理</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/table_common.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/err_msg.css">
</head>
<body>
<%
    Pair<ServiceMessage, List<Course>> getAllCoursesServiceResult
            = (Pair<ServiceMessage, List<Course>>) request.getAttribute(CourseManageServlet.ReqAttr.GET_ALL_COURSES_SERVICE_RESULT.value);
%>
<h1>所有课程一览表</h1>
<form>
    <table>
        <%
            if (getAllCoursesServiceResult == null) {
                return;
            }

            List<Course> courses = getAllCoursesServiceResult.getRight();

            if (getAllCoursesServiceResult.getLeft().getMessageLevel() == ServiceMessage.Level.SUCCESS) {

        %>
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
            }
        %>
        <%
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
                    <input type="radio" name="selected_course[]" value="<%= c.getCode()%>"/>
                </label>
            </td>
        </tr>

        <%
            }
        %>
    </table>

</form>


</body>
</html>
