package com.github.akagawatsurunaki.novappro.servlet.stu;

import com.github.akagawatsurunaki.novappro.constant.SC;
import com.github.akagawatsurunaki.novappro.constant.VC;
import com.github.akagawatsurunaki.novappro.service.stu.ApplyCourseService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
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
import java.util.ArrayList;
import java.util.List;

@MultipartConfig
@WebServlet(name = "ApplyCoursesServlet", value = SC.WebServletValue.APPLY_COURSES)
public class ApplyCoursesServlet extends HttpServlet {

    private static final ApplyCourseService APPLY_COURSE_SERVICE = ApplyCourseService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {

        // 防止中文乱码
        request.setCharacterEncoding("UTF-8");

        // 获取登录人ID
        Integer id = (Integer) request.getSession().getAttribute("login_user_id");
        String remark = null;

        if (!ServletFileUpload.isMultipartContent(request)) {
            return;
        }
        var factory = new DiskFileItemFactory();
        var upload = new ServletFileUpload(factory);
        String selectedCourseCode = null;
        InputStream is = null;
        try {

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

        } catch (FileUploadException | IOException e) {
            System.out.println("InputStream 不能获取。");
            e.printStackTrace();
        }

        if (is == null || remark == null) {
            System.out.println("is 和 remark 不可以为空.");
            return;
        }

        // 获取选取的课程列表
        if (selectedCourseCode != null) {

            List<String> selectedCourseCodeList = new ArrayList<>();
            selectedCourseCodeList.add(selectedCourseCode);
            // 申请这些课程
            APPLY_COURSE_SERVICE.apply(id, selectedCourseCodeList, is, remark);

        }

        // 获取这些课程
        var vc_cal_asl = APPLY_COURSE_SERVICE.getCourseApplsByUserId(id);

        if (vc_cal_asl.getLeft() == VC.Service.OK) {
            var courseApplications = vc_cal_asl.getMiddle();
            var approStatusList = vc_cal_asl.getRight();

            // 设置到 Request 中
            request.setAttribute(SC.ReqAttr.COURSE_APPLICATIONS.name, courseApplications);
            request.setAttribute(SC.ReqAttr.APPRO_STATUS_LIST.name, approStatusList);

            // 跳转页面
            request.getRequestDispatcher(SC.JSPResource.GET_APPLIED_COURSES.name).forward(request, response);
        }


    }
}
