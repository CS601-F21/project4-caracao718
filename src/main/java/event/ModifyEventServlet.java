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
 * Handle requests to /modify
 * To choose to modify or delete a event created by user
 */
public class ModifyEventServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sessionId = req.getSession(false).getId();

        int userId;
        ResultSet results = null;
        // displays all the events (title) that the user created
        try {
            Connection con = DBCPDataSource.getConnection();
            userId = JDBCServer.getUserIdGivenSession(con, sessionId);
            results = JDBCEvent.getEventGivenUserId(con, userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // in each subpage, display the details, and a button to modify the event
        resp.setStatus(HttpStatus.OK_200);
        resp.getWriter().println(EventServletConstant.PAGE_HEADER);
        resp.getWriter().println("<h1> Here are all the events you created: </h1>");
        try {
            while (results.next()) {
                resp.getWriter().print("<h3> " + results.getString("title") + " </h3>");
                resp.getWriter().println("<p><a href=\"/my-detail-event?event_id=" + results.getInt("id") + "\">Modify</a>");
                resp.getWriter().println("<p><a href=\"/delete-event?event_id=" + results.getInt("id") + "\">Delete</a>");
                resp.getWriter().println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        resp.getWriter().println("<p><a href=\"/login\">Back to Home Page</a>");
        resp.getWriter().println(EventServletConstant.PAGE_FOOTER);
    }
}
