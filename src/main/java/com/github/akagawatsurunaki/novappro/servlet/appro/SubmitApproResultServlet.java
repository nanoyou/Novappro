package com.github.akagawatsurunaki.novappro.servlet.appro;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "SubmitApproResultServlet", value = "/submit_appro_ret")
public class SubmitApproResultServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {

    }
}
