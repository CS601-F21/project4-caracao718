package event;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.eclipse.jetty.http.HttpStatus;
import server.JDBCServer;
import utilities.DBCPDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

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

        String sessionId = req.getSession(false).getId();

        resp.setStatus(HttpStatus.OK_200);
        resp.getWriter().println(CreateEventConstants.GET_CREATE_EVENT_PAGE);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sessionId = req.getSession(false).getId();

        String title = req.getParameter("title");
        String description = req.getParameter("description");
        Date startDate = Date.valueOf(req.getParameter("date"));
        int numOfTickets = Integer.parseInt(req.getParameter("numticket"));
        double price = Double.parseDouble(req.getParameter("price"));
        String location = req.getParameter("location");
//        String image = req.getParameter("event_image");

        Part imagePart = req.getPart("event_image");
        InputStream inputStream = imagePart.getInputStream();

        // add the input to DB
        try {
            Connection con = DBCPDataSource.getConnection();
            int userId = JDBCServer.getUserIdGivenSession(con, sessionId);

            if (inputStream != null) {
                JDBCEvent.executeInsertEventWithImage(con, userId, title, description, startDate, numOfTickets, price, location, inputStream);
            } else {
                JDBCEvent.executeInsertEvent(con, userId, title, description, startDate, numOfTickets, price, location);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            assert inputStream != null;
            inputStream.close();
        }
        // print a line saying successfully created
        resp.setStatus(HttpStatus.OK_200);
        resp.getWriter().println(EventServletConstant.PAGE_HEADER);
        resp.getWriter().println("<h3> Event successfully created. </h3>");
        resp.getWriter().println("<p><a href=\"/login\">My Home Page</a>");
        resp.getWriter().println(EventServletConstant.PAGE_FOOTER);
    }
}
