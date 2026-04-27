import java.io.*;
import java.util.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/addtocart")
public class AddToCartServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String[] ids = request.getParameterValues("id");

        HttpSession session = request.getSession();
        ArrayList<String> cart = (ArrayList<String>) session.getAttribute("cart");

        if (cart == null) {
            cart = new ArrayList<String>();
        }

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        if (ids != null && ids.length > 0) {
            // Add selected books to cart
            for (String id : ids) {
                cart.add(id);
            }
            session.setAttribute("cart", cart);

            out.println("<h2>Books added to cart.</h2>");
            out.println("<p><a href='display'>Continue Shopping</a></p>");
            out.println("<p><a href='viewcart'>View Cart</a></p>");
        } else {
            // No books selected
            out.println("<h2>No books selected!</h2>");
            out.println("<p><a href='display'>Go back to book list</a></p>");
        }
    }
}