package event;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.http.HttpStatus;
import server.TicketServerConstants;
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

        resp.setStatus(HttpStatus.OK_200);
        resp.getWriter().println(TicketServerConstants.PAGE_HEADER);
        resp.getWriter().println("<h1>Here are your upcoming event details</h1>");
        // display all the events
        try {
            Connection con = DBCPDataSource.getConnection();
            eventIds = JDBCEvent.getEventIdsGivenSession(con, sessionId);
            for (int id : eventIds) {
                details.add(JDBCEvent.getEventGivenEventId(con, id));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (ResultSet event : details) {
            try {
                while (event.next()) {
                    try {
                        resp.getWriter().println("<h2> " + event.getString("title") + " </h2>");
                        resp.getWriter().println("<p> " + event.getString("description") + " </p>");

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        resp.getWriter().println(TicketServerConstants.PAGE_FOOTER);
    }
}
