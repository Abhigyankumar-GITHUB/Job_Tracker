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


@WebServlet("/updateApplication")
public class UpdateApplicationServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;


    public UpdateApplicationServlet() {
        super();
    }


    @Override
    protected void doPost(HttpServletRequest request,
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
        // 2. FORM SE DATA LENA
        // =========================================

        String idParameter =
                request.getParameter("id");

        String companyName =
                request.getParameter("companyName");

        String jobRole =
                request.getParameter("jobRole");

        String location =
                request.getParameter("location");

        String appliedDate =
                request.getParameter("appliedDate");

        String status =
                request.getParameter("status");

        String jobLink =
                request.getParameter("jobLink");

        String notes =
                request.getParameter("notes");


        response.setContentType("text/html;charset=UTF-8");


        // =========================================
        // 3. APPLICATION ID CHECK
        // =========================================

        if (idParameter == null ||
            idParameter.trim().isEmpty()) {

            PrintWriter out = response.getWriter();

            out.println("<h2>Application ID not found!</h2>");

            return;
        }


        int applicationId;


        try {

            applicationId =
                    Integer.parseInt(idParameter);

        }
        catch (NumberFormatException e) {

            PrintWriter out = response.getWriter();

            out.println("<h2>Invalid Application ID!</h2>");

            return;
        }


       


        // =========================================
        // 5. DATABASE CONNECTION
        // =========================================

        try {

        	Connection con = DBConnection.getConnection();
           


            // =========================================
            // 6. UPDATE QUERY
            // =========================================

            String sql =
                    "UPDATE applications SET "
                    + "company_name = ?, "
                    + "job_role = ?, "
                    + "location = ?, "
                    + "applied_date = ?, "
                    + "status = ?, "
                    + "job_link = ?, "
                    + "notes = ? "
                    + "WHERE id = ? AND user_id = ?";


            PreparedStatement ps =
                    con.prepareStatement(sql);


            // =========================================
            // 7. VALUES SET KARNA
            // =========================================

            ps.setString(1, companyName);

            ps.setString(2, jobRole);

            ps.setString(3, location);

            ps.setString(4, appliedDate);

            ps.setString(5, status);

            ps.setString(6, jobLink);

            ps.setString(7, notes);

            ps.setInt(8, applicationId);

            ps.setInt(9, userId);


            // =========================================
            // 8. UPDATE EXECUTE
            // =========================================

            int rowsUpdated =
                    ps.executeUpdate();


            // =========================================
            // 9. CHECK RESULT
            // =========================================

            if (rowsUpdated > 0) {

                // Update successful
                // Wapas View Applications page par

                response.sendRedirect("viewApplications");

            }
            else {

                PrintWriter out =
                        response.getWriter();

                out.println(
                    "<h2>Application could not be updated!</h2>"
                );

                out.println(
                    "<p>Application not found or "
                    + "you are not allowed to update it.</p>"
                );

                out.println(
                    "<a href='viewApplications'>"
                    + "Back to Applications</a>"
                );
            }


            // =========================================
            // 10. CLOSE RESOURCES
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
                + "Back to Applications</a>"
            );
        }

    }

}