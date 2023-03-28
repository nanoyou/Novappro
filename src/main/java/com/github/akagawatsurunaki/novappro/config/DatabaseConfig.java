package com.github.akagawatsurunaki.novappro.config;

import lombok.Getter;


public class DatabaseConfig {

    @Getter
    private static final String PASSWORD = "123456";

    @Getter
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/web_dev?serverTimezone=UTC&allowPublicKeyRetrieval=true";

    @Getter
    private static final String USER = "root";

}
