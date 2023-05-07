<%@ page import="com.github.akagawatsurunaki.novappro.constant.SC" %>
<%@ page import="java.util.List" %>
<%@ page import="com.github.akagawatsurunaki.novappro.model.database.approval.CourseApplication" %>
<%@ page import="com.github.akagawatsurunaki.novappro.enumeration.ApprovalStatus" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>学生申请课程系统 - 获取已申请课程</title>
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
    function detail(
        self, flowNo
    ) {
        location.href = "${pageContext.request.contextPath}/course_appl_detail?<%=SC.ReqAttr.SELECTED_COURSE_APPL_FLOW_NO.name%>=" + flowNo;
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
