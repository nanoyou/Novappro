<%@ page import="com.github.akagawatsurunaki.novappro.constant.ServletConstant" %>
<%@ page import="java.util.List" %>
<%@ page import="com.github.akagawatsurunaki.novappro.model.course.Course" %>
<%@ page
        import="static com.github.akagawatsurunaki.novappro.constant.ServletConstant.RequestAttr.SELECTED_COURSE_APPL_FLOW_NO" %>
<%--
  Created by IntelliJ IDEA.
  User: 96514
  Date: 2023/4/16
  Time: 22:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>

</head>
<script>
    function removeCourse(id) {
        var elem = document.getElementById("crs_row_" + id);
        elem.parentNode.removeChild(elem);
    }
    function refresh(flowNo){
        location.href =
            "${pageContext.request.contextPath}/course_appl_detail?<%=SELECTED_COURSE_APPL_FLOW_NO.name%>="+flowNo
    }
</script>
<body>
<h1>查看课程申请详细内容</h1>
<form method="post" action="./<%=ServletConstant.WebServletValue.MODIFY_COURSE_APPL%>">

    <%
        String flowNo = (String) request.getAttribute(ServletConstant.RequestParam.SELECTED_COURSE_APPL_FLOW_NO.name);
    %>

    单号 <%=flowNo%>
    <input type="hidden" name="<%=ServletConstant.RequestParam.SELECTED_COURSE_APPL_FLOW_NO.name%>" value="<%=flowNo%>">
    <table border="1">
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
            List<Course> courses =
                    (List<Course>) request.getAttribute(ServletConstant.RequestAttr.APPLIED_COURSES.name);
            int index = 1;
            for (Course c :
                    courses) {
        %>

        <tr  id="crs_row_<%=c.getCode()%>">
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
                    <input type="button" value="删除" onclick="removeCourse('<%=c.getCode()%>')"/>
                </label>
            </td>
            <input type="hidden" name="<%=ServletConstant.RequestParam.UPDATED_COURSES.name%>" value="<%=c.getCode()%>">
        </tr>
        <tr>

        </tr>
        <%
            }
        %>
    </table>
    <label>
        <input type="button" name="add_appro_course" value="增加课程"/>
    </label>
    <label>
        <input type="submit" name="" value="确认修改" onclick="refresh('<%=flowNo%>')"/>
    </label>
    <label>
        <input type="button" name="" value="取消修改"/>
    </label>
</form>
</body>
</html>
