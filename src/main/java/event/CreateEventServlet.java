package event;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class CreateEventServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // shows the page with several text boxes
        // 1. event title
        // 2. description (if there's VIP and student price tickets, describe it here)
        // 3. event date
        // 4. total number of tickets = tickets avaiable
        // 5. normal price
        // 6. VIP price (can be null)
        // 7. student price (can be null)
        // 8. event image (can be null)
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // add the input to DB
        // print a line saying successfully created

        // a button to go back to homepage
    }
}
