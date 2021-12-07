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

public class EventsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // retrieve the ID of this session
        String sessionId = req.getSession(false).getId();

        ResultSet events = null;

        // get current date in sql format
        long millis = System.currentTimeMillis();
        Date currDate = new Date(millis);

        try {
            Connection con = DBCPDataSource.getConnection();
            events = JDBCEvent.getAllEvents(con, currDate);
        } catch (SQLException e) {
            e.printStackTrace();
        }

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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        resp.getWriter().println("<p><a href=\"/login\">Back to Home Page</a>");
        resp.getWriter().println(EventServletConstant.PAGE_FOOTER);
    }
}
