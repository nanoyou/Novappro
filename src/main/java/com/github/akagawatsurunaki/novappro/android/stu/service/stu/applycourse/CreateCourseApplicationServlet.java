package com.github.akagawatsurunaki.novappro.android.stu.service.stu.applycourse;

import com.github.akagawatsurunaki.novappro.constant.SC;
import com.github.akagawatsurunaki.novappro.model.database.User;
import com.github.akagawatsurunaki.novappro.service.stu.ApplyCourseService;
import com.github.akagawatsurunaki.novappro.servlet.stu.ApplyCoursesServlet;
import lombok.val;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@WebServlet(name = "CreateCourseApplicationServlet", value = "/android/applyCourseService/createCourseApplication")
public class CreateCourseApplicationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
            val applyServiceResult = ApplyCourseService.getInstance().apply(user.getId(), selectedCourseCode, is, remark);
            request.setAttribute(ApplyCoursesServlet.ReqAttr.APPLY_SERVICE_RESULT.value, applyServiceResult);

            doGet(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            response.sendRedirect("error.jsp");
        }
    }
}
