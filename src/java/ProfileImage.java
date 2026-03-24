
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.*;

@WebServlet("/ProfileImage")
public class ProfileImage extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String mail = request.getParameter("mail");

        if (mail == null || mail.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String url = "jdbc:mysql://localhost:3306/devbloods_db";
        String user = "root";
        String password = "Gunaguku143@";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);

            // ⚠️ Change table/column name based on your DB
            String sql = "SELECT image FROM registration WHERE mail=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, mail);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Blob blob = rs.getBlob("image");

                if (blob != null) {
                    byte[] imageBytes = blob.getBytes(1, (int) blob.length());

                    // ✅ Set image type
                    response.setContentType("image/jpeg"); // or image/png

                    OutputStream os = response.getOutputStream();
                    os.write(imageBytes);
                    os.flush();
                    os.close();
                } else {
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                }
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
