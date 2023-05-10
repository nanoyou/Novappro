<%@ page import="com.github.akagawatsurunaki.novappro.constant.SC" %>
<%@ page import="java.util.List" %>
<%@ page import="com.github.akagawatsurunaki.novappro.model.database.approval.CourseApplication" %>
<%@ page import="com.github.akagawatsurunaki.novappro.enumeration.ApprovalStatus" %>
<%@ page import="com.github.akagawatsurunaki.novappro.servlet.stu.ApplyCoursesServlet" %>
<%@ page import="lombok.val" %>
<%@ page import="com.github.akagawatsurunaki.novappro.model.frontend.ServiceMessage" %>
<%@ page import="org.apache.commons.lang3.tuple.Triple" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>学生申请课程系统 - 课程申请列表</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/table_common.css">
</head>
<script>
    function detail(
        self, flowNo
    ) {
        location.href = "${pageContext.request.contextPath}/course_appl_detail?<%=SC.ReqAttr.SELECTED_COURSE_APPL_FLOW_NO.name%>=" + flowNo;
    }
</script>
<body>
<%

    Triple<ServiceMessage, List<CourseApplication>, List<ApprovalStatus>> getCourseApplsByUserIdServiceResult =
            (Triple<ServiceMessage, List<CourseApplication>, List<ApprovalStatus>>)
                    request.getAttribute(ApplyCoursesServlet.ReqAttr.GET_COURSE_APPLS_BY_USER_ID_SERVICE_RESULT.value);

    if (getCourseApplsByUserIdServiceResult.getLeft().getMessageLevel() != ServiceMessage.Level.SUCCESS) {
        return;
    }

    List<CourseApplication> crsApps = getCourseApplsByUserIdServiceResult.getMiddle();
    List<ApprovalStatus> approvalStatusList = getCourseApplsByUserIdServiceResult.getRight();
%>

<h1>您的课程申请</h1>
以下是您的课程申请记录
<form method="get">
    <table border="1">
        <tr>
            <th>流水号</th>
            <th>工号</th>
            <th>选课编号</th>
            <th>审批状态</th>
            <th>操作</th>
        </tr>

        <%
            int i = 0;
            for (CourseApplication crsApp : crsApps) {
        %>

        <tr>
            <td><%=crsApp.getFlowNo()%>
            </td>
            <td><%=crsApp.getAddUserId()%>
            </td>
            <td><%=crsApp.getApproCourses()%>
            </td>
            <td>
                <%=approvalStatusList.get(i).chinese%>
            </td>
            <td>
                <label>
                    <input id="<%= "btn" + crsApp.getFlowNo() %>>" type="button"
                           name="<%=SC.ReqAttr.SELECTED_COURSE_APPL_FLOW_NO.name%>"
                           value="查看详情" onclick="detail(this, '<%= crsApp.getFlowNo() %>')"/>
                </label>
            </td>
        </tr>

        <%
                i++;
            }
        %>
    </table>
</form>

</body>
</html>
