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


@WebServlet("/addApplication")
public class AddApplicationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public AddApplicationServlet() {
        super();
        
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Check Logged in user 
		
		HttpSession session = request.getSession(false);
		
		//Session nahi hai ya user login nahi  hai 
		if(session == null || session.getAttribute("userId") == null) {
			response.sendRedirect("login.html");
			return;
		}
		
		//Session se logged in user ki id 
		int userId = (Integer) session.getAttribute("userId");
		
		// Frontend form data 
		
		String companyName = request.getParameter("company_name");
		String jobRole = request.getParameter("job_role");
		String location = request.getParameter("location");
		String appliedDate = request.getParameter("applied_date");
		String status = request.getParameter("status");
		String jobLink = request.getParameter("job_link");
		String notes = request.getParameter("notes");
		
		
		
		
		response.setContentType("text/html");
		
		try {
			
			
			Connection con = DBConnection.getConnection();
			//Insert query
			
			String sql = "INSERT INTO applications" + "(user_id,company_name,job_role,location,"
			             + "applied_date, status, job_link, notes)"+"VALUES(?,?,?,?,?,?,?,?)";
			
			PreparedStatement ps = con.prepareStatement(sql);
			
			//set values
			
			ps.setInt(1, userId);
			ps.setString(2, companyName);
			ps.setString(3, jobRole);
			ps.setString(4, location);
			ps.setString(5, appliedDate);
			ps.setString(6, status);
			ps.setString(7, jobLink);
			ps.setString(8, notes);
			
			
			//7. Exceute Insert
			
			int rows = ps.executeUpdate();
			
			if(rows > 0) {
				//Application successfully added
				
				response.sendRedirect("dashboard");
			}
			else {
				PrintWriter out = response.getWriter();
				
				out.println("<h2>Application could not be added!</h2>");
				out.println("<a href='add-application.html'>Try Again</a>");
			}
			
			
			ps.close();
			con.close();
			
		}catch(Exception e) {
			e.printStackTrace();
			PrintWriter out = response.getWriter();
			out.println("<h2>Database Error</h2>");
			out.println("<p>"+e.getMessage()+"</p>");
			out.println("<a href='add-application.html'>Go Back</a>");
		}
		
	}

}

























