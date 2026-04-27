/*import java.io.*;
import java.sql.*;
import java.util.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession();
        ArrayList<String> cart = (ArrayList<String>) session.getAttribute("cart");

        String custName = request.getParameter("cust_name");
        String custEmail = request.getParameter("cust_email");
        String custPhone = request.getParameter("cust_phone");

        if (custName == null || custName.trim().isEmpty()
            || custEmail == null || custEmail.trim().isEmpty()
            || custPhone == null || custPhone.trim().isEmpty()) 
        {

            out.println("<h2>Checkout Failed</h2>");
            out.println("<p>Please enter your name, email, and phone before checkout.</p>");
            out.println("<p><a href='viewcart'>Go back to cart</a></p>");
            return;
        }

        if (cart == null || cart.size() == 0) {
            out.println("<p>Your cart is empty.</p>");
            return;
        }

        try {
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/ebookshop",
                    "myuser", "annado");

            Statement stmt = conn.createStatement();

            for (int i = 0; i < cart.size(); i++) {
                String id = cart.get(i);

                String sqlStr = "UPDATE books SET qty = qty - 1 WHERE id = " + id;
                stmt.executeUpdate(sqlStr);

                sqlStr = "INSERT INTO order_records (id, qty_ordered, cust_name, cust_email, cust_phone) VALUES ("
                        + id + ", 1, '"
                        + custName + "', '"
                        + custEmail + "', '"
                        + custPhone + "')";
                stmt.executeUpdate(sqlStr);
            }

            session.removeAttribute("cart");

            out.println("<h2>Checkout successful!</h2>");
            out.println("<p>Your order has been placed.</p>");
            out.println("<p><a href='display'>Back to Shop</a></p>");

            conn.close();

        } catch (SQLException e) {
            out.println("<h3>Database error</h3>");
            out.println("<p>" + e.getMessage() + "</p>");
        }
    }
}
*/
import java.io.*;
import java.sql.*;
import java.util.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession();
        ArrayList<String> cart = (ArrayList<String>) session.getAttribute("cart");

        String custName = request.getParameter("cust_name");
        String custEmail = request.getParameter("cust_email");
        String custPhone = request.getParameter("cust_phone");

        if (cart == null || cart.size() == 0) {
            out.println("<h2>Your cart is empty.</h2>");
            out.println("<p><a href='display'>Back to Bookshop</a></p>");
            return;
        }

        if (custName == null || custName.trim().isEmpty()
                || custEmail == null || custEmail.trim().isEmpty()
                || custPhone == null || custPhone.trim().isEmpty()) {

            out.println("<h2>Checkout Failed</h2>");
            out.println("<p>Please enter your name, email, and phone before checkout.</p>");
            out.println("<p><a href='viewcart'>Go back to cart</a></p>");
            return;
        }

        try {
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/ebookshop",
                    "myuser",
                    "annado");

            Statement stmt = conn.createStatement();

            // Count how many times each book appears in cart
            LinkedHashMap<String, Integer> cartCount = new LinkedHashMap<>();
            for (String id : cart) {
                if (cartCount.containsKey(id)) {
                    cartCount.put(id, cartCount.get(id) + 1);
                } else {
                    cartCount.put(id, 1);
                }
            }

            boolean hasError = false;

            // First pass: check stock for every book
            for (Map.Entry<String, Integer> entry : cartCount.entrySet()) {
                String id = entry.getKey();
                int qtyWanted = entry.getValue();

                String checkSql = "SELECT title, qty FROM books WHERE id = " + id;
                ResultSet rs = stmt.executeQuery(checkSql);

                if (rs.next()) {
                    String title = rs.getString("title");
                    int stock = rs.getInt("qty");

                    if (stock < qtyWanted) {
                        out.println("<h2>Checkout Failed</h2>");
                        out.println("<p>Not enough stock for: " + title + "</p>");
                        out.println("<p>You want " + qtyWanted + ", but only " + stock + " left.</p>");
                        out.println("<p><a href='viewcart'>Go back to cart</a></p>");
                        hasError = true;
                        break;
                    }
                } else {
                    out.println("<h2>Checkout Failed</h2>");
                    out.println("<p>Book with ID " + id + " was not found.</p>");
                    out.println("<p><a href='viewcart'>Go back to cart</a></p>");
                    hasError = true;
                    break;
                }
            }

            if (!hasError) {
                // Second pass: update stock and insert order records
                for (Map.Entry<String, Integer> entry : cartCount.entrySet()) {
                    String id = entry.getKey();
                    int qtyWanted = entry.getValue();

                    String updateSql = "UPDATE books SET qty = qty - " + qtyWanted
                            + " WHERE id = " + id;
                    stmt.executeUpdate(updateSql);

                    String insertSql = "INSERT INTO order_records "
                            + "(id, qty_ordered, cust_name, cust_email, cust_phone) VALUES ("
                            + id + ", "
                            + qtyWanted + ", '"
                            + custName + "', '"
                            + custEmail + "', '"
                            + custPhone + "')";

                    stmt.executeUpdate(insertSql);
                }

                // Clear cart after successful checkout
                session.removeAttribute("cart");

                out.println("<h2>Checkout Successful!</h2>");
                out.println("<p>Thank you, " + custName + ". Your order has been placed.</p>");
                out.println("<p><a href='display'>Back to Bookshop</a></p>");
            }

            conn.close();

        } catch (SQLException e) {
            out.println("<h2>Database Error</h2>");
            out.println("<p>" + e.getMessage() + "</p>");
            e.printStackTrace();
        }
    }
}