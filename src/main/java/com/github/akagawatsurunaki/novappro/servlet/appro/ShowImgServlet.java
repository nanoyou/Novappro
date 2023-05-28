package com.github.akagawatsurunaki.novappro.servlet.appro;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@WebServlet(name = "ShowImgServlet", value = "/ShowImgServlet")
public class ShowImgServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String filePath = request.getParameter("file");

        // 如果文件路径为空或者不是文件，则返回 404 错误
        if (filePath == null || !new File(filePath).isFile()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String contentType = getServletContext().getMimeType(filePath);
        if (contentType == null || !contentType.startsWith("image")) {
            contentType = "application/octet-stream";
        }
        response.setContentType(contentType);

        try (FileInputStream inputStream = new FileInputStream(filePath)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                response.getOutputStream().write(buffer, 0, bytesRead);
            }
            response.getOutputStream().flush();
        }
    }
}
