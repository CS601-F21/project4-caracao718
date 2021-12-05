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
import java.util.ArrayList;

public class EventsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // retrieve the ID of this session
        String sessionId = req.getSession(false).getId();

        ArrayList<Integer> eventIds;
        ArrayList<String> titles = new ArrayList<>();

        try {
            Connection con = DBCPDataSource.getConnection();
            eventIds = JDBCEvent.getEventIdsGivenSession(con, sessionId);
            for (int id : eventIds) {
                titles.add(JDBCEvent.getTitleGivenEventId(con, id));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // display all the event titles, with the link to the detail information
        resp.setStatus(HttpStatus.OK_200);
        resp.setHeader("Connection", "close");
        resp.getWriter().println(EventServletConstant.PAGE_HEADER);

        for (String title : titles) {
            resp.getWriter().print("<h3> " + title + " </h3>");

        }
        resp.getWriter().println("<p><a href=\"/login\">Back to Home Page</a>");
        resp.getWriter().println(EventServletConstant.PAGE_FOOTER);
    }
}
