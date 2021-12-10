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
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Handle requests to /purchase
 * To purchase event tickets
 */
public class PurchaseTicketServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String[] requestURI = req.getQueryString().split("=", 2);
        int eventId = Integer.parseInt(requestURI[1]);


        ResultSet event = null;

        try {
            Connection con = DBCPDataSource.getConnection();
            event = JDBCEvent.getEventGivenEventId(con, eventId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        int ticketsAvailable;
        // display information about the event
        resp.setStatus(HttpStatus.OK_200);
        resp.getWriter().println(EventConstants.PAGE_HEADER);
        try {
            if (event.next()) {
                resp.getWriter().print("<h2> " + event.getString("title") + " </h2>");
                resp.getWriter().print("<h3> Event Location: " + event.getString("location") + " </h3>");
                resp.getWriter().print("<h3> The date of the event: " + event.getDate("event_date") + " </h3>");
                ticketsAvailable = event.getInt("tickets_avaiable");
                if (ticketsAvailable <= 0) {
                    resp.getWriter().println("<h2> Sorry, no more tickets </h2>");
                    resp.getWriter().println("<p><a href=\"/events\">Back to Events</a>");
                    resp.getWriter().println(EventConstants.PAGE_FOOTER);
                    return;
                }
                resp.getWriter().print("<h3> Tickets available: " + ticketsAvailable + " </h3>");
                resp.getWriter().print("<h3> Price per ticket: " + event.getDouble("price") + " </h3>");

                resp.getWriter().print("<form action=\"/purchase?event_id=" + eventId + "\" method=\"post\">\n" +

                        "  <label for=\"number\">Number of Tickets to buy:</label><br/>\n" +
                        "  <select id=\"number\" name=\"number\"/><br/>\n");

                int number = 1;
                while (ticketsAvailable > 0) {
                    resp.getWriter().println("\"  <option value=" + number + ">" + number +"</option>\\n\"");
                    ticketsAvailable--;
                    number++;
                }
                resp.getWriter().println("</select>" +

                        "  <input type=\"submit\" value=\"Purchase\"/>\n");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        resp.getWriter().println("<p><a href=\"/events\">Back to Events</a>");
        resp.getWriter().println(EventConstants.PAGE_FOOTER);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sessionId = req.getSession(false).getId();
        String[] requestURI = req.getQueryString().split("=", 2);
        int eventId = Integer.parseInt(requestURI[1]);

        int ticketsPurchased = Integer.parseInt(req.getParameter("number"));
        int currTickets;
        int newNum;
        int userId;

        // update the events DB and the user_to_event DB
        ResultSet event = null;

        try {
            Connection con = DBCPDataSource.getConnection();
            event = JDBCEvent.getEventGivenEventId(con, eventId);

            if (event.next()) {
                // update events DB
                currTickets = event.getInt("tickets_avaiable");
                newNum = currTickets - ticketsPurchased;
                JDBCEvent.updateTicketsAvailable(con, eventId, newNum);

                // update user_to_event DB
                userId = JDBCServer.getUserIdGivenSession(con, sessionId);

                // first check if the user_id and event_id row exists, if exists:
                if (JDBCEvent.checkUserEvent(con, userId, eventId)) {
                    // get the row where userid and eventid matches
                    int tempTickets = JDBCEvent.getTicketsGivenUserAndEventID(con, userId, eventId);
                    // then update that row's ticket_num to +ticketsPurchased
                    JDBCEvent.updateTicketsNum(con, userId, eventId, tempTickets + ticketsPurchased);
                } else {
                    // if the row doesn't exist, create a new row
                    JDBCEvent.insertUserAndEvent(con, userId, eventId, ticketsPurchased);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        resp.setStatus(HttpStatus.OK_200);
        resp.getWriter().println(EventConstants.PAGE_HEADER);
        resp.getWriter().println("<h2> Purchase successful </h2>");
        resp.getWriter().println("<p><a href=\"/events\">Back to Events</a>");
        resp.getWriter().println(EventConstants.PAGE_FOOTER);
    }
}
