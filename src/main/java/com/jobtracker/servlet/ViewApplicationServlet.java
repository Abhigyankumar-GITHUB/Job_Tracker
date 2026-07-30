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

@WebServlet("/viewApplications")
public class ViewApplicationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public ViewApplicationServlet() {
        super();
        
    }

    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Check user session
    	
    	HttpSession session = request.getSession(false);
    	
    	if(session == null || session.getAttribute("userId") == null) {
    		response.sendRedirect("login.html");
    		return;
    	}
    	
    	//LoginServlet me save ki hui user id 
    	
    	int userId = (Integer) session.getAttribute("userId");
    	
    	//Email bhi session se le rhe hai 
    	
    	String userEmail = (String) session.getAttribute("userEmail");
    	
    	
    	
    	response.setContentType("text/html;charset=UTF-8");
    	
    	PrintWriter out = response.getWriter();
    	
    	try {
    		
    		Connection con = DBConnection.getConnection();
    		//Fetch application 
    		
    		String sql = "Select * From applications where user_id = ? Order by applied_date Desc";
    		
    		PreparedStatement ps = con.prepareStatement(sql);
    		
    		ps.setInt(1, userId);
    		
    		ResultSet rs = ps.executeQuery();
    		
    		// HTML START
    		
    		
    		 out.println("<!DOCTYPE html>");

             out.println("<html lang='en'>");


             out.println("<head>");

             out.println("<meta charset='UTF-8'>");

             out.println(
                     "<meta name='viewport' " +
                     "content='width=device-width, initial-scale=1.0'>"
             );


             out.println(
                     "<title>View Applications | Job Tracker</title>"
             );


             // CSS
             out.println(
                     "<link rel='stylesheet' " +
                     "href='css/view-applications.css'>"
             );


             // Font Awesome
             out.println(
                     "<link rel='stylesheet' " +
                     "href='https://cdnjs.cloudflare.com/ajax/libs/" +
                     "font-awesome/6.5.2/css/all.min.css'>"
             );


             // Google Font
             out.println(
                     "<link href='https://fonts.googleapis.com/css2?" +
                     "family=Inter:wght@400;500;600;700&display=swap' " +
                     "rel='stylesheet'>"
             );


             out.println("</head>");


             out.println("<body>");
             
             
             // ==========================================
             // 7. DASHBOARD CONTAINER
             // ==========================================

             out.println("<div class='dashboard-container'>");


             // ==========================================
             // SIDEBAR
             // ==========================================

             out.println("<aside class='sidebar'>");


             out.println("<div class='logo'>");

             out.println("<div class='logo-icon'>");

             out.println(
                     "<i class='fa-solid fa-briefcase'></i>"
             );

             out.println("</div>");

             out.println("<h2>Job Tracker</h2>");

             out.println("</div>");



             // Navigation

             out.println("<nav class='nav-menu'>");


             out.println(
                     "<a href='dashboard' " +
                     "class='nav-link'>"
             );

             out.println(
                     "<i class='fa-solid fa-house'></i>"
             );

             out.println("<span>Dashboard</span>");

             out.println("</a>");



             out.println(
                     "<a href='add-application.html' " +
                     "class='nav-link'>"
             );

             out.println(
                     "<i class='fa-regular fa-circle-plus'></i>"
             );

             out.println("<span>Add Application</span>");

             out.println("</a>");



             out.println(
                     "<a href='viewApplications' " +
                     "class='nav-link active'>"
             );

             out.println(
                     "<i class='fa-solid fa-briefcase'></i>"
             );

             out.println("<span>View Applications</span>");

             out.println("</a>");


             out.println("</nav>");



             // Logout

             out.println("<div class='logout'>");

             out.println("<a href='logout'>");

             out.println(
                     "<i class='fa-solid " +
                     "fa-arrow-right-from-bracket'></i>"
             );

             out.println("<span>Logout</span>");

             out.println("</a>");

             out.println("</div>");


             out.println("</aside>");



             // ==========================================
             // MAIN CONTENT
             // ==========================================

             out.println("<main class='main-content'>");



             // ==========================================
             // TOP HEADER
             // ==========================================

             out.println("<header class='top-header'>");


             out.println("<div class='page-title'>");

             out.println("<h1>View Applications</h1>");

             out.println(
                     "<p>Track and manage all your " +
                     "job applications</p>"
             );

             out.println("</div>");



             // User Profile

             out.println("<div class='user-profile'>");


             out.println("<div class='profile-icon'>");

             out.println(
                     "<i class='fa-regular fa-user'></i>"
             );

             out.println("</div>");



             out.println("<div class='user-info'>");

             out.println("<h4>Welcome, User</h4>");

             out.println(
                     "<p>" +
                     (userEmail != null ? userEmail : "") +
                     "</p>"
             );

             out.println("</div>");



             out.println(
                     "<i class='fa-solid fa-chevron-down " +
                     "dropdown-icon'></i>"
             );


             out.println("</div>");


             out.println("</header>");



             // ==========================================
             // CONTENT
             // ==========================================

             out.println("<section class='content-area'>");


             out.println("<div class='applications-card'>");



             // ==========================================
             // CARD HEADER
             // ==========================================

             out.println("<div class='card-header'>");


             out.println("<div class='card-title-section'>");


             out.println("<div class='application-icon'>");

             out.println(
                     "<i class='fa-solid fa-briefcase'></i>"
             );

             out.println("</div>");



             out.println("<div>");

             out.println("<h2>My Applications</h2>");

             out.println(
                     "<p>Here's a list of all the jobs " +
                     "you have applied for</p>"
             );

             out.println("</div>");


             out.println("</div>");



             // Search / Filter

             out.println("<div class='card-actions'>");


             out.println("<div class='search-box'>");

             out.println(
                     "<i class='fa-solid fa-magnifying-glass'></i>"
             );

             out.println(
                     "<input type='text' " +
                     "placeholder='Search applications...'>"
             );

             out.println("</div>");



             out.println("<button class='filter-btn'>");

             out.println(
                     "<i class='fa-solid fa-filter'></i>"
             );

             out.println("Filter");

             out.println("</button>");


             out.println("</div>");


             out.println("</div>");



             // ==========================================
             // TABLE
             // ==========================================

             out.println("<div class='table-wrapper'>");

             out.println("<table>");


             // Table Head

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



             // ==========================================
             // DATABASE DATA
             // ==========================================

             out.println("<tbody>");


             boolean hasApplications = false;

             int count = 0;


             while (rs.next()) {


                 hasApplications = true;

                 count++;


                 int applicationId =
                         rs.getInt("id");


                 String companyName =
                         rs.getString("company_name");


                 String jobRole =
                         rs.getString("job_role");


                 String location =
                         rs.getString("location");


                 String appliedDate =
                         rs.getString("applied_date");


                 String status =
                         rs.getString("status");



                 // ======================================
                 // TABLE ROW
                 // ======================================

                 out.println("<tr>");



                 // COMPANY

                 out.println("<td>");

                 out.println("<div class='company'>");


                 out.println(
                         "<div class='company-logo blue-logo'>"
                 );


                 // Company ka first letter
                 if (companyName != null &&
                     !companyName.isEmpty()) {

                     out.println(
                             companyName.substring(0, 1)
                                     .toUpperCase()
                     );

                 } else {

                     out.println("C");
                 }


                 out.println("</div>");


                 out.println(
                         "<span>" + companyName + "</span>"
                 );


                 out.println("</div>");

                 out.println("</td>");



                 // JOB ROLE

                 out.println(
                         "<td>" + jobRole + "</td>"
                 );



                 // LOCATION

                 out.println("<td>");

                 if (location != null &&
                     !location.trim().isEmpty()) {

                     out.println(location);

                 } else {

                     out.println("-");
                 }

                 out.println("</td>");



                 // APPLIED DATE

                 out.println(
                         "<td>" + appliedDate + "</td>"
                 );



                 // ======================================
                 // STATUS
                 // ======================================

                 String statusClass = "applied";


                 if (status != null) {

                     if (status.equalsIgnoreCase("Interview")) {

                         statusClass = "interview";

                     }

                     else if (
                             status.equalsIgnoreCase("Selected")
                     ) {

                         statusClass = "selected";

                     }

                     else if (
                             status.equalsIgnoreCase("Rejected")
                     ) {

                         statusClass = "rejected";

                     }
                 }


                 out.println("<td>");

                 out.println(
                         "<span class='status " +
                         statusClass +
                         "'>"
                         + status +
                         "</span>"
                 );

                 out.println("</td>");



                 // ======================================
                 // ACTIONS
                 // ======================================

                 out.println("<td>");


                 out.println("<div class='action-buttons'>");



                 // EDIT

                 out.println(
                         "<a href='editApplication?id=" +
                         applicationId +
                         "' class='edit-btn'>"
                 );

                 out.println(
                         "<i class='fa-solid fa-pen'></i>"
                 );

                 out.println("</a>");



                 // DELETE

                 out.println(
                         "<a href='deleteApplication?id=" +
                         applicationId +
                         "' class='delete-btn'>"
                 );

                 out.println(
                         "<i class='fa-solid fa-trash'></i>"
                 );

                 out.println("</a>");


                 out.println("</div>");


                 out.println("</td>");


                 out.println("</tr>");
             }



             // ==========================================
             // NO APPLICATION
             // ==========================================

             if (!hasApplications) {


                 out.println("<tr>");


                 out.println(
                         "<td colspan='6' " +
                         "style='text-align:center;" +
                         "padding:50px;'>"
                 );


                 out.println(
                         "No applications found. "
                 );


                 out.println(
                         "<a href='add-application.html'>" +
                         "Add your first application" +
                         "</a>"
                 );


                 out.println("</td>");


                 out.println("</tr>");
             }


             out.println("</tbody>");


             out.println("</table>");

             out.println("</div>");



             // ==========================================
             // TABLE FOOTER
             // ==========================================

             out.println("<div class='table-footer'>");


             out.println(
                     "<p>Showing " +
                     count +
                     " application(s)</p>"
             );


             out.println("<div class='pagination'>");


             out.println(
                     "<button class='page-arrow'>" +
                     "<i class='fa-solid fa-chevron-left'></i>" +
                     "</button>"
             );


             out.println(
                     "<button class='page-number active-page'>" +
                     "1" +
                     "</button>"
             );


             out.println(
                     "<button class='page-arrow'>" +
                     "<i class='fa-solid fa-chevron-right'></i>" +
                     "</button>"
             );


             out.println("</div>");


             out.println("</div>");



             out.println("</div>");

             out.println("</section>");

             out.println("</main>");

             out.println("</div>");

             out.println("</body>");

             out.println("</html>");



             // ==========================================
             // CLOSE DATABASE RESOURCES
             // ==========================================

             rs.close();

             ps.close();

             con.close();


             
    		
    	}catch(Exception e) {
    		e.printStackTrace();
    		out.println("<h2>Database Error</h2>");


            out.println(
                    "<p>" +
                    e.getMessage() +
                    "</p>"
            );
    	}
    	
	}

}
























