package event;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.http.HttpStatus;
import utilities.DBCPDataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class DeleteEventServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sessionId = req.getSession(false).getId();

        String[] requestURI = req.getQueryString().split("=", 2);
        int eventId = Integer.parseInt(requestURI[1]);

        try {
            Connection con = DBCPDataSource.getConnection();
            // delete the event row in events
            JDBCEvent.deleteEventInEvents(con, eventId);
            // delete the event in user_to_event table
            JDBCEvent.deleteEventInUserToEvent(con, eventId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        resp.setStatus(HttpStatus.OK_200);
        resp.getWriter().println(CreateEventConstants.PAGE_HEADER);
        resp.getWriter().println("<h2> Event successfully deleted. </h2>");
        resp.getWriter().println("<p><a href=\"/login\">Back to Home Page</a>");
        resp.getWriter().println(CreateEventConstants.PAGE_FOOTER);
    }
}
