package com.github.akagawatsurunaki.novappro.servlet.stu;

import com.github.akagawatsurunaki.novappro.constant.JSPResource;
import com.github.akagawatsurunaki.novappro.constant.SC;
import com.github.akagawatsurunaki.novappro.model.database.User;
import com.github.akagawatsurunaki.novappro.service.stu.ApplyCourseService;
import lombok.AllArgsConstructor;
import lombok.val;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@MultipartConfig
@WebServlet(name = "ApplyCoursesServlet", value = "/apply_courses")
public class ApplyCoursesServlet extends HttpServlet {

    private static final ApplyCourseService APPLY_COURSE_SERVICE = ApplyCourseService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        try {
            val id = ((User) request.getSession().getAttribute(SC.ReqAttr.LOGIN_USER.name)).getId();

            // 获取这些课程
            val getCourseApplsByUserIdServiceResult
                    = APPLY_COURSE_SERVICE.getCourseApplsByUserId(id);

            request.setAttribute(ReqAttr.GET_COURSE_APPLS_BY_USER_ID_SERVICE_RESULT.value,
                    getCourseApplsByUserIdServiceResult);
            request.getRequestDispatcher(JSPResource.GET_APPLIED_COURSES.value).forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        try {
            // 防止中文乱码
            request.setCharacterEncoding("UTF-8");

            // 获取登录人ID
            val user = (User) request.getSession().getAttribute(SC.ReqAttr.LOGIN_USER.name);
            String remark = null;

            if (!ServletFileUpload.isMultipartContent(request)) {
                return;
            }
            var factory = new DiskFileItemFactory();
            var upload = new ServletFileUpload(factory);
            String selectedCourseCode = null;
            InputStream is = null;
            var items = upload.parseRequest(request);

            for (FileItem item : items) {
                // i是普通表单项
                if (item.isFormField()) {
                    if ("selected_course[]".equals(item.getFieldName())) {
                        selectedCourseCode = item.getString();
                        continue;
                    }
                    if ("remark".equals(item.getFieldName())) {
                        remark = item.getString(StandardCharsets.UTF_8.name());
                    }
                } else {
                    is = item.getInputStream();
                }
            }
            // 申请这些课程
            val applyServiceResult = APPLY_COURSE_SERVICE.apply(user.getId(), selectedCourseCode, is, remark);
            request.setAttribute(ReqAttr.APPLY_SERVICE_RESULT.value, applyServiceResult);

            doGet(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }

    @AllArgsConstructor
    public enum ReqAttr {
        APPLY_SERVICE_RESULT("apply_service_result"),
        GET_COURSE_APPLS_BY_USER_ID_SERVICE_RESULT("get_course_appls_by_user_id_service_result");

        public final String value;
    }

}
