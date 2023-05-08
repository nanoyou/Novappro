package com.github.akagawatsurunaki.novappro.constant;

import lombok.AllArgsConstructor;

/**
 * Verify Code
 */
public final class VC {

    @AllArgsConstructor
    public enum Service{
        ERROR("未知错误"),
        OK("服务成功"),
        NO_SUCH_USER("该用户不存在"),
        ;

        public final String message;

    }
}
