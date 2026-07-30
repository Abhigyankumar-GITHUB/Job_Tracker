package com.jobtracker.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import jakarta.servlet.http.HttpSession;
import com.jobtracker.util.DBConnection;


@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public LoginServlet() {
        super();
        
    }

	
	


	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Frontend se email aur password lena
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		//Database details
		
		
		
		response.setContentType("text/html");
		
		try {
			//MySQL driver load
			//Class.forName("com.mysql.cj.jdbc.Driver");
			
			//Database connection
			//Connection con = DriverManager.getConnection(url, username, dbpassword);
			
			Connection con = DBConnection.getConnection();
			
			
			//User check
			String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
			
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setString(1, email);
			ps.setString(2, password);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {

			    // Logged-in user ki ID database se lena
			    int userId = rs.getInt("id");

			    // Session create karna
			    HttpSession session = request.getSession();

			    // User ID session me store karna
			    session.setAttribute("userId", userId);

			    // Email bhi session me store kar dete hain
			    session.setAttribute("userEmail", email);

			    response.sendRedirect("dashboard");
			}
			else {
				//Invalid email
				PrintWriter out = response.getWriter();
				
				out.println("<h2>Invalid email or password!</h2>");
				out.println("<a href='login.html'>Try Again</a>");
			}
			
			rs.close();
			ps.close();
			con.close();
			
			
		}catch(Exception e) {
			e.printStackTrace();
			
			PrintWriter out = response.getWriter();
			out.println("<h2>Database Error</h2>");
			out.println("<p>"+e.getMessage()+"</p>");
		}
		
	}

}





















