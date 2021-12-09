package server;

import event.JDBCEvent;
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

/**
 * Implements logic for the /login path
 */
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // retrieve the ID of this session
        String sessionId = req.getSession(false).getId();

        //retrieve first name from database
        String firstName = "";
        try {
            Connection con = DBCPDataSource.getConnection();
            firstName = JDBCServer.getFirstNameGivenSession(con, sessionId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ArrayList<Integer> eventIds;
        ArrayList<String> titles = new ArrayList<>();

        resp.setStatus(HttpStatus.OK_200);
        resp.setHeader("Connection", "close");
        resp.getWriter().println(LoginServletConstant.PAGE_HEADER);
        resp.getWriter().println("<h1>Hello, " + firstName + "</h1>");
        resp.getWriter().println("<h2> Here are the events you have coming up </h2>");
        // display all the events
        try {
            Connection con = DBCPDataSource.getConnection();
            eventIds = JDBCEvent.getEventIdsGivenSession(con, sessionId);
            for (int id : eventIds) {
                titles.add(JDBCEvent.getTitleGivenEventId(con, id));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (String title : titles) {
            resp.getWriter().println("<h3> " + title + " </h3>");
        }
        if (!titles.isEmpty()) {
            resp.getWriter().println("<p><a href=\"/user-events\">event details</a>");
        } else {
            resp.getWriter().println("<p> You don't have any events coming up, please check out the EVENTS page! </p>");
        }

        resp.getWriter().println("<h2> What can I do for you? </h2>");
        addButtons(resp);
        resp.getWriter().println(LoginServletConstant.PAGE_FOOTER);

//        resp.getWriter().close();
//        resp.flushBuffer();
    }

    /**
     * Add the buttons for the functionalities of the web page
     * @param resp
     * @throws IOException
     */
    private void addButtons(HttpServletResponse resp) throws IOException {
        resp.getWriter().println("<p><a href=\"/user\">My Account</a>");
        resp.getWriter().println("<p><a href=\"/create\">Create Event</a>");
        resp.getWriter().println("<p><a href=\"/events?page=0&total=1\">Events</a>");
        resp.getWriter().println("<p><a href=\"/modify\">Modify events created by me</a>");
//        resp.getWriter().println("<p><a href=\"/transfer\">Transfer Tickets</a>");
        resp.getWriter().println("<p><a href=\"/logout\">Signout</a>");
    }


}
