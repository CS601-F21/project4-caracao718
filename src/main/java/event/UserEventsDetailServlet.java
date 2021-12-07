package event;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.http.HttpStatus;
import server.JDBCServer;
import server.TicketServerConstants;
import user.JDBCUsers;
import utilities.DBCPDataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserEventsDetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String sessionId = req.getSession(false).getId();

        ArrayList<Integer> eventIds;
        ArrayList<ResultSet> details = new ArrayList<>();
        int userId = -1;


        try {
            Connection con = DBCPDataSource.getConnection();
            userId = JDBCServer.getUserIdGivenSession(con, sessionId);
            eventIds = JDBCEvent.getEventIdsGivenSession(con, sessionId);
            for (int id : eventIds) {
                details.add(JDBCEvent.getEventGivenEventId(con, id));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (userId == -1) {
            resp.setStatus(HttpStatus.INTERNAL_SERVER_ERROR_500);
            resp.getWriter().println(EventServletConstant.ERROR_PAGE);
        }

        resp.setStatus(HttpStatus.OK_200);
        resp.setHeader("Connection", "close");
        resp.getWriter().println(EventServletConstant.PAGE_HEADER);
        resp.getWriter().println("<h1>Here are your upcoming event details</h1>");
        // display all the events
        for (ResultSet event : details) {
            try {
                while (event.next()) {
                    try {
                        resp.getWriter().println("<h2> " + event.getString("title") + " </h2>");
                        resp.getWriter().println("<p> " + event.getString("description") + " </p>");
                        resp.getWriter().println("<p> Event Date and Time: " + event.getDate("event_date") + " </p>");
                        // number of ticket purchased
                        int numTickets = 0;
                        try {
                            Connection con = DBCPDataSource.getConnection();
                            numTickets = JDBCEvent.getNumTicketsGivenEventId(con, event.getInt("id"), userId);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        resp.getWriter().println("<h3> Number of Tickets Purchased: " + numTickets + " </h3>");
                        resp.getWriter().println("<p><a href=\"/transfer?event_id=" + event.getInt("id") + "\">Transfer Ticket(s)</a>");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        resp.getWriter().println();
        resp.getWriter().println("<p><a href=\"/login\">My Home Page</a>");
        resp.getWriter().println(EventServletConstant.PAGE_FOOTER);
    }
}
