import java.io.*;
import java.sql.*;
import java.util.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/viewcart")
public class ViewCartServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession();
        ArrayList<String> cart = (ArrayList<String>) session.getAttribute("cart");

        out.println("<h1>Your Shopping Cart</h1>");

        if (cart == null || cart.isEmpty()) {
            out.println("<p>Your cart is empty.</p>");
            out.println("<p><a href='display'>Continue Shopping</a></p>");
            return;
        }

        try {
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/ebookshop",
                    "myuser", "annado");

            Statement stmt = conn.createStatement();

            // Count quantity for each book
            Map<String, Integer> bookCounts = new LinkedHashMap<>();
            for (String id : cart) {
                bookCounts.put(id, bookCounts.getOrDefault(id, 0) + 1);
            }

            out.println("<table border='1'>");
            out.println("<tr><th>Book ID</th><th>Author</th><th>Title</th><th>Price</th><th>Qty</th><th>Subtotal</th><th>Action</th></tr>");

            double totalPrice = 0;

            for (Map.Entry<String, Integer> entry : bookCounts.entrySet()) {
                String id = entry.getKey();
                int qty = entry.getValue();

                String sqlStr = "SELECT * FROM books WHERE id = " + id;
                ResultSet rset = stmt.executeQuery(sqlStr);

                if (rset.next()) {
                    double price = rset.getDouble("price");
                    double subtotal = price * qty;
                    totalPrice += subtotal;

                    out.println("<tr>");
                    out.println("<td>" + id + "</td>");
                    out.println("<td>" + rset.getString("author") + "</td>");
                    out.println("<td>" + rset.getString("title") + "</td>");
                    out.println("<td>$" + price + "</td>");
                    out.println("<td>" + qty + "</td>");
                    out.println("<td>$" + subtotal + "</td>");
                    // Remove one copy button
                    out.println("<td>");
                    out.println("<form method='post' action='viewcart'>");
                    out.println("<input type='hidden' name='removeId' value='" + id + "'/>");
                    out.println("<input type='submit' value='Remove one' />");
                    out.println("</form>");
                    out.println("</td>");
                    out.println("</tr>");
                }
            }

            // Total price row
            out.println("<tr>");
            out.println("<td colspan='5'><strong>Total Price</strong></td>");
            out.println("<td colspan='2'><strong>$" + totalPrice + "</strong></td>");
            out.println("</tr>");
            out.println("</table>");

            // Checkout form
            out.println("<h3>Checkout</h3>");
            out.println("<form method='get' action='checkout'>");
            out.println("<p>Enter your Name: <input type='text' name='cust_name' required /></p>");
            out.println("<p>Enter your Email: <input type='email' name='cust_email' required /></p>");
            out.println("<p>Enter your Phone: <input type='text' name='cust_phone' required /></p>");
            out.println("<p><input type='submit' value='Checkout' /></p>");
            out.println("</form>");

            // Clear Cart form
            out.println("<form method='post' action='viewcart'>");
            out.println("<input type='submit' name='clearCart' value='Clear Cart' />");
            out.println("</form>");

            out.println("<p><a href='display'>Continue Shopping</a></p>");

            conn.close();

        } catch (SQLException e) {
            out.println("<h3>Database error</h3>");
            out.println("<p>" + e.getMessage() + "</p>");
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        ArrayList<String> cart = (ArrayList<String>) session.getAttribute("cart");

        if (request.getParameter("clearCart") != null) {
            // Remove all items
            session.removeAttribute("cart");
        } else if (request.getParameter("removeId") != null) {
            // Remove ONE copy of the selected book
            String removeId = request.getParameter("removeId");
            if (cart != null && cart.contains(removeId)) {
                cart.remove(removeId); // removes first occurrence
                session.setAttribute("cart", cart);
            }
        }

        // Redirect back to cart page
        response.sendRedirect("viewcart");
    }
}