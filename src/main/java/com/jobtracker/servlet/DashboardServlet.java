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
import java.sql.ResultSet;


@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;


    public DashboardServlet() {
        super();
    }


    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {


        // =========================================
        // 1. SESSION CHECK
        // =========================================

        HttpSession session = request.getSession(false);

        if (session == null ||
            session.getAttribute("userId") == null) {

            response.sendRedirect("login.html");
            return;
        }


        int userId =
                (Integer) session.getAttribute("userId");

        String userEmail =
                (String) session.getAttribute("userEmail");


        // =========================================
        // 2. DATABASE DETAILS
        // =========================================

        

        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();


        try {

            // =========================================
            // 3. MYSQL DRIVER LOAD
            // =========================================

        	Connection con = DBConnection.getConnection();


            // =========================================
            // 5. APPLICATION COUNTS
            // =========================================

            int totalApplications = 0;
            int appliedCount = 0;
            int interviewCount = 0;
            int selectedCount = 0;
            int rejectedCount = 0;


            String countSql =
                    "SELECT "
                  + "COUNT(*) AS total, "
                  + "SUM(CASE WHEN status = 'Applied' "
                  + "THEN 1 ELSE 0 END) AS applied, "
                  + "SUM(CASE WHEN status = 'Interview' "
                  + "THEN 1 ELSE 0 END) AS interview, "
                  + "SUM(CASE WHEN status = 'Selected' "
                  + "THEN 1 ELSE 0 END) AS selected, "
                  + "SUM(CASE WHEN status = 'Rejected' "
                  + "THEN 1 ELSE 0 END) AS rejected "
                  + "FROM applications "
                  + "WHERE user_id = ?";


            PreparedStatement countPs =
                    con.prepareStatement(countSql);

            countPs.setInt(1, userId);


            ResultSet countRs =
                    countPs.executeQuery();


            if (countRs.next()) {

                totalApplications =
                        countRs.getInt("total");

                appliedCount =
                        countRs.getInt("applied");

                interviewCount =
                        countRs.getInt("interview");

                selectedCount =
                        countRs.getInt("selected");

                rejectedCount =
                        countRs.getInt("rejected");
            }


            countRs.close();
            countPs.close();


            // =========================================
            // 6. RECENT APPLICATIONS QUERY
            // =========================================

            String recentSql =
                    "SELECT id, company_name, job_role, "
                  + "location, applied_date, status "
                  + "FROM applications "
                  + "WHERE user_id = ? "
                  + "ORDER BY applied_date DESC, id DESC "
                  + "LIMIT 5";


            PreparedStatement recentPs =
                    con.prepareStatement(recentSql);

            recentPs.setInt(1, userId);


            ResultSet recentRs =
                    recentPs.executeQuery();


            // =========================================
            // HTML START
            // =========================================

            out.println("<!DOCTYPE html>");

            out.println("<html lang='en'>");

            out.println("<head>");

            out.println("<meta charset='UTF-8'>");

            out.println(
                "<meta name='viewport' "
                + "content='width=device-width, initial-scale=1.0'>"
            );

            out.println(
                "<title>Dashboard | Job Tracker</title>"
            );


            // EXACT SAME CSS
            out.println(
                "<link rel='stylesheet' "
                + "href='css/dashboard.css'>"
            );


            // Font Awesome
            out.println(
                "<link rel='stylesheet' "
                + "href='https://cdnjs.cloudflare.com/ajax/libs/"
                + "font-awesome/6.5.1/css/all.min.css'>"
            );


            out.println("</head>");

            out.println("<body>");


            // =========================================
            // SIDEBAR
            // =========================================

            out.println("<aside class='sidebar'>");


            // LOGO

            out.println("<div class='logo'>");

            out.println("<div class='logo-icon'>");

            out.println(
                "<i class='fa-solid fa-briefcase'></i>"
            );

            out.println("</div>");

            out.println("<h2>Job Tracker</h2>");

            out.println("</div>");


            // =========================================
            // SIDEBAR MENU
            // =========================================

            out.println("<nav class='sidebar-menu'>");


            // DASHBOARD

            out.println(
                "<a href='dashboard' "
                + "class='menu-link active'>"
            );

            out.println(
                "<i class='fa-solid fa-house'></i>"
            );

            out.println("<span>Dashboard</span>");

            out.println("</a>");


            // ADD APPLICATION

            out.println(
                "<a href='add-application.html' "
                + "class='menu-link'>"
            );

            out.println(
                "<i class='fa-solid fa-circle-plus'></i>"
            );

            out.println(
                "<span>Add Application</span>"
            );

            out.println("</a>");


            // VIEW APPLICATIONS

            out.println(
                "<a href='viewApplications' "
                + "class='menu-link'>"
            );

            out.println(
                "<i class='fa-solid fa-briefcase'></i>"
            );

            out.println(
                "<span>View Applications</span>"
            );

            out.println("</a>");


            out.println("</nav>");


            // =========================================
            // LOGOUT
            // =========================================

            out.println(
                "<div class='logout-section'>"
            );

            out.println(
                "<a href='logout' "
                + "class='logout-link'>"
            );

            out.println(
                "<i class='fa-solid "
                + "fa-arrow-right-from-bracket'></i>"
            );

            out.println("<span>Logout</span>");

            out.println("</a>");

            out.println("</div>");


            out.println("</aside>");


            // =========================================
            // MAIN CONTENT
            // =========================================

            out.println(
                "<main class='main-content'>"
            );


            // =========================================
            // TOP HEADER
            // =========================================

            out.println(
                "<header class='top-header'>"
            );


            // HEADER LEFT

            out.println(
                "<div class='header-title'>"
            );

            out.println("<h1>Dashboard</h1>");

            out.println(
                "<p>Track and manage your "
                + "job applications</p>"
            );

            out.println("</div>");


            // =========================================
            // USER PROFILE
            // =========================================

            out.println("<div class='profile'>");


            out.println(
                "<div class='profile-icon'>"
            );

            out.println(
                "<i class='fa-solid fa-user'></i>"
            );

            out.println("</div>");


            out.println(
                "<div class='profile-details'>"
            );


            out.println(
                "<span class='profile-name'>"
                + "Welcome, User"
                + "</span>"
            );


            out.println(
                "<span class='profile-email'>"
                + (userEmail != null ? userEmail : "")
                + "</span>"
            );


            out.println("</div>");


            out.println(
                "<i class='fa-solid fa-chevron-down "
                + "profile-arrow'></i>"
            );


            out.println("</div>");


            out.println("</header>");


            // =========================================
            // DASHBOARD BODY
            // =========================================

            out.println(
                "<div class='dashboard-body'>"
            );


            // =========================================
            // WELCOME CARD
            // =========================================

            out.println(
                "<section class='welcome-card'>"
            );


            out.println(
                "<div class='welcome-content'>"
            );

            out.println(
                "<h2>Welcome back, User! 👋</h2>"
            );

            out.println(
                "<p>Keep track of your applications "
                + "and stay focused on your next "
                + "opportunity.</p>"
            );

            out.println("</div>");


            // ADD NEW APPLICATION BUTTON

            out.println(
                "<a href='add-application.html' "
                + "class='add-new-btn'>"
            );

            out.println(
                "<i class='fa-solid fa-plus'></i>"
            );

            out.println(
                "Add New Application"
            );

            out.println("</a>");


            out.println("</section>");


            // =========================================
            // STATISTICS
            // =========================================

            out.println(
                "<section class='stats-grid'>"
            );


            // =========================================
            // TOTAL APPLICATIONS
            // =========================================

            out.println("<div class='stat-card'>");

            out.println(
                "<div class='stat-icon total-icon'>"
            );

            out.println(
                "<i class='fa-solid fa-briefcase'></i>"
            );

            out.println("</div>");


            out.println(
                "<div class='stat-content'>"
            );

            out.println(
                "<h2>" + totalApplications + "</h2>"
            );

            out.println(
                "<p>Total Applications</p>"
            );

            out.println("</div>");

            out.println("</div>");


            // =========================================
            // APPLIED
            // =========================================

            out.println("<div class='stat-card'>");

            out.println(
                "<div class='stat-icon applied-icon'>"
            );

            out.println(
                "<i class='fa-solid fa-paper-plane'></i>"
            );

            out.println("</div>");


            out.println(
                "<div class='stat-content'>"
            );

            out.println(
                "<h2>" + appliedCount + "</h2>"
            );

            out.println("<p>Applied</p>");

            out.println("</div>");

            out.println("</div>");


            // =========================================
            // INTERVIEW
            // =========================================

            out.println("<div class='stat-card'>");

            out.println(
                "<div class='stat-icon interview-icon'>"
            );

            out.println(
                "<i class='fa-solid fa-comment-dots'></i>"
            );

            out.println("</div>");


            out.println(
                "<div class='stat-content'>"
            );

            out.println(
                "<h2>" + interviewCount + "</h2>"
            );

            out.println("<p>Interview</p>");

            out.println("</div>");

            out.println("</div>");


            // =========================================
            // SELECTED
            // =========================================

            out.println("<div class='stat-card'>");

            out.println(
                "<div class='stat-icon selected-icon'>"
            );

            out.println(
                "<i class='fa-solid fa-check'></i>"
            );

            out.println("</div>");


            out.println(
                "<div class='stat-content'>"
            );

            out.println(
                "<h2>" + selectedCount + "</h2>"
            );

            out.println("<p>Selected</p>");

            out.println("</div>");

            out.println("</div>");


            // =========================================
            // REJECTED
            // =========================================

            out.println("<div class='stat-card'>");

            out.println(
                "<div class='stat-icon rejected-icon'>"
            );

            out.println(
                "<i class='fa-solid fa-xmark'></i>"
            );

            out.println("</div>");


            out.println(
                "<div class='stat-content'>"
            );

            out.println(
                "<h2>" + rejectedCount + "</h2>"
            );

            out.println("<p>Rejected</p>");

            out.println("</div>");

            out.println("</div>");


            out.println("</section>");


            // =========================================
            // RECENT APPLICATIONS
            // =========================================

            out.println(
                "<section class='applications-card'>"
            );


            // APPLICATION HEADER

            out.println(
                "<div class='applications-header'>"
            );


            out.println("<div>");

            out.println(
                "<h2>Recent Applications</h2>"
            );

            out.println(
                "<p>Your recently added "
                + "job applications</p>"
            );

            out.println("</div>");


            out.println(
                "<a href='viewApplications' "
                + "class='view-all-link'>"
            );

            out.println(
                "View All Applications "
            );

            out.println(
                "<i class='fa-solid "
                + "fa-arrow-right'></i>"
            );

            out.println("</a>");


            out.println("</div>");


            // =========================================
            // TABLE
            // =========================================

            out.println(
                "<div class='table-wrapper'>"
            );

            out.println("<table>");


            out.println("<thead>");

            out.println("<tr>");

            out.println("<th>Company</th>");

            out.println("<th>Job Role</th>");

            out.println("<th>Location</th>");

            out.println("<th>Applied Date</th>");

            out.println("<th>Status</th>");

            out.println("<th>Actions</th>");

            out.println("</tr>");

            out.println("</thead>");


            out.println("<tbody>");


            boolean hasApplications = false;


            // =========================================
            // DATABASE APPLICATIONS
            // =========================================

            while (recentRs.next()) {

                hasApplications = true;


                int applicationId =
                        recentRs.getInt("id");

                String companyName =
                        recentRs.getString("company_name");

                String jobRole =
                        recentRs.getString("job_role");

                String location =
                        recentRs.getString("location");

                String appliedDate =
                        recentRs.getString("applied_date");

                String status =
                        recentRs.getString("status");


                out.println("<tr>");


                // COMPANY

                out.println(
                    "<td>" + companyName + "</td>"
                );


                // JOB ROLE

                out.println(
                    "<td>" + jobRole + "</td>"
                );


                // LOCATION

                out.println(
                    "<td>"
                    + (location != null ? location : "-")
                    + "</td>"
                );


                // APPLIED DATE

                out.println(
                    "<td>" + appliedDate + "</td>"
                );


                // STATUS

                out.println(
                    "<td>" + status + "</td>"
                );


                // ACTIONS

                out.println("<td>");


                // EDIT

                out.println(
                    "<a href='editApplication?id="
                    + applicationId
                    + "' title='Edit'>"
                );

                out.println(
                    "<i class='fa-solid fa-pen'></i>"
                );

                out.println("</a>");


                out.println("&nbsp;&nbsp;");


                // DELETE

                out.println(
                    "<a href='deleteApplication?id="
                    + applicationId
                    + "' "
                    + "title='Delete' "
                    + "onclick=\"return confirm("
                    + "'Are you sure you want to "
                    + "delete this application?');\">"
                );

                out.println(
                    "<i class='fa-solid fa-trash'></i>"
                );

                out.println("</a>");


                out.println("</td>");


                out.println("</tr>");
            }


            // =========================================
            // EMPTY STATE
            // =========================================

            if (!hasApplications) {

                out.println(
                    "<tr class='empty-row'>"
                );

                out.println(
                    "<td colspan='6'>"
                );


                out.println(
                    "<div class='empty-applications'>"
                );


                out.println(
                    "<div class='empty-icon'>"
                );

                out.println(
                    "<i class='fa-solid "
                    + "fa-briefcase'></i>"
                );

                out.println("</div>");


                out.println(
                    "<h3>No applications yet</h3>"
                );


                out.println(
                    "<p>Start tracking your job "
                    + "applications by adding your "
                    + "first application.</p>"
                );


                out.println(
                    "<a href='add-application.html' "
                    + "class='empty-add-btn'>"
                );

                out.println(
                    "<i class='fa-solid fa-plus'></i>"
                );

                out.println(
                    "Add Your First Application"
                );

                out.println("</a>");


                out.println("</div>");

                out.println("</td>");

                out.println("</tr>");
            }


            out.println("</tbody>");

            out.println("</table>");

            out.println("</div>");


            out.println("</section>");


            out.println("</div>");

            out.println("</main>");

            out.println("</body>");

            out.println("</html>");


            // =========================================
            // CLOSE DATABASE RESOURCES
            // =========================================

            recentRs.close();

            recentPs.close();

            con.close();


        }
        catch (Exception e) {

            e.printStackTrace();

            out.println("<h2>Database Error</h2>");

            out.println(
                "<p>" + e.getMessage() + "</p>"
            );
        }

    }

}