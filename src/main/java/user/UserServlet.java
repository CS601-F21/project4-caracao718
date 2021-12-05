package user;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.http.HttpStatus;
import server.JDBCServer;
import server.TicketServerConstants;
import utilities.DBCPDataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * User account page that shows the user's information and has a button directing to purchased tickets
 */
public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // retrieve the ID of this session
        String sessionId = req.getSession(false).getId();

        String firstName = "";
        String lastName = "";
        String email = "";
        String username = "";
        try {
            Connection con = DBCPDataSource.getConnection();
            firstName = JDBCServer.getFirstNameGivenSession(con, sessionId);
            lastName = JDBCServer.getLastNameGivenSession(con, sessionId);
            email = JDBCServer.getEmailGivenSession(con, sessionId);
            username = JDBCServer.getUsernameGivenSession(con, sessionId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //button to change name
        resp.setStatus(HttpStatus.OK_200);
        resp.setHeader("Connection", "close");
        resp.getWriter().println(TicketServerConstants.PAGE_HEADER);
        resp.getWriter().println("<h1>Hello, " + firstName + " " + lastName + "</h1>");
        resp.getWriter().println("<h3>Your email is: " + email + "</h3>");
        resp.getWriter().println("<h3>Your Username is: " + username + "</h3>");
        resp.getWriter().println("<p><a href=\"/change-name\">Change Name</a>");
        resp.getWriter().println("<p><a href=\"/login\">Back to Home Page</a>");
        resp.getWriter().println(TicketServerConstants.PAGE_FOOTER);

    }
}
