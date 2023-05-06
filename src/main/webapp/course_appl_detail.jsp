<%@ page import="com.github.akagawatsurunaki.novappro.constant.SC" %>
<%@ page import="java.util.List" %>
<%@ page import="com.github.akagawatsurunaki.novappro.model.database.course.Course" %>
<%@ page import="com.github.akagawatsurunaki.novappro.util.ZhFieldUtil" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>学生申请课程系统 - 查看课程申请详细内容</title>

</head>
<script>
    function removeCourse(id) {
        const elem = document.getElementById("crs_row_" + id);
        elem.parentNode.removeChild(elem);
    }

    function refresh(flowNo) {
        location.href =
            "${pageContext.request.contextPath}/course_appl_detail?<%=SC.ReqAttr.SELECTED_COURSE_APPL_FLOW_NO.name%>=" + flowNo
    }

    function addCourse(flowNo) {
        const code = document.getElementById("text_add_course").value;
        const crsTable = document.getElementById("courses_table");
        crsTable.insertRow();
        // const row = crsTable.rows[crsTable.rows.length - 1];
        const elem = '<input type="hidden" name="<%=SC.ReqParam.UPDATED_COURSES.name%>" value="' +
            code + '">';
        const input = document.createElement('input');

        crsTable.appendChild(input);
        refresh(flowNo)
    }
</script>
<body>
<h1>查看课程申请详细内容</h1>

<%
    String flowNo = (String) request.getAttribute(SC.ReqParam.SELECTED_COURSE_APPL_FLOW_NO.name);
%>
<form method="post" action="${pageContext.request.contextPath}
<%=SC.WebServletValue.MODIFY_COURSE_APPL%>">
    单号 <%=flowNo%>
    <input type="hidden" name="<%=SC.ReqParam.SELECTED_COURSE_APPL_FLOW_NO.name%>" value="<%=flowNo%>">
    <table id="courses_table" border="1">
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
            List<Course> courses =
                    (List<Course>) request.getAttribute(SC.ReqAttr.APPLIED_COURSES.name);
            int index = 1;
            for (Course c :
                    courses) {
        %>

        <tr id="crs_row_<%=c.getCode()%>">
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
            <input type="hidden" name="<%=SC.ReqParam.UPDATED_COURSES.name%>"
                    value="<%=c.getCode()%>">
        </tr>
        <%
            }
        %>
    </table>
    <label>
        输入课程代码
        <input id="text_add_course" type="text" name="<%=SC.ReqParam.UPDATED_COURSES.name%>" value=""/>
    </label>
    <label>
        <input type="submit" value="增加课程" onclick="refresh('<%=flowNo%>')" />
    </label>
    <label>
        <input type="submit" value="确认修改" onclick="refresh('<%=flowNo%>')"/>
    </label>
</form>
</body>
</html>
