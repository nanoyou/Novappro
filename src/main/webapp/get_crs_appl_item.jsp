<%@ page import="java.util.List" %>
<%@ page import="com.github.akagawatsurunaki.novappro.model.database.course.Course" %>
<%@ page import="com.github.akagawatsurunaki.novappro.model.frontend.CourseAppItemDetail" %>
<%@ page import="com.github.akagawatsurunaki.novappro.constant.SC" %>
<%@ page import="com.github.akagawatsurunaki.novappro.util.ZhFieldUtil" %>
<%@ page import="static com.github.akagawatsurunaki.novappro.constant.Constant.MAX_LEN_COURSE_COMMENT" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>教师审批系统 - 详细</title>
</head>
<body>
<%
    CourseAppItemDetail caid = (CourseAppItemDetail)
            request.getAttribute(SC.ReqAttr.SELECTED_APPL_ITEM_DETAIL.name);
%>
<%-- 审批人点击一个详细的ApplicationItem就可以跳转到这个界面 --%>
这是一份详细的申请。
<form method="post" action="submit_appro_ret">



    <table border="1">
        <caption style="font-size: large">
            <h2><%=caid.getTitle()%></h2>
            <p style="font-style: italic">
                <%=ZhFieldUtil.getZhValue(CourseAppItemDetail.class, CourseAppItemDetail.Fields.flowNo)%>
                :
                <%=caid.getFlowNo()%>
            </p>
            <input style="display: none" name="<%=SC.ReqParam.SELECTED_APPL_ITEM_FLOW_NO.name%>" value="<%=caid.getFlowNo()%>">
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
            <%--        课程表--%>
            <td>
                <%=ZhFieldUtil.getZhValue(CourseAppItemDetail.class, CourseAppItemDetail.Fields.applCourses)%>
            </td>
            <td>
                <%--            课程表--%>
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
        <%--    申请状态--%>
        <tr>
            <td><%=ZhFieldUtil.getZhValue(CourseAppItemDetail.class, CourseAppItemDetail.Fields.approStatus)%>></td>
            <td><%=caid.getApproStatus().chinese%></td>
        </tr>
        <%--    对用户Application的审批Remark回复--%>
        <tr>
            <td>
                备注
            </td>
            <td>
                <label>
                    <input type="text"
                           name="<%=SC.ReqParam.APPL_REMARK.name%>"
                           maxlength="<%=MAX_LEN_COURSE_COMMENT%>"
                           placeholder="如果同意审批，可以此栏可以不填写；如果驳回审批，请输入不大于<%=MAX_LEN_COURSE_COMMENT%>个字符的驳回理由，理由不能为空。"
                           style="width:200px; height:20px;"
                    >
                </label>
            </td>

        </tr>
        <tr style="display: none" >
            <td>
                审批同意或失败
            </td>
        </tr>
        </tbody>
    </table>
    <input type="submit" name="<%=SC.ReqParam.APPL_ITEM_CONFIRM.name%>" value="驳回审批">
    <input type="submit" name="<%=SC.ReqParam.APPL_ITEM_CONFIRM.name%>" value="同意审批">
</form>

</body>
</html>
