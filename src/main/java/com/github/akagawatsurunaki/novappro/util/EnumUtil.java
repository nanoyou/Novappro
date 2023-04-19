package com.github.akagawatsurunaki.novappro.util;

import com.github.akagawatsurunaki.novappro.annotation.ZhField;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

public final class EnumUtil {
    public static <T extends Enum<T>> Pair<String, String> parseVerifyCode(T verifyCode) {
        try {
            ZhField annotation = verifyCode.getClass().getField(verifyCode.name()).getAnnotation(ZhField.class);
            return new ImmutablePair<>(
                    annotation.value(),
                    annotation.desc()
            );

        } catch (NoSuchFieldException e) {
            return new ImmutablePair<>("","");
        }
    }
}
