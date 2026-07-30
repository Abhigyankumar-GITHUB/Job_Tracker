package com.jobtracker.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;


@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;


    public LogoutServlet() {
        super();
    }


    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {


        // =========================================
        // 1. CURRENT SESSION GET KARNA
        // =========================================

        HttpSession session = request.getSession(false);


        // =========================================
        // 2. SESSION INVALIDATE KARNA
        // =========================================

        if (session != null) {

            session.invalidate();
        }


        // =========================================
        // 3. LOGIN PAGE PAR REDIRECT
        // =========================================

        response.sendRedirect("login.html");

    }

}