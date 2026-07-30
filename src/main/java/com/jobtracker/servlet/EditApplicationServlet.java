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


@WebServlet("/editApplication")
public class EditApplicationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public EditApplicationServlet() {
        super();
       
    }

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		
		PrintWriter out = response.getWriter();
		
		//SESSION CHECK 
		
		HttpSession session = request.getSession(false);
		if(session == null || session.getAttribute("userId") == null) {
			response.sendRedirect("login.html");
			return;
		}
		
		int userId = (Integer) session.getAttribute("userId");
		
		//application id get karna 
		
		String idParameter = request.getParameter("id");
		if(idParameter == null) {
			out.println("<h2>Application ID not found!</h2>");
			return;
		}
		
		int applicationId;
		
		try {
			applicationId = Integer.parseInt(idParameter);
			
		}catch(NumberFormatException e) {
			out.println("<h2>Invalid application ID!</h2>");
			return ;
		}
		
		//Database details
		
		
		
		try {
			//Driver load
			
			Connection con = DBConnection.getConnection();
			//Application fecth
			
			String sql = "SELECT * FROM applications "
                    + "WHERE id = ? AND user_id = ?";
			
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setInt(1, applicationId);
			ps.setInt(2, userId);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				//database value
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

                String jobLink =
                        rs.getString("job_link");

                String notes =
                        rs.getString("notes");
                
                
                //Null values handle karna 
                
                if(location == null) {
                	location = "";
                }
                if (jobLink == null)
                    jobLink = "";

                if (notes == null)
                    notes = "";
                
                //html start
                
                out.println("<!DOCTYPE html>");
                out.println("<html lang='en'>");

                out.println("<head>");

                out.println("<meta charset='UTF-8'>");

                out.println(
                    "<meta name='viewport' "
                    + "content='width=device-width, initial-scale=1.0'>"
                );

                out.println(
                    "<title>Edit Application | Job Tracker</title>"
                );


                // CSS
                out.println(
                    "<link rel='stylesheet' href='"
                    + request.getContextPath()
                    + "/css/editApplication.css'>"
                );


                // Font Awesome
                out.println(
                    "<link rel='stylesheet' "
                    + "href='https://cdnjs.cloudflare.com/ajax/libs/"
                    + "font-awesome/6.5.2/css/all.min.css'>"
                );


                // Google Font
                out.println(
                    "<link href='https://fonts.googleapis.com/css2?"
                    + "family=Inter:wght@400;500;600;700&display=swap' "
                    + "rel='stylesheet'>"
                );


                out.println("</head>");

                out.println("<body>");


                out.println("<div class='dashboard-container'>");


                // =========================================
                // SIDEBAR
                // =========================================

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
                    "<a href='dashboard' class='nav-link'>"
                );

                out.println(
                    "<i class='fa-solid fa-house'></i>"
                );

                out.println("<span>Dashboard</span>");

                out.println("</a>");


                out.println(
                    "<a href='add-application.html' "
                    + "class='nav-link'>"
                );

                out.println(
                    "<i class='fa-regular fa-circle-plus'></i>"
                );

                out.println("<span>Add Application</span>");

                out.println("</a>");


                out.println(
                    "<a href='viewApplications' "
                    + "class='nav-link active'>"
                );

                out.println(
                    "<i class='fa-solid fa-briefcase'></i>"
                );

                out.println(
                    "<span>View Applications</span>"
                );

                out.println("</a>");


                out.println("</nav>");


                // Logout

                out.println("<div class='logout'>");

                out.println("<a href='logout'>");

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

                out.println("<main class='main-content'>");


                // HEADER

                out.println("<header class='top-header'>");


                out.println("<div class='menu-icon'>");

                out.println(
                    "<i class='fa-solid fa-bars'></i>"
                );

                out.println("</div>");


                out.println("<div class='user-profile'>");


                out.println("<div class='profile-icon'>");

                out.println(
                    "<i class='fa-regular fa-user'></i>"
                );

                out.println("</div>");


                out.println("<div class='user-info'>");

                out.println("<h4>Welcome, User</h4>");

                out.println(
                    "<p>"
                    + session.getAttribute("userEmail")
                    + "</p>"
                );

                out.println("</div>");


                out.println(
                    "<i class='fa-solid fa-chevron-down "
                    + "dropdown-icon'></i>"
                );


                out.println("</div>");

                out.println("</header>");


                // =========================================
                // CONTENT AREA
                // =========================================

                out.println("<section class='content-area'>");


                out.println("<div class='page-heading'>");

                out.println("<h1>Edit Application</h1>");


                out.println("<div class='breadcrumb'>");

                out.println(
                    "<i class='fa-solid fa-house'></i>"
                );

                out.println("<span>›</span>");

                out.println(
                    "<a href='viewApplications'>"
                    + "View Applications</a>"
                );

                out.println("<span>›</span>");

                out.println("<p>Edit Application</p>");

                out.println("</div>");

                out.println("</div>");


                // =========================================
                // APPLICATION CARD
                // =========================================

                out.println(
                    "<div class='application-card'>"
                );


                out.println("<div class='card-heading'>");


                out.println("<div class='heading-icon'>");

                out.println(
                    "<i class='fa-solid fa-briefcase'></i>"
                );

                out.println("</div>");


                out.println("<div>");

                out.println(
                    "<h2>Application Details</h2>"
                );

                out.println(
                    "<p>Update the details of your "
                    + "job application</p>"
                );

                out.println("</div>");


                out.println("</div>");


                // =========================================
                // FORM
                // =========================================

                out.println(
                    "<form action='updateApplication' "
                    + "method='POST'>"
                );


                // Hidden ID

                out.println(
                    "<input type='hidden' name='id' value='"
                    + applicationId
                    + "'>"
                );


                out.println("<div class='form-grid'>");


                // COMPANY NAME

                out.println("<div class='form-group'>");

                out.println(
                    "<label>Company Name <span>*</span></label>"
                );

                out.println(
                    "<input type='text' "
                    + "name='companyName' value='"
                    + companyName
                    + "' required>"
                );

                out.println("</div>");


                // JOB ROLE

                out.println("<div class='form-group'>");

                out.println(
                    "<label>Job Role <span>*</span></label>"
                );

                out.println(
                    "<input type='text' "
                    + "name='jobRole' value='"
                    + jobRole
                    + "' required>"
                );

                out.println("</div>");


                // LOCATION

                out.println("<div class='form-group'>");

                out.println(
                    "<label>Location <span>*</span></label>"
                );

                out.println(
                    "<input type='text' "
                    + "name='location' value='"
                    + location
                    + "' required>"
                );

                out.println("</div>");


                // APPLIED DATE

                out.println("<div class='form-group'>");

                out.println(
                    "<label>Applied Date <span>*</span></label>"
                );

                out.println(
                    "<input type='date' "
                    + "name='appliedDate' value='"
                    + appliedDate
                    + "' required>"
                );

                out.println("</div>");


                // STATUS

                out.println("<div class='form-group'>");

                out.println(
                    "<label>Status <span>*</span></label>"
                );


                out.println(
                    "<select name='status' required>"
                );


                out.println(
                    "<option value='Applied' "
                    + (status.equals("Applied")
                       ? "selected" : "")
                    + ">Applied</option>"
                );


                out.println(
                    "<option value='Interview' "
                    + (status.equals("Interview")
                       ? "selected" : "")
                    + ">Interview</option>"
                );


                out.println(
                    "<option value='Selected' "
                    + (status.equals("Selected")
                       ? "selected" : "")
                    + ">Selected</option>"
                );


                out.println(
                    "<option value='Rejected' "
                    + (status.equals("Rejected")
                       ? "selected" : "")
                    + ">Rejected</option>"
                );


                out.println("</select>");

                out.println("</div>");


                // JOB LINK

                out.println("<div class='form-group'>");

                out.println("<label>Job Link</label>");

                out.println(
                    "<input type='url' "
                    + "name='jobLink' value='"
                    + jobLink
                    + "'>"
                );

                out.println("</div>");


                out.println("</div>");


                // NOTES

                out.println(
                    "<div class='form-group notes-group'>"
                );

                out.println("<label>Notes</label>");

                out.println(
                    "<textarea name='notes' rows='5'>"
                    + notes
                    + "</textarea>"
                );

                out.println("</div>");


                // =========================================
                // BUTTONS
                // =========================================

                out.println("<div class='form-actions'>");


                out.println(
                    "<button type='submit' "
                    + "class='update-btn'>"
                );

                out.println(
                    "<i class='fa-solid fa-check'></i>"
                );

                out.println("Update Application");

                out.println("</button>");


                out.println(
                    "<a href='viewApplications' "
                    + "class='cancel-btn'>"
                );

                out.println(
                    "<i class='fa-solid fa-xmark'></i>"
                );

                out.println("Cancel");

                out.println("</a>");


                out.println("</div>");


                out.println("</form>");


                out.println("</div>");

                out.println("</section>");

                out.println("</main>");

                out.println("</div>");

                out.println("</body>");

                out.println("</html>");
			}
			else {

	                out.println(
	                    "<h2>Application not found!</h2>"
	                );
	        }
			rs.close();
            ps.close();
            con.close();
			
			
			
		}catch(Exception e) {
			 e.printStackTrace();

	            out.println("<h2>Database Error</h2>");

	            out.println(
	                    "<p>" + e.getMessage() + "</p>"
	            );
		}
		
	}

}



























