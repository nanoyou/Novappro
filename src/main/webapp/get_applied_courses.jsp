<%@ page import="com.github.akagawatsurunaki.novappro.constant.ServletConstant" %>
<%@ page import="java.util.List" %>
<%@ page import="com.github.akagawatsurunaki.novappro.model.approval.CourseApplication" %>
  Created by IntelliJ IDEA.
  User: 96514
  Date: 2023/4/16
  Time: 16:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>获取已申请课程</title>
</head>
<body>
<%
    List<CourseApplication> crsApps =
            (List<CourseApplication>) request.getAttribute(ServletConstant.RequestAttr.COURSE_APPLICATIONS.name);
%>

<h1>申请的课程</h1>

<form action="${pageContext.request.contextPath}/apply_courses" method="post">
    <table border="1">
        <tr>
            <th>号</th>
            <th>工号</th>
            <th>选课编号</th>
            <th>操作</th>
        </tr>

        <%
            for (CourseApplication crsApp : crsApps) {

        %>

        <tr>
            <td><%=crsApp.getFlowNo()%>
            </td>
            <td><%=crsApp.getAddUserId()%>
            </td>
            <td><%=crsApp.getApproCourseIds()%>
            </td>
            <td>
                <label>
                    <input id="<%= "btn" + crsApp.getFlowNo() %>>" type="button" name="selected_course[]" value="查看详情"/>
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