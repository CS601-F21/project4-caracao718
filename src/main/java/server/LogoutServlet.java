package server;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utilities.DBCPDataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Handles a request to /logout
 */
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // log out by invalidating the session
        String sessionId = req.getSession(false).getId();
        try {
            Connection con = DBCPDataSource.getConnection();
            JDBCServer.deleteSession(con, sessionId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        req.getSession().invalidate();
        resp.getWriter().println(TicketServerConstants.PAGE_HEADER);
        resp.getWriter().println("<h1>Thanks for joining</h1>");
        resp.getWriter().println("<p><a href=\"/\">Login Again</a>");
        resp.getWriter().println(TicketServerConstants.PAGE_FOOTER);
    }
}
