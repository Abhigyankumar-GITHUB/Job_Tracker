package com.jobtracker.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import com.jobtracker.util.DBConnection;
import java.sql.PreparedStatement;


@WebServlet("/deleteApplication")
public class DeleteApplicationServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;


    public DeleteApplicationServlet() {
        super();
    }


    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {


        // =========================================
        // 1. CHECK USER SESSION
        // =========================================

        HttpSession session = request.getSession(false);


        if (session == null ||
            session.getAttribute("userId") == null) {

            response.sendRedirect("login.html");
            return;
        }


        // Logged-in user ki ID
        int userId =
                (Integer) session.getAttribute("userId");


        // =========================================
        // 2. APPLICATION ID GET KARNA
        // =========================================

        String idParameter =
                request.getParameter("id");


        response.setContentType("text/html;charset=UTF-8");


        // ID nahi mili
        if (idParameter == null ||
            idParameter.trim().isEmpty()) {

            PrintWriter out =
                    response.getWriter();

            out.println("<h2>Application ID not found!</h2>");

            return;
        }


        int applicationId;


        try {

            applicationId =
                    Integer.parseInt(idParameter);

        }
        catch (NumberFormatException e) {

            PrintWriter out =
                    response.getWriter();

            out.println("<h2>Invalid Application ID!</h2>");

            return;
        }


        // =========================================
        // 3. DATABASE DETAILS
        // =========================================

        


        // =========================================
        // 4. DATABASE CONNECTION
        // =========================================

        try {

            // MySQL Driver load
        	Connection con = DBConnection.getConnection();

            // =========================================
            // 5. DELETE QUERY
            // =========================================

            String sql =
                    "DELETE FROM applications "
                  + "WHERE id = ? AND user_id = ?";


            PreparedStatement ps =
                    con.prepareStatement(sql);


            // Application ID
            ps.setInt(1, applicationId);

            // Logged-in User ID
            ps.setInt(2, userId);


            // =========================================
            // 6. EXECUTE DELETE
            // =========================================

            int rowsDeleted =
                    ps.executeUpdate();


            // =========================================
            // 7. CHECK RESULT
            // =========================================

            if (rowsDeleted > 0) {

                // Successfully deleted

                response.sendRedirect("viewApplications");

            }
            else {

                PrintWriter out =
                        response.getWriter();

                out.println(
                    "<h2>Application could not be deleted!</h2>"
                );

                out.println(
                    "<p>Application not found or "
                    + "you are not allowed to delete it.</p>"
                );

                out.println(
                    "<a href='viewApplications'>"
                    + "Back to Applications"
                    + "</a>"
                );
            }


            // =========================================
            // 8. CLOSE RESOURCES
            // =========================================

            ps.close();

            con.close();


        }
        catch (Exception e) {

            e.printStackTrace();


            PrintWriter out =
                    response.getWriter();


            out.println("<h2>Database Error</h2>");

            out.println(
                "<p>" + e.getMessage() + "</p>"
            );

            out.println(
                "<a href='viewApplications'>"
                + "Back to Applications"
                + "</a>"
            );
        }

    }

}