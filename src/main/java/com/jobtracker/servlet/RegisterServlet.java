package com.jobtracker.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import com.jobtracker.util.DBConnection;
import java.sql.PreparedStatement;


@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;


    public RegisterServlet() {
        super();
    }


    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Register form se data lena

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");


        // 2. Password and confirmPassword check

        if (!password.equals(confirmPassword)) {

            response.getWriter().println("Passwords do not match!");
            return;
        }


        // 3. Database details



        try {
        	
        	Connection con = DBConnection.getConnection();
 

            // 6. User database me insert

            String sql =
                    "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";


            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);


            int rows = ps.executeUpdate();


            // 7. Registration successful

            if (rows > 0) {

                response.sendRedirect("login.html");

            } else {

                response.getWriter().println("Registration failed!");
            }


            ps.close();
            con.close();


        } catch (Exception e) {

            e.printStackTrace();

            response.getWriter().println(
                    "Database Error: " + e.getMessage()
            );
        }
    }
}