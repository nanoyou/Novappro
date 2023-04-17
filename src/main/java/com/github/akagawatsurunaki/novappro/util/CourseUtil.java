package com.github.akagawatsurunaki.novappro.util;

import cn.hutool.json.JSONUtil;
import lombok.NonNull;

import java.util.List;

public final class CourseUtil {

    public static List<String> getCourseCodes(@NonNull String courseIdsStr) {
        return JSONUtil.toList(courseIdsStr, String.class);
    }

    public static String toCourseCodesStr(@NonNull List<String> courseCodes) {
        return JSONUtil.toJsonStr(courseCodes);
    }

}
