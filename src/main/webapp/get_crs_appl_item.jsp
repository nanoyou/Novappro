<%@ page import="java.util.List" %>
<%@ page import="com.github.akagawatsurunaki.novappro.model.database.course.Course" %>
<%@ page import="com.github.akagawatsurunaki.novappro.model.frontend.CourseAppItemDetail" %>
<%@ page import="com.github.akagawatsurunaki.novappro.constant.ServletConstant" %>
<%@ page import="com.github.akagawatsurunaki.novappro.util.ZhFieldUtil" %>
<%--
  Created by IntelliJ IDEA.
  User: 96514
  Date: 2023/4/20
  Time: 12:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>详细</title>
</head>
<body>
<%
    CourseAppItemDetail caid = (CourseAppItemDetail)
            request.getAttribute(ServletConstant.RequestAttr.SELECTED_APPL_ITEM_DETAIL.name);
%>
这是一份详细的申请。

<table border="1">
    <caption style="font-size: large">
        <h2><%=caid.getTitle()%></h2>
        <p style="font-style: italic">
            <%=ZhFieldUtil.getZhValue(CourseAppItemDetail.class, CourseAppItemDetail.Fields.flowNo)%>
            :
            <%=caid.getFlowNo()%>
        </p>
    </caption>
    <tbody>
    <tr>
        <th>
            申请人信息
        </th>
    </tr>
    <tr>
        <td>
            <%=ZhFieldUtil.getZhValue(CourseAppItemDetail.class, CourseAppItemDetail.Fields.applicantId)%>
        </td>
        <td>
            <%=caid.getApplicantId()%>
        </td>
    </tr>
    <tr>
        <td>
            <%=ZhFieldUtil.getZhValue(CourseAppItemDetail.class, CourseAppItemDetail.Fields.applicantName)%>
        </td>
        <td>
            <%=caid.getApplicantName()%>
        </td>
    </tr>
    <tr>
        <th>
            申请详细
        </th>
    </tr>
    <tr>
        <td>
            <%=ZhFieldUtil.getZhValue(CourseAppItemDetail.class, CourseAppItemDetail.Fields.addTime)%>
        </td>
        <td>
            <%=caid.getAddTime()%>
        </td>
    </tr>
    <tr>
        <td>
            <%=ZhFieldUtil.getZhValue(CourseAppItemDetail.class, CourseAppItemDetail.Fields.applCourses)%>
        </td>
        <td>
            <table border="1">
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
                </tr>

                <%
                    List<Course> courses = caid.getApplCourses();
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
                </tr>

                <%
                    }
                %>
            </table>
        </td>
    </tr>
    <tr>
        <td><%=ZhFieldUtil.getZhValue(CourseAppItemDetail.class, CourseAppItemDetail.Fields.approStatus)%>></td>
        <td><%=caid.getApproStatus().chinese%></td>
    </tr>
    </tbody>
</table>

</body>
</html>
