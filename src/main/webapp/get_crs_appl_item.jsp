<%@ page import="java.util.List" %>
<%@ page import="com.github.akagawatsurunaki.novappro.model.database.course.Course" %>
<%@ page import="com.github.akagawatsurunaki.novappro.model.frontend.CourseAppItemDetail" %>
<%@ page import="com.github.akagawatsurunaki.novappro.constant.SC" %>
<%@ page import="com.github.akagawatsurunaki.novappro.util.ZhFieldUtil" %>
<%@ page import="static com.github.akagawatsurunaki.novappro.constant.Constant.MAX_LEN_COURSE_COMMENT" %>
<%@ page import="com.github.akagawatsurunaki.novappro.servlet.appro.ApplItemDetailServlet" %>
<%@ page import="com.github.akagawatsurunaki.novappro.model.frontend.ServiceMessage" %>
<%@ page import="java.util.Optional" %>
<%@ page import="org.apache.commons.lang3.tuple.Pair" %>
<%@ page import="com.github.akagawatsurunaki.novappro.servlet.appro.SubmitApproResultServlet" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
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

    .reject {
    !important;
        background-color: #ff3030;
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

    .err-msg {
        position: relative;
        font-size: 10px;
        float: top;
        color: rgb(255, 0, 0);
        /* 错误提示闪烁 */
        animation: blink 1.5s linear 3;
        -webkit-animation: blink 1.5s linear 3;
        -moz-animation: blink 1.5s linear 3;
        -ms-animation: blink 1.5s linear 3;
        -o-animation: blink 1.5s linear 3;
    }


    @keyframes blink {
        0% {
            color: red;
        }

        50% {
            color: transparent;
        }

        100% {
            color: red;
        }
    }

</style>
<head>
    <title>教师审批系统 - 详细</title>
</head>
<body>
<%
    Pair<ServiceMessage, Optional<CourseAppItemDetail>> getApplItemServiceResult =
            (Pair<ServiceMessage, Optional<CourseAppItemDetail>>) request.getAttribute(ApplItemDetailServlet.ReqAttr.GET_COURSE_APP_ITEM_DETAIL_SERVICE_RESULT.value);
    Pair<ServiceMessage, Optional<CourseAppItemDetail>> saveApproResultServiceResult =
            (Pair<ServiceMessage, Optional<CourseAppItemDetail>>)
                    request.getAttribute(SubmitApproResultServlet.ReqAttr.SAVE_APPRO_RESULT_SERVICE_RESULT.value);

%>
<%-- 审批人点击一个详细的ApplicationItem就可以跳转到这个界面 --%>

<form method="post" action="submit_appro_ret">
    <%
        if (getApplItemServiceResult.getRight().isEmpty()) {
            return;
        }
        CourseAppItemDetail caid = getApplItemServiceResult.getRight().get();
    %>
    <table border="1">
        <caption style="font-size: large">
            <h2><%=caid.getTitle()%>
            </h2>
            <p style="font-style: italic">
                <%=ZhFieldUtil.getZhValue(CourseAppItemDetail.class, CourseAppItemDetail.Fields.flowNo)%>
                :
                <%=caid.getFlowNo()%>
            </p>
            <input style="display: none" name="<%=SC.ReqParam.SELECTED_APPL_ITEM_FLOW_NO.name%>"
                   value="<%=caid.getFlowNo()%>">
        </caption>
        <tbody>
        <tr>
            <td colspan="2" style="font-weight: bold; font-size: larger">
                申请人信息
            </td>
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
            <td colspan="2" style="font-weight: bold; font-size: larger">
                申请详细
            </td>
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
            <td><%=ZhFieldUtil.getZhValue(CourseAppItemDetail.class, CourseAppItemDetail.Fields.approStatus)%>
            </td>
            <td><%=caid.getApproStatus().chinese%>
            </td>
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
                           style="width:800px; height:100px;"
                    >
                </label>
            </td>

        </tr>
        <tr style="display: none">
            <td>
                审批同意或失败
            </td>
        </tr>
        </tbody>
    </table>
    <div style="text-align: center;">
        <input type="submit" class="reject" name="<%=SC.ReqParam.APPL_ITEM_CONFIRM.name%>" value="驳回审批">
        <input type="submit" name="<%=SC.ReqParam.APPL_ITEM_CONFIRM.name%>" value="同意审批">
    </div>
    <div class="err-msg" style="text-align: center">
        <%
            if (getApplItemServiceResult.getLeft().getMessageLevel() != ServiceMessage.Level.SUCCESS) {
                out.print(getApplItemServiceResult.getLeft().getMessage());
            }

        %>
        <%
            if (saveApproResultServiceResult != null) {
                if (saveApproResultServiceResult.getLeft().getMessageLevel() != ServiceMessage.Level.SUCCESS) {
                    out.print(saveApproResultServiceResult.getLeft().getMessage());
                }
            }
        %>
    </div>
</form>

</body>
</html>
