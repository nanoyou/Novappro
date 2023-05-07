<%@ page import="com.github.akagawatsurunaki.novappro.constant.SC" %>
<%@ page import="java.util.List" %>
<%@ page import="com.github.akagawatsurunaki.novappro.model.database.course.Course" %>
<%@ page import="com.github.akagawatsurunaki.novappro.util.ZhFieldUtil" %>
<%@ page import="com.github.akagawatsurunaki.novappro.model.database.approval.CourseApplication" %>
<%@ page import="com.github.akagawatsurunaki.novappro.model.frontend.ServiceMessage" %>
<%@ page import="org.apache.commons.lang3.tuple.Triple" %>
<%@ page import="org.apache.commons.lang3.tuple.Pair" %>
<%@ page import="com.github.akagawatsurunaki.novappro.servlet.stu.CourseApplDetailServlet" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>学生申请课程系统 - 查看课程申请详细内容</title>
</head>
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
<%
    Pair<ServiceMessage, List<Course>> getAppliedCoursesServiceResult = (Pair<ServiceMessage, List<Course>>) request.getAttribute(CourseApplDetailServlet.ReqAttr.GET_APPLIED_COURSES_SERVICE_RESULT.value);
    List<Course> courses = getAppliedCoursesServiceResult.getRight();
    Triple<ServiceMessage, List<Course>, CourseApplication> updateAppliedCoursesServiceResult  = (Triple<ServiceMessage, List<Course>, CourseApplication>)request.getAttribute(SC.ReqAttr.UPDATE_APPLIED_COURSES_SERVICE_RESULT.name);
    if (updateAppliedCoursesServiceResult != null){
        courses = updateAppliedCoursesServiceResult.getMiddle();
    }

%>

<h1>查看课程申请详细内容</h1>
<div>
    <%=getAppliedCoursesServiceResult.getLeft().getMessage()%>
</div>
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
            int index = 1;
            for (Course c : courses) {
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
<%--            <td>--%>
<%--                <label>--%>
<%--                    <input type="button" value="删除" onclick="removeCourse('<%=c.getCode()%>')"/>--%>
<%--                </label>--%>
<%--            </td>--%>
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
