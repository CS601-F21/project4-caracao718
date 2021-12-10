package user;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.http.HttpStatus;
import server.JDBCServer;
import server.TicketServerConstants;
import utilities.DBCPDataSource;
import utilities.HTTPFetcher;
import utilities.LoginUtilities;
import utilities.ServerConfig;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

/**
 * Handle requests to /signup
 * For first time users to create an account
 */
public class SignupServlet extends HttpServlet {
    private Logger LOGGER = LogManager.getLogger(SignupServlet.class);;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // retrieve the ID of this session
        String sessionId = req.getSession(false).getId();

        // determine whether the user is already authenticated
        Object clientInfoObj = req.getSession().getAttribute(TicketServerConstants.CLIENT_INFO_KEY);
        if(clientInfoObj != null) {
            // already authed, no need to log in
            resp.setStatus(HttpStatus.OK_200);
            resp.setHeader("Connection", "close");
            resp.getWriter().println(TicketServerConstants.PAGE_HEADER);
            resp.getWriter().println("<h1>You have already been authenticated</h1>");
            resp.getWriter().println("<p><a href=\"/login\">Main Page</a>");
            resp.getWriter().println(TicketServerConstants.PAGE_FOOTER);
            return;
        }

        // retrieve the serverConfig info from the context
        ServerConfig serverConfig = (ServerConfig) req.getServletContext().getAttribute(TicketServerConstants.CONFIG_KEY);

        // retrieve the code provided by Slack
        String code = req.getParameter(TicketServerConstants.CODE_KEY);

        // generate the url to use to exchange the code for a token:
        // After the user successfully grants your app permission to access their Slack profile,
        // they'll be redirected back to your service along with the typical code that signifies
        // a temporary access code. Exchange that code for a real access token using the
        // /openid.connect.token method.
        String url = LoginUtilities.generateSlackTokenURL(serverConfig.getClient_id(), serverConfig.getClient_secret(), code, serverConfig.getRedirect_url());

        // Make the request to the token API
        String responseString = HTTPFetcher.doGet(url, null);
        Map<String, Object> response = LoginUtilities.jsonStrToMap(responseString);

        UserInfo userInfo = LoginUtilities.verifyTokenResponse(response, sessionId);
        try {
            Connection con = DBCPDataSource.getConnection();
            assert userInfo != null : "user info is null";
            JDBCServer.executeInsertSession(con, sessionId, userInfo.getEmail());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (userInfo == null) {
            resp.setStatus(HttpStatus.OK_200);
            resp.setHeader("Connection", "close");
            resp.getWriter().println(TicketServerConstants.PAGE_HEADER);
            resp.getWriter().println("<h1>Oops, authentication unsuccessful</h1>");
            resp.getWriter().println(TicketServerConstants.PAGE_FOOTER);
            return;
        }


        // check if the user is already in the database, if so, redirect to /login
        boolean exists = false;
        try {
            Connection con = DBCPDataSource.getConnection();
            String currEmail = JDBCServer.getEmailGivenSession(con, sessionId);
            exists = JDBCUsers.checkUserExistence(con, currEmail);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (exists) {
            resp.setStatus(HttpStatus.OK_200);
            resp.setHeader("Connection", "close");
            resp.getWriter().println(TicketServerConstants.PAGE_HEADER);
            resp.getWriter().println("You have already signed up, please click below to your home page~");
            resp.getWriter().println("<p><a href=\"/login\">My Home Page</a>");
            resp.getWriter().println(TicketServerConstants.PAGE_FOOTER);
//            resp.sendRedirect("/login");
            return;
        }
        // if not, insert user into the DB and continue in the signup page
        try {
            Connection con = DBCPDataSource.getConnection();
            JDBCUsers.executeInsertFirstnameEmail(con, userInfo.getFirstName(), userInfo.getEmail());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        resp.setStatus(HttpStatus.OK_200);
        resp.setHeader("Connection", "close");
        resp.getWriter().println(SignupConstant.SIGN_UP_PAGE);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sessionId = req.getSession(false).getId();

        String username = req.getParameter("username");
        String lastName = req.getParameter("lname");
        String location = req.getParameter("location");
        String eventType = req.getParameter("event_type");

        try {
            Connection con = DBCPDataSource.getConnection();
            String email = JDBCServer.getEmailGivenSession(con, sessionId);
            JDBCUsers.executeSignupInsert(con, username, lastName, location, eventType, email);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        resp.sendRedirect("/login");

    }
}
