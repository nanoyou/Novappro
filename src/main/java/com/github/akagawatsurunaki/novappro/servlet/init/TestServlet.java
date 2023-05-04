package com.github.akagawatsurunaki.novappro.servlet.init;

import com.github.akagawatsurunaki.novappro.mapper.UserMapper;
import com.github.akagawatsurunaki.novappro.util.MyDb;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "TestServlet", value = "/TestServlet")
public class TestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        try(SqlSession session = MyDb.use().openSession()){
            // 测试用例: http://localhost:8080/Novappro_war_exploded/TestServlet
            var mapper = session.getMapper(UserMapper.class);
//
//            List<Integer> list = new ArrayList<>();
//            list.add(20210000);
//            list.add(20210001);
//            var s = mapper.selectByIds(list);
//            System.out.println("s = " + s);
//            System.out.println("这是一段测试");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {

    }
}
