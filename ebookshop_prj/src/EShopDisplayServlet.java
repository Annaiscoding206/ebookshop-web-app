import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/display")
public class EShopDisplayServlet extends HttpServlet {

   public void doGet(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {

      response.setContentType("text/html");
      PrintWriter out = response.getWriter();

      try {

         Connection conn = DriverManager.getConnection(
               "jdbc:mysql://localhost:3306/ebookshop",
               "myuser",
               "annado");

         Statement stmt = conn.createStatement();

         String sqlStr = "SELECT DISTINCT author FROM books WHERE qty > 0";

         ResultSet rset = stmt.executeQuery(sqlStr);

         out.println("<html><body>");
         out.println("<h1>A&J Bookshop</h1>");

         out.println("<p>");
         out.println("<a href='eshoplogin.html'><button>Login</button></a>");
         out.println("<a href='eshopregister.html'><button>Register</button></a>");
         out.println("<a href='viewcart'><button>View Cart</button></a>");
         out.println("</p>");

         out.println("<form method='get' action='eshopquery'>");
         out.println("Choose an author:<br>");

         while (rset.next()) {

            String author = rset.getString("author");

            out.println("<input type='checkbox' name='author' value='"
                     + author + "'>" + author);
         }

         out.println("<br><br>");
         out.println("<input type='submit' value='Search'>");
         out.println("</form>");

         out.println("</body></html>");

         conn.close();

      } catch (SQLException e) {
         out.println("<h3>Database error</h3>");
         out.println("<p>" + e.getMessage() + "</p>");
      }
   }
}