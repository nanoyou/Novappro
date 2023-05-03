package com.github.akagawatsurunaki.novappro.util;

import cn.hutool.core.io.resource.ResourceUtil;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class MyDb {

    private static SqlSessionFactory sqlSessionFactory;

    private static boolean isInited = false;

    public static SqlSessionFactory use(){
        if (!isInited){
            try {
                init();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        return sqlSessionFactory;
    }

    static void init() throws IOException {
        var resource = ResourceUtil.getResource("config/mybatis/mybatis-config.xml");
        InputStream inputStream = Resources.getResourceAsStream("config/mybatis/mybatis-config.xml");
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        isInited = true;
    }
}
