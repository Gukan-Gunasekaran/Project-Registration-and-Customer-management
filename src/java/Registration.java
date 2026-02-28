/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import java.sql.*;

import org.json.JSONObject;

@WebServlet(urlPatterns = {"/Registration"})
public class Registration extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/devbloods_db";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "Gunaguku143@";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Set content type
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        // Retrieve request parameters from HTML
        String name = request.getParameter("u-name");
        String mail = request.getParameter("mail");
        String pass = request.getParameter("pass");
        String gender = request.getParameter("gender");
        int age=Integer.parseInt(request.getParameter("age"));
        

        
        JSONObject json = new JSONObject();
        try {
            // Load JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Use try-with-resources for DB operations
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {

                // Insert user into database
                String insertSQL = "INSERT INTO registration(name, age, mail, gender,password) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement ps = conn.prepareStatement(insertSQL)) {
                    ps.setString(1,name);
                    ps.setInt(2, age);
                    ps.setString(3, mail);
                    ps.setString(4, gender);
                    ps.setString(5, pass);
                    ps.executeUpdate();
                }

                // Retrieve all users from database
                json.put("resgister",true);
                json.put("message","registration sucessfull !");
                out.println("registration sucessfull !");
                           
            }

            // Return the list of registered users as JSON
            response.setStatus(HttpServletResponse.SC_OK);

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
            out.print("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @Override
    public String getServletInfo() {
        return "Handles registration and returns list of users in JSON format.";
    }
}
