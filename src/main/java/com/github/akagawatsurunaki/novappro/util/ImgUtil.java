package com.github.akagawatsurunaki.novappro.util;

import cn.hutool.core.util.NumberUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public final class ImgUtil {
    // A.T.
    public static String genImgName(Integer userId) {
        return  "IMG-" + userId + System.currentTimeMillis() +  NumberUtil.generateRandomNumber(0, 10000,
                1)[0];
    }

    public static InputStream copyInputStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        byte[] data = outputStream.toByteArray();
        return new ByteArrayInputStream(data);
    }


}
