package com.github.akagawatsurunaki.novappro.servlet.init;

import com.github.akagawatsurunaki.novappro.enumeration.UserType;
import com.github.akagawatsurunaki.novappro.mapper.UserMapper;
import com.github.akagawatsurunaki.novappro.model.database.User;
import com.github.akagawatsurunaki.novappro.util.MyDb;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "InitServlet", value = "/InitServlet")
public class InitServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
//        try(SqlSession session = MyDb.use().openSession()){
//            var mapper = session.getMapper(UserMapper.class);
//            var insertUser = new User();
//            insertUser.setType(UserType.ADMIN);
//            insertUser.setUsername("针不错");
//            insertUser.setRawPassword("sdjkf83udnsdj!");
//
//            mapper.insert(insertUser);
//            session.commit();
//        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {

    }
}
