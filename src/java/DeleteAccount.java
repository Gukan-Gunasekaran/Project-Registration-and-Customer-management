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
 * @author malar
 */
@WebServlet(urlPatterns = {"/DeleteAccount"})
public class DeleteAccount extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url="jdbc:mysql://localhost:3306/devbloods_db";
        String userName= "root";
        String password="Gunaguku143@";
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        JSONObject json=new JSONObject();
        
        
        
        
try(PrintWriter out = response.getWriter()) {
    HttpSession session = request.getSession(false);
    
    if (session == null || session.getAttribute("mail") == null) {
        return;
    }
    
    String mail =(String)session.getAttribute("mail");
    String pass =request.getParameter("pass");

    if (pass == null || pass.trim().isEmpty()) {
        return;
    }

    Class.forName("com.mysql.cj.jdbc.Driver");
    
    try (Connection conn = DriverManager.getConnection(url, userName, password)){
        PreparedStatement ps = conn.prepareStatement("delete from registration where mail=? and password=?");
        ps.setString(1, mail);
        ps.setString(2, pass);
        
        int rows=ps.executeUpdate();
        conn.close();
               
        if (rows > 0) {
            session.removeAttribute("mail");
            session.invalidate();
            json.put("deletion",true);
            json.put("message", "Account deleted successfully.");
            out.println("Account deleted successfully.");
            
         }else{
            String passMsg="Incorrect password or account not found.";
            json.put("deletion",false);
            json.put("message", "Invalid action.");
         }
    }
} catch (Exception e) {
    json.put("deletion",false);
    json.put("deletion","server error"+e.getMessage());
    e.printStackTrace();
}
response.getWriter().print(json.toString());
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
