# Job Tracker

Job Tracker is a Java-based web application that allows users to manage and track their job applications in one place.

Users can register, log in, add job applications, update their application details, track application status, and delete applications.

## Features

- User Registration
- User Login and Logout
- Session Management
- Add Job Applications
- View All Applications
- Edit Application Details
- Update Application Status
- Delete Applications
- Dynamic Dashboard
- Track application status:
  - Applied
  - Interview
  - Selected
  - Rejected
- User-specific application data

## Technologies Used

### Backend
- Java
- Jakarta Servlet
- JDBC

### Frontend
- HTML5
- CSS3
- Font Awesome

### Database
- MySQL

### Server
- Apache Tomcat 10

### Development Tools
- Eclipse IDE
- MySQL Workbench
- Git
- GitHub

## Project Structure

```text
JobTracker/
│
├── src/main/java/
│   └── com/jobtracker/
│       ├── servlet/
│       │   ├── RegisterServlet.java
│       │   ├── LoginServlet.java
│       │   ├── DashboardServlet.java
│       │   ├── AddApplicationServlet.java
│       │   ├── ViewApplicationServlet.java
│       │   ├── EditApplicationServlet.java
│       │   ├── UpdateApplicationServlet.java
│       │   ├── DeleteApplicationServlet.java
│       │   └── LogoutServlet.java
│       │
│       └── util/
│           └── DBConnection.java
│
├── src/main/webapp/
│   ├── css/
│   ├── images/
│   ├── WEB-INF/
│   │   └── web.xml
│   ├── login.html
│   ├── register.html
│   └── add-application.html
│
├── .gitignore
└── README.md