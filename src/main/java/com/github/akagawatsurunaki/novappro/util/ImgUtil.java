package com.github.akagawatsurunaki.novappro.util;

import cn.hutool.core.util.NumberUtil;

public final class ImgUtil {
    // A.T.
    public static String genImgName(Integer userId) {
        return  "IMG-" + userId + System.currentTimeMillis() +  NumberUtil.generateRandomNumber(0, 10000,
                1)[0];
    }

}
