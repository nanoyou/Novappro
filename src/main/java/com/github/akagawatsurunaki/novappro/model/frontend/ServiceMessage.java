package com.github.akagawatsurunaki.novappro.model.frontend;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ServiceMessage {

    Level messageLevel;

    String message;

    public enum Level{
        SUCCESS,
        INFO,
        WARN,
        ERROR,
        FATAL
        ;
    }

}
