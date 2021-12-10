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
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A servlet class that displays 5 events per page
 */
public class EventsServlet extends HttpServlet {
    private int eventsPerPage = 5;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // retrieve the ID of this session
        String sessionId = req.getSession(false).getId();
        String[] requestURI = req.getQueryString().split("\\p{Punct}", 4);
        // first page number default is 0
        int pageNumber = Integer.parseInt(requestURI[1]);
        int total = Integer.parseInt(requestURI[3]);

        ResultSet events = null;

        // get current date in sql format
        long millis = System.currentTimeMillis();
        Date currDate = new Date(millis);

        int totalNumberOfEvents = 0;
        try {
            Connection con = DBCPDataSource.getConnection();
            events = JDBCEvent.getAllEvents(con, currDate);
            while (events.next()) {
                totalNumberOfEvents++;
            }
            events = JDBCEvent.getLimitEvents(con, currDate, pageNumber * 5);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /**
         * Calculate the total page needed. Each page displays 5 event titles
         */
        int totalPageNumber = (int)Math.ceil(totalNumberOfEvents / eventsPerPage);


        // display all the event titles, with the link to the detail information
        resp.setStatus(HttpStatus.OK_200);
        resp.setHeader("Connection", "close");
        resp.getWriter().println(EventServletConstant.PAGE_HEADER);
        resp.getWriter().println("<h1> Here are all the events that are available. </h1>");
        try {
            while (events.next()) {
                resp.getWriter().print("<h3> " + events.getString("title") + " </h3>");
                resp.getWriter().println("<p><a href=\"/event-detail?event_id=" + events.getInt("id") + "\">details</a>");
                resp.getWriter().println();
            }
            int displayNumber = pageNumber + 1;
            resp.getWriter().println("<h5>This is page " + displayNumber + "</h5>");
            if (pageNumber < total) {
                resp.getWriter().println();
                resp.getWriter().println("<p><a href=\"/events?page=" + ++pageNumber + "&total=" + totalPageNumber + "\">Next Page</a>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        resp.getWriter().println("<p><a href=\"/login\">Back to Home Page</a>");
        resp.getWriter().println(EventServletConstant.PAGE_FOOTER);
    }
}
