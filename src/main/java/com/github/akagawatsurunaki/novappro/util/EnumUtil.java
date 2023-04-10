package com.github.akagawatsurunaki.novappro.util;

import com.github.akagawatsurunaki.novappro.annotation.ChineseFieldName;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

public final class EnumUtil {
    public static <T extends Enum<T>> Pair<String, String> parseVerifyCode(T verifyCode) {
        try {
            ChineseFieldName annotation = verifyCode.getClass().getField(verifyCode.name()).getAnnotation(ChineseFieldName.class);
            return new ImmutablePair<>(
                    annotation.value(),
                    annotation.description()
            );

        } catch (NoSuchFieldException e) {
            return new ImmutablePair<>("","");
        }
    }
}
