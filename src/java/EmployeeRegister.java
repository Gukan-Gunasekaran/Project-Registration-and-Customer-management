/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.io.InputStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.Part;
import javax.servlet.annotation.MultipartConfig;

import java.sql.*;
import org.json.JSONObject;

/**
 *
 * @author malar
 */
@WebServlet(urlPatterns = {"/EmployeeRegister"})
@MultipartConfig
public class EmployeeRegister extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = "jdbc:mysql://localhost:3306/devbloods_db";
        String username = "root";
        String password = "Gunaguku143@";

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Part imgFile = request.getPart("image");
        String name = request.getParameter("e-name");
        String phone = request.getParameter("phone");
        String experience = request.getParameter("exp");
        String job = request.getParameter("job");
        String gender = request.getParameter("gender");
        JSONObject json = new JSONObject();
        
        //img
        
        InputStream image=imgFile.getInputStream();

        try (PrintWriter out = response.getWriter()) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, username, password);

            String insertSQL = "INSERT INTO staffs VALUES (default, ?, ?, ?, ?, ?,?);";
            PreparedStatement ps = conn.prepareStatement(insertSQL);
            ps.setString(1, name);
            ps.setString(2, phone);
            ps.setString(3, experience);
            ps.setString(4, job);
            ps.setString(5, gender);
            ps.setBlob(6,image);

            int result=ps.executeUpdate();
            
            if(result>0){
                json.put("action", true);
                json.put("message", "Inserted successfully");
            }
         out.print(json.toString());   
        } catch (Exception e) {
           try(PrintWriter out=response.getWriter()){
               json.put("action",false);
               json.put("message",e.getMessage());
               out.print(json.toString());
           }
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
