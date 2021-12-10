package event;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.http.HttpStatus;
import server.JDBCServer;
import user.JDBCUsers;
import utilities.DBCPDataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Handle requests to /transfer
 * To transfer tickets to an existing user in the database
 */
public class TransferTicketServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sessionId = req.getSession(false).getId();
        String[] requestURI = req.getQueryString().split("=", 2);
        int eventId = Integer.parseInt(requestURI[1]);

        ResultSet event = null;
        int ticketsAvailable = 0;
        try {
            Connection con = DBCPDataSource.getConnection();
            event = JDBCEvent.getEventGivenEventId(con, eventId);
            int userId = JDBCServer.getUserIdGivenSession(con, sessionId);
            ticketsAvailable = JDBCEvent.getTicketsGivenUserAndEventID(con, userId, eventId);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        // display information about the event
        resp.setStatus(HttpStatus.OK_200);
        resp.getWriter().println(EventConstants.PAGE_HEADER);
        try {
            if (event.next()) {
                resp.getWriter().print("<h2> " + event.getString("title") + " </h2>");
                resp.getWriter().print("<h3> Price per ticket: " + event.getDouble("price") + " </h3>");
                // display how many tickets the user have
                resp.getWriter().println("<h3> Number of ticket(s) you have: " + ticketsAvailable + " </h3>");
                // let user enter username of the account he/she want to transfer to
                // enter the number of tickets
                resp.getWriter().print("<form action=\"/transfer?event_id=" + eventId + "\" method=\"post\">\n" +

                        "  <label for=\"number\">Number of Tickets to transfer:</label><br/>\n" +
                        "  <select id=\"number\" name=\"number\"/><br/>\n");

                int number = 1;
                while (ticketsAvailable > 0) {
                    resp.getWriter().println("\"  <option value=" + number + ">" + number +"</option>\\n\"");
                    ticketsAvailable--;
                    number++;
                }

                resp.getWriter().println("</select>\n" + "\n" +

                        "  <label for=\"email\">Email of the person you want to transfer to:</label><br/>\n" +
                        "  <input type=\"text\" id=\"email\" name=\"email\" required/><br/>\n" +

                        "  <input type=\"submit\" value=\"Transfer\"/>\n");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        resp.getWriter().println();
        resp.getWriter().println("<p><a href=\"/events\">Back to Events</a>");
        resp.getWriter().println(EventConstants.PAGE_FOOTER);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sessionId = req.getSession(false).getId();
        String[] requestURI = req.getQueryString().split("=", 2);
        int eventId = Integer.parseInt(requestURI[1]);

        int tickets = Integer.parseInt(req.getParameter("number"));
        String email = req.getParameter("email");

        int userId;
        int userIdTrans = -1;
        try {
            Connection con = DBCPDataSource.getConnection();
            userId = JDBCServer.getUserIdGivenSession(con, sessionId);
            // get email's userId
            if (JDBCUsers.checkUserExistence(con, email)) {
                userIdTrans = JDBCUsers.getUserIdGivenEmail(con, email);
            } else {
                resp.setStatus(HttpStatus.BAD_REQUEST_400);
                resp.getWriter().println(EventConstants.PAGE_HEADER);
                resp.getWriter().println("<h2> The email you entered cannot be matched to a user in our database, please try a different email address. </h2>");
                resp.getWriter().println("<p><a href=\"/transfer?event_id=" + eventId + "\">Back</a>");
                resp.getWriter().println(EventConstants.PAGE_FOOTER);
                return;
            }
            // in user_to_event table
            // the original userId row should - tickets
            int tempTickets = JDBCEvent.getTicketsGivenUserAndEventID(con, userId, eventId);
            if (tempTickets - tickets <= 0) {
                // delete the userId row in user_to_event table
                JDBCEvent.deleteUserAndEvent(con, userId, eventId);
            } else {
                JDBCEvent.updateTicketsNum(con, userId, eventId, tempTickets - tickets);
            }
            // the new (or existing row) should + tickets
            if (JDBCEvent.checkUserEvent(con, userIdTrans, eventId)) {
                int tempTicketsTrans = JDBCEvent.getTicketsGivenUserAndEventID(con, userIdTrans, eventId);
                JDBCEvent.updateTicketsNum(con, userIdTrans, eventId, tempTicketsTrans + tickets);
            } else {
                JDBCEvent.insertUserAndEvent(con, userIdTrans, eventId, tickets);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        resp.setStatus(HttpStatus.OK_200);
        resp.getWriter().println(EventConstants.PAGE_HEADER);
        resp.getWriter().println("<h2> Transfer Successful. </h2>");
        resp.getWriter().println("<p><a href=\"/login\">Back to Home Page</a>");
        resp.getWriter().println(EventConstants.PAGE_FOOTER);


    }
}
