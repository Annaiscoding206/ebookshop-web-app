import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/login")
public class EshopLoginServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/ebookshop",
                    "myuser",
                    "annado");

            Statement stmt = conn.createStatement();

            // First: check username only
            String sqlStr = "SELECT * FROM customers WHERE username='" + username + "'";
            ResultSet rset = stmt.executeQuery(sqlStr);

            if (rset.next()) {
                // Username exists
                String dbPassword = rset.getString("password");

                if (dbPassword.equals(password)) {
                    out.println("<h2>Login Successful</h2>");
                    out.println("<p>Welcome, " + rset.getString("cust_name") + "!</p>");
                    out.println("<p><a href='display'>Go to Book Shop</a></p>");
                } else {
                    out.println("<h2>Login Failed</h2>");
                    out.println("<p>Incorrect password.</p>");
                    out.println("<p><a href='forgotpassword.html'>Renew your password</a></p>");
                }

            } else {
                // Username not found
                out.println("<h2>Login Failed</h2>");
                out.println("<p>No username found. Register your account.</p>");
                out.println("<p><a href='eshopregister.html'>Go to Register</a></p>");
            }

            conn.close();

        } catch (SQLException e) {
            out.println("<h3>Database error</h3>");
            out.println("<p>" + e.getMessage() + "</p>");
            e.printStackTrace();
        }
    }
}