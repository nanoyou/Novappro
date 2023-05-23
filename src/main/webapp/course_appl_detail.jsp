<%@ page import="com.github.akagawatsurunaki.novappro.constant.SC" %>
<%@ page import="java.util.List" %>
<%@ page import="com.github.akagawatsurunaki.novappro.model.database.course.Course" %>
<%@ page import="com.github.akagawatsurunaki.novappro.util.ZhFieldUtil" %>
<%@ page import="com.github.akagawatsurunaki.novappro.model.database.approval.CourseApplication" %>
<%@ page import="com.github.akagawatsurunaki.novappro.model.frontend.ServiceMessage" %>
<%@ page import="org.apache.commons.lang3.tuple.Triple" %>
<%@ page import="org.apache.commons.lang3.tuple.Pair" %>
<%@ page import="com.github.akagawatsurunaki.novappro.servlet.stu.CourseApplDetailServlet" %>
<%@ page import="com.github.akagawatsurunaki.novappro.model.database.approval.ApprovalFlowDetail" %>
<%@ page import="com.github.akagawatsurunaki.novappro.servlet.stu.StudentConfirmApprovalFlowServlet" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>学生申请课程系统 - 查看课程申请详细内容</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/table_common.css">
</head>
<body>
<%
    Pair<ServiceMessage, List<Course>> getAppliedCoursesServiceResult = (Pair<ServiceMessage, List<Course>>) request.getAttribute(CourseApplDetailServlet.ReqAttr.GET_APPLIED_COURSES_SERVICE_RESULT.value);
    List<Course> courses = getAppliedCoursesServiceResult.getRight();
    Triple<ServiceMessage, List<Course>, CourseApplication> updateAppliedCoursesServiceResult = (Triple<ServiceMessage, List<Course>, CourseApplication>) request.getAttribute(SC.ReqAttr.UPDATE_APPLIED_COURSES_SERVICE_RESULT.name);

    Pair<ServiceMessage, List<ApprovalFlowDetail>> getApprovalFlowDetailsServiceResult = (Pair<ServiceMessage,
            List<ApprovalFlowDetail>>)
            request.getAttribute(CourseApplDetailServlet.ReqAttr.GET_APPROVAL_FLOW_DETAILS_SERVICE_RESULT.value);

    if (updateAppliedCoursesServiceResult != null) {
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
<form method="post" action="${pageContext.request.contextPath}/modify_course_appl">
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
            <input type="hidden" name="<%=SC.ReqParam.UPDATED_COURSES.name%>"
                   value="<%=c.getCode()%>">
        </tr>
        <%
            }
        %>
    </table>
</form>

<%
    if (getApprovalFlowDetailsServiceResult == null) {
        return;
    }


%>
<div>
    <%=getApprovalFlowDetailsServiceResult.getLeft().getMessage()%>
</div>

<%
    if (getApprovalFlowDetailsServiceResult.getLeft().getMessageLevel() == ServiceMessage.Level.SUCCESS) {

        List<ApprovalFlowDetail> approvalFlowDetails = getApprovalFlowDetailsServiceResult.getRight();
%>
<table>
    <tr>
        <th>
            <%=ZhFieldUtil.getZhValue(ApprovalFlowDetail.class, ApprovalFlowDetail.Fields.auditUserId)%>
        </th>
        <th>
            <%=ZhFieldUtil.getZhValue(ApprovalFlowDetail.class, ApprovalFlowDetail.Fields.auditRemark)%>
        </th>
        <th>
            <%=ZhFieldUtil.getZhValue(ApprovalFlowDetail.class, ApprovalFlowDetail.Fields.auditStatus)%>
        </th>
        <th>
            <%=ZhFieldUtil.getZhValue(ApprovalFlowDetail.class, ApprovalFlowDetail.Fields.auditTime)%>
        </th>
    </tr>

    <%
        for (ApprovalFlowDetail approvalFlowDetail : approvalFlowDetails) {
    %>

    <tr>
        <td>
            <%=approvalFlowDetail.getAuditUserId()%>
        </td>
        <td>
            <%=approvalFlowDetail.getAuditRemark()%>
        </td>
        <td>
            <%=approvalFlowDetail.getAuditStatus().chinese%>
        </td>
        <td>
            <%=approvalFlowDetail.getAuditTime()%>
        </td>
    </tr>
    <%
        }
    %>

</table>
<%
    }
%>

<form action="${pageContext.request.contextPath}/stu_confirm_appro">
    <input type="hidden" value="<%=flowNo%>" name="<%=StudentConfirmApprovalFlowServlet.ReqParam.FLOW_NO.value%>">
    <input type="submit"
           value="确定"
           name="<%=StudentConfirmApprovalFlowServlet.ReqParam.CONFIRM.value%>">
</form>

</body>
</html>
