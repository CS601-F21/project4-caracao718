package event;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.http.HttpStatus;
import utilities.DBCPDataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Handle requests to /my-detail-event
 * To modify the event created by user
 */
public class ModifyDetailEventServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String[] requestURI = req.getQueryString().split("=", 2);
        int eventId = Integer.parseInt(requestURI[1]);

        // get the event details from DB
        ResultSet event = null;
        try {
            Connection con = DBCPDataSource.getConnection();
            event = JDBCEvent.getEventGivenEventId(con, eventId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        long millis = System.currentTimeMillis();
        Date date = new Date(millis);
        String currDate = date.toString();


        resp.setStatus(HttpStatus.OK_200);
        resp.getWriter().println(EventConstants.PAGE_HEADER);
        try {
            if (event.next()) {
                resp.getWriter().println("<h2> Please fill out all the information in the form below to modify the event.</h2>\n");
                resp.getWriter().print(

                    "<form action=\"/my-detail-event?event_id=" + event.getInt("id") + "\" method=\"post\">\n" +

                    "  <label for=\"title\">Event Title:</label><br/>\n" +
                    "  <input type=\"text\" id=\"title\" name=\"title\" value=\"" + event.getString("title") + "\"/><br/>\n" +

                        "  <label for=\"description\">Description:</label><br/>\n" +
                    "  <textarea id=\"description\" name=\"description\" rows=\"10\" cols=\"30\">" + event.getString("description") + "</textarea><br/>\n" +

                    "  <label for=\"date\">Event Date:</label><br/>\n" +
                    "  <input type=\"date\" id=\"date\" name=\"date\" min=\"" + currDate + "\" value=\"" + event.getDate("event_date") + "\"/><br/>\n" +

                    "  <label for=\"numticket\">Total Number of Tickets:</label><br/>\n" +
                    "  <input type=\"number\" id=\"numticket\" name=\"numticket\" min=\"" + event.getInt("total_tickets") + "\" value=\"" + event.getInt("total_tickets") + "\"/><br/>\n" +

                    "  <label for=\"price\">Price:</label><br/>\n" +
                    "  <input type=\"number\" id=\"price\" name=\"price\" value=\"" + event.getDouble("price") + "\"/><br/>\n" +

                    "  <label for=\"location\">Location:</label><br/>\n" +
                    "  <select id=\"location\" name=\"location\" value=\"" + event.getString("location") + "\"/><br/>\n" +
                    "  <option value=\"New York City\"> New York City</option>\n" +
                    "  <option value=\"San Francisco\"> San Francisco</option>\n" +
                    "  <option value=\"Los Angeles\"> Los Angeles</option>\n" +
                    "  <option value=\"Boston\"> Boston</option>\n" +
                    "  <option value=\"Seattle\"> Seattle</option>\n" +
                    "  <option value=\"San Diego\"> San Diego</option>\n" +
                    "</select>" +

                    "   \n  " +
                    "  <input type=\"submit\" value=\"Submit\"/>\n" +
                    "</form>\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // link to purchase ticket servlet
        resp.getWriter().println();
        resp.getWriter().println("<p><a href=\"/login\">Back to Home Page</a>");
        resp.getWriter().println(EventConstants.PAGE_FOOTER);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sessionId = req.getSession(false).getId();

        String[] requestURI = req.getQueryString().split("=", 2);
        int eventId = Integer.parseInt(requestURI[1]);

        String title = req.getParameter("title");
        String description = req.getParameter("description");
        Date startDate = Date.valueOf(req.getParameter("date"));
        int numOfTickets = Integer.parseInt(req.getParameter("numticket"));

        double price = Double.parseDouble(req.getParameter("price"));
        String location = req.getParameter("location");

        int currAvailableTickets;
        int originalTotalTickets;

        ResultSet event = null;

        // update the database
        try {
            Connection con = DBCPDataSource.getConnection();
            // get current available tickets
            currAvailableTickets = JDBCEvent.getAvailableTicketsGivenEventId(con, eventId);
            originalTotalTickets = JDBCEvent.getTotalTicketsGivenEventId(con, eventId);
            int newAvailableTicket = currAvailableTickets + (numOfTickets - originalTotalTickets);
            JDBCEvent.updateEvent(con, eventId, newAvailableTicket, title, description, startDate, numOfTickets, price, location);

            event = JDBCEvent.getEventGivenEventId(con, eventId);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        resp.setStatus(HttpStatus.OK_200);
        resp.getWriter().println(EventConstants.PAGE_HEADER);
        try {
            if (event.next()) {
                resp.getWriter().print("<h2> " + event.getString("title") + " </h2>");
                resp.getWriter().print("<h3> Event Location: " + event.getString("location") + " </h3>");
                resp.getWriter().print("<p> " + event.getString("description") + " </p>");
                resp.getWriter().print("<h3> The date of the event: " + event.getDate("event_date") + " </h3>");
                resp.getWriter().print("<h3> Tickets available: " + event.getInt("tickets_avaiable") + " </h3>");
                resp.getWriter().print("<h3> Total Tickets: " + event.getInt("total_tickets") + " </h3>");
                resp.getWriter().print("<h3> Price per ticket: " + event.getDouble("price") + " </h3>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        resp.getWriter().println();
        resp.getWriter().println("<p><a href=\"/events\">Back to Events</a>");
        resp.getWriter().println(EventConstants.PAGE_FOOTER);
    }
}
