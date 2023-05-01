<%@ page import="com.github.akagawatsurunaki.novappro.constant.SC" %>
<%@ page import="java.util.List" %>
<%@ page import="com.github.akagawatsurunaki.novappro.model.database.approval.CourseApplication" %>
<%@ page
        import="static com.github.akagawatsurunaki.novappro.constant.SC.ReqParam.SELECTED_COURSE_APPL_FLOW_NO" %>
<%@ page import="com.github.akagawatsurunaki.novappro.enumeration.ApprovalStatus" %>
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
<script>
    function detail(
        self, flowNo
    ) {
        location.href = "${pageContext.request.contextPath}/course_appl_detail?<%=SELECTED_COURSE_APPL_FLOW_NO.name%>=" + flowNo;
    }
</script>
<body>
<%
    List<CourseApplication> crsApps =
            (List<CourseApplication>) request.getAttribute(SC.ReqAttr.COURSE_APPLICATIONS.name);
    List<ApprovalStatus> approvalStatusList =
            (List<ApprovalStatus>) request.getAttribute(SC.ReqAttr.APPRO_STATUS_LIST.name);
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
            <td><%=crsApp.getApproCourseIds()%>
            </td>
            <td>
                <%=approvalStatusList.get(i).chinese%>
            </td>
            <td>
                <label>
                    <input id="<%= "btn" + crsApp.getFlowNo() %>>" type="button"
                           name="<%=SELECTED_COURSE_APPL_FLOW_NO.name%>"
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
