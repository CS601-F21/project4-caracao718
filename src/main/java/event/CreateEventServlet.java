package event;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.http.HttpStatus;
import server.JDBCServer;
import utilities.DBCPDataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

/**
 * Handle requests to /create
 * To create event
 */
public class CreateEventServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setStatus(HttpStatus.OK_200);
        resp.getWriter().println(EventConstants.GET_CREATE_EVENT_PAGE);
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

        try {
            Connection con = DBCPDataSource.getConnection();
            int userId = JDBCServer.getUserIdGivenSession(con, sessionId);

            JDBCEvent.executeInsertEvent(con, userId, title, description, startDate, numOfTickets, price, location);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        resp.setStatus(HttpStatus.OK_200);
        resp.getWriter().println(EventServletConstant.PAGE_HEADER);
        resp.getWriter().println("<h3> Event successfully created. </h3>");
        resp.getWriter().println("<p><a href=\"/login\">My Home Page</a>");
        resp.getWriter().println(EventServletConstant.PAGE_FOOTER);
    }
}
