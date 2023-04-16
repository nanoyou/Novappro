package com.github.akagawatsurunaki.novappro.util;

import cn.hutool.core.util.NumberUtil;

public final class IdUtil {

    public static String genFlowNo(Integer userId) {
        return "APFL" + userId + "" + System.currentTimeMillis() + "" + NumberUtil.generateRandomNumber(0, 10000, 1)[0];
    }
}
