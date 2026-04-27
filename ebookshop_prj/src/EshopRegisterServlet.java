import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/register")
public class EshopRegisterServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String custName = request.getParameter("cust_name");
        String custEmail = request.getParameter("cust_email");
        String custPhone = request.getParameter("cust_phone");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // validation 
        if (custName == null || custName.trim().isEmpty() ||
            custEmail == null || custEmail.trim().isEmpty() ||
            custPhone == null || custPhone.trim().isEmpty() ||
            username == null || username.trim().isEmpty() ||
            password == null || password.trim().isEmpty()) 
            {

                out.println("<h2>Registration Failed</h2>");
                out.println("<p>Please fill in all fields.</p>");
                out.println("<br><a href='eshopregister.html'><button>Back to Registration</button></a>");
                return;
            }

        try {
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/ebookshop",
                    "myuser", "annado");

            Statement stmt = conn.createStatement();

            String sqlStr = "INSERT INTO customers (cust_name, cust_email, cust_phone, username, password) VALUES ('"
                    + custName + "', '"
                    + custEmail + "', '"
                    + custPhone + "', '"
                    + username + "', '"
                    + password + "')";

            int count = stmt.executeUpdate(sqlStr);

            out.println("<h2>Registration Successful</h2>");
            out.println("<p>" + count + " customer registered.</p>");
            out.println("<br><br>");
            out.println("<a href='display'><button>Go Back to Bookshop</button></a>");
            conn.close();

        } catch (SQLException e) {
            out.println("<h3>Database error</h3>");
            out.println("<p>" + e.getMessage() + "</p>");
            e.printStackTrace();
        }
    }
}