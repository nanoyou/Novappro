package com.github.akagawatsurunaki.novappro.util;

import com.alibaba.fastjson2.JSON;
import com.github.akagawatsurunaki.novappro.constant.Constant;
import lombok.NonNull;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public final class ResponseUtil {
    /**
     * 为response设置JSON内容
     * @param response
     * @param json
     */
    public static void setBody(@NonNull HttpServletResponse response, @NonNull String json) {
        // 设置 response 的 Content-Type
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        // 使用输出流输出
        try(PrintWriter out = response.getWriter()) {
            //out.append(json);
            out.write(json);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setErrBody(@NonNull HttpServletResponse response) {
        setBody(response, JSON.toJSONString(Constant.exceptionServiceMessage));
    }
}
