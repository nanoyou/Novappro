package com.github.akagawatsurunaki.novappro.util;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

public final class RequestUtil {
    public static  <Model> Model parse(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        }
        String json = sb.toString();
        // 处理 JSON 数据
        return JSONObject.parseObject(json, new TypeReference<Model>() {});
    }
}
