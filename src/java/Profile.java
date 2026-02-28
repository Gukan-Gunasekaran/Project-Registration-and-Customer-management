/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.*;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * 
 * @author malar
 */
@WebServlet(urlPatterns = {"/Profile"})
public class Profile extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
us
     * @throws java.io.IOException     */
  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
      
    HttpSession session=request.getSession(false);
    if(session == null || session.getAttribute("mail")==null){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
    
    String url="jdbc:mysql://localhost:3306/devbloods_db";
    String userName="root";
    String password="Gunaguku143@";
    
    String mail=(String)session.getAttribute("mail");
    response.setContentType("application/json");
    
    try{
        
        Connection conn=DriverManager.getConnection(url,userName,password);
        PreparedStatement ps=conn.prepareStatement("select name,age,mail,gender from registration where mail= ? ");
        ps.setString(1,mail);
    
        ResultSet rs=ps.executeQuery();
           
        JSONArray userList = new JSONArray();

        if(rs.next()) {
            
            
            JSONObject user = new JSONObject();
            user.put("name", rs.getString("name"));
            user.put("age",rs.getString("age"));
            user.put("mail", rs.getString("mail"));
            user.put("gender", rs.getString("gender"));
            
            userList.put(user);
            PrintWriter out = response.getWriter();
            out.print(userList.toString());
            out.flush();
            conn.close();
        }      
        
        else{
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
       }

    }catch(Exception e){
        e.printStackTrace();
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