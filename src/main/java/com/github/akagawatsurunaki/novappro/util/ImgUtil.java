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
}
