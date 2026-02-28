
import java.io.*;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/ProjectImage")
public class ProjectImage extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter("pro-id");
        if (id == null || id.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing ID parameter");
            return;
        }

        String url = "jdbc:mysql://localhost:3306/devbloods_db";
        String username = "root";
        String password = "Gunaguku143@";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(url, username, password); PreparedStatement ps = conn.prepareStatement("SELECT image FROM projects WHERE p_id = ?")) {

                ps.setString(1,id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        Blob blob = rs.getBlob("image");
                        if (blob != null) {
                            response.setContentType("image/jpeg"); // change if your image type differs
                            try (InputStream inputStream = blob.getBinaryStream(); OutputStream outputStream = response.getOutputStream()) {

                                byte[] buffer = new byte[4096];
                                int bytesRead;
                                while ((bytesRead = inputStream.read(buffer)) != -1) {
                                    outputStream.write(buffer, 0, bytesRead);
                                }
                            }
                        } else {
                            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Image not found for ID: " + id);
                        }
                    } else {
                        response.sendError(HttpServletResponse.SC_NOT_FOUND, "Staff ID not found: " + id);
                    }
                }
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "JDBC Driver not found");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
        }
    }
}
