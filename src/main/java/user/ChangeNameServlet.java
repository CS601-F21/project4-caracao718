package user;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.http.HttpStatus;
import server.JDBCServer;
import utilities.DBCPDataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;

public class ChangeNameServlet extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(ChangeNameServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String sessionId = req.getSession(false).getId();

        String newFirstName = req.getParameter("fname");
        String newLastName = req.getParameter("lname");

        String email = "";
        // change first name and last name in DB
        try {
            Connection con = DBCPDataSource.getConnection();
            email = JDBCServer.getEmailGivenSession(con, sessionId);
            JDBCUsers.changeFirstName(con, newFirstName, email);
            JDBCUsers.changeLastName(con, newLastName, email);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        resp.setStatus(HttpStatus.OK_200);
        resp.setHeader("Connection", "close");
        resp.getWriter().println(ChangeNameConstant.PAGE_HEADER);
        resp.getWriter().println("<h3>Your first name has been updated to: " + newFirstName + "</h3>");
        resp.getWriter().println("<h3>Your last name has been updated to: " + newLastName + "</h3>");
        resp.getWriter().println("<p><a href=\"/user\">Back</a>");
        resp.getWriter().println(ChangeNameConstant.PAGE_FOOTER);


    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sessionId = req.getSession(false).getId();
        resp.setStatus(HttpStatus.OK_200);
        resp.getWriter().println(ChangeNameConstant.GET_CHANGE_NAME_PAGE);
    }
}
