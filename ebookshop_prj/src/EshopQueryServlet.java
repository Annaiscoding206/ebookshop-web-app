// To save as "ebookshop\WEB-INF\classes\QueryMultiValueServlet.java".
import java.io.*;
import java.sql.*;
import jakarta.servlet.*;            // Tomcat 10 (Jakarta EE 9)
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
//import javax.servlet.*;            // Tomcat 9 (Java EE 8 / Jakarta EE 8)
//import javax.servlet.http.*;
//import javax.servlet.annotation.*;

@WebServlet("/eshopquery")   // Configure the request URL for this servlet (Tomcat 7/Servlet 3.0 upwards)
public class EshopQueryServlet extends HttpServlet {

   // The doGet() runs once per HTTP GET request to this servlet.
   @Override
   public void doGet(HttpServletRequest request, HttpServletResponse response)
               throws ServletException, IOException {
      // Set the MIME type for the response message
      response.setContentType("text/html");
      // Get a output writer to write the response message into the network socket
      PrintWriter out = response.getWriter();
      // Print an HTML page as the output of the query
      out.println("<!DOCTYPE html>");
      out.println("<html>");
      out.println("<head><title>Query Response</title></head>");
      out.println("<body>");

      try (
         // Step 1: Allocate a database 'Connection' object
         Connection conn = DriverManager.getConnection(
               "jdbc:mysql://localhost:3306/ebookshop?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
               "myuser", "annado");   // For MySQL
               // The format is: "jdbc:mysql://hostname:port/databaseName", "username", "password"

         // Step 2: Allocate a 'Statement' object in the Connection
         Statement stmt = conn.createStatement();
      ) {
         // Step 3: Execute a SQL SELECT query
         // === Form the SQL command - BEGIN ===
         String[] authors = request.getParameterValues("author");  // Returns an array of Strings
         // Add the following lines after 
         // String[] authors = request.getParameterValues("author");
         if (authors == null) {
            out.println("<h2>No author selected. Please go back to select author(s)</h2><body></html>");
            return; // Exit doGet()
         } 
         String sqlStr = "SELECT * FROM books WHERE author IN (";
         for (int i = 0; i < authors.length; ++i) {
            if (i < authors.length - 1) {
               sqlStr += "'" + authors[i] + "', ";  // need a commas
            } else {
               sqlStr += "'" + authors[i] + "'";    // no commas
            }
         }
         sqlStr += ") ORDER BY author ASC, title ASC";
         // === Form the SQL command - END ===

         out.println("<h3>Thank you for your query.</h3>");
         ResultSet rset = stmt.executeQuery(sqlStr);  // Send the query to the server


         // Step 4: Process the query result
        int count = 0;

        // Start form
        out.println("<form method='get' action='addtocart'>");

        // Start table
        out.println("<table border='1' cellpadding='6'>");

        // Header row
        out.println("<tr>");
        out.println("<th>&nbsp;</th>"); 
        out.println("<th>AUTHOR</th>");
        out.println("<th>TITLE</th>");
        out.println("<th>PRICE</th>");
        out.println("<th>STOCK</th>");
        out.println("</tr>");

        // Data rows
        while (rset.next()) 
        {
         int qty = rset.getInt("qty");

         out.println("<tr>");

         if (qty > 0) {
         out.println("<td><input type='checkbox' name='id' value='"
            + rset.getString("id") + "' /></td>");
         } else {
         out.println("<td></td>");
         }

         out.println("<td>" + rset.getString("author") + "</td>");
         //to add in the image 
        out.println("<td><img src='images/" + rset.getString("id") + ".png' width='60'> " 
        + rset.getString("title") + "</td>");
         out.println("<td>$" + rset.getString("price") + "</td>");

         if (qty > 0) {
         out.println("<td>" + qty + " left</td>");
         } else {
         out.println("<td>Out of Stock</td>");
         }

         out.println("</tr>");
         count++;
         }

        // End table
        out.println("</table>");

        // Order button
        out.println("<p><input type='submit' value='Add to Cart' /></p>");    
        out.println("</form>");
        out.println("<br><br>");
        out.println("<a href='display'><button>Back</button></a>");
      }
        catch (SQLException e) {
        out.println("<h3>Database error</h3>");
        out.println("<p>" + e.getMessage() + "</p>");
        e.printStackTrace();
        }
        // Step 5: Close conn and stmt - Done automatically by try-with-resources (JDK 7)
 
        out.println("</body></html>");
        out.close();
   }
}