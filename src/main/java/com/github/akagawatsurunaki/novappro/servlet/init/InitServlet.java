package com.github.akagawatsurunaki.novappro.servlet.init;

import com.github.akagawatsurunaki.novappro.enumeration.UserType;
import com.github.akagawatsurunaki.novappro.mapper.ApprovalAuthorityMapper;
import com.github.akagawatsurunaki.novappro.mapper.CourseMapper;
import com.github.akagawatsurunaki.novappro.mapper.UserMapper;
import com.github.akagawatsurunaki.novappro.model.database.ApprovalAuthority;
import com.github.akagawatsurunaki.novappro.model.database.User;
import com.github.akagawatsurunaki.novappro.model.database.course.Course;
import com.github.akagawatsurunaki.novappro.util.MyDb;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "InitServlet", value = "/InitServlet")
public class InitServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        try(SqlSession session = MyDb.use().openSession()){
            // 测试用例: http://localhost:8080/Novappro_war_exploded/InitServlet
//            var mapper = session.getMapper(ApprovalAuthorityMapper.class);
//            List<ApprovalAuthority> approvalAuthorities = mapper.selectByUserId(20210004);
//            approvalAuthorities.forEach(System.out::println);
//            System.out.println("这是一段测试");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {

    }
}
