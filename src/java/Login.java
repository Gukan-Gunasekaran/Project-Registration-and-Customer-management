/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

import java.sql.*;
import org.json.JSONObject;
/**
 *
 * @author Gukan G;
 */
@WebServlet(urlPatterns = {"/login"})
public class Login extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String url="jdbc:mysql://localhost:3306/devbloods_db";
        String username="root";
        String password="Gunaguku143@";
        
        String mail = request.getParameter("mail");
        String pass = request.getParameter("password");
        
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");        
        response.setCharacterEncoding("UTF-8");
        JSONObject json=new JSONObject();
        try{
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            Connection conn=DriverManager.getConnection(url, username, password);
            
            PreparedStatement ps = conn.prepareStatement("SELECT mail,password from registration where mail = ? and password = ?");
            
            ps.setString(1, mail);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();
            PrintWriter out=response.getWriter();
            
            
            
            if(rs.next()){
                json.put("success", true);
                json.put("message","Login successfull");
                HttpSession session=request.getSession();           
                session.setAttribute("mail",mail);
               
                
            }else{
               json.put("success",false);
               json.put("message","invalid username or password");
            }
            
        }catch(Exception e){
            
            e.printStackTrace();
            json.put("success",false);
            json.put("message","server error"+e.getMessage());
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
