import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/forgotpassword")
public class ForgotPasswordServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String username = request.getParameter("username");
        String email = request.getParameter("cust_email");
        String newPassword = request.getParameter("new_password");

        try {

            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/ebookshop",
                    "myuser",
                    "annado");

            Statement stmt = conn.createStatement();

            // check if user exists
            String checkSql = "SELECT * FROM customers WHERE username='"
                    + username + "' AND cust_email='" + email + "'";

            ResultSet rset = stmt.executeQuery(checkSql);

            if (rset.next()) {

                String updateSql = "UPDATE customers SET password='"
                        + newPassword + "' WHERE username='"
                        + username + "'";

                stmt.executeUpdate(updateSql);

                out.println("<h2>Password successfully updated!</h2>");
                out.println("<a href='eshoplogin.html'>Back to Login</a>");

            } else {

                out.println("<h2>Account not found.</h2>");
                out.println("<p>Username and email do not match.</p>");
                out.println("<a href='forgotpassword.html'>Try Again</a>");

            }

            conn.close();

        } catch (SQLException e) {

            out.println("<h3>Database Error</h3>");
            out.println(e.getMessage());
        }
    }
}