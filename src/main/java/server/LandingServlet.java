package server;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.http.HttpStatus;
import utilities.DBCPDataSource;
import utilities.ServerConfig;
import utilities.LoginUtilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Landing page that allows a user to request to login with Slack.
 */
public class LandingServlet extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger(LandingServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // retrieve the ID of this session
        String sessionId = req.getSession(true).getId();
        LOGGER.info("sessionID: " +sessionId);

        // put sessionID into the session table in DB
        try {
            Connection con = DBCPDataSource.getConnection();
            if (!JDBCServer.ifSessionExists(con, sessionId)) {
                JDBCServer.executeInsertSession(con, sessionId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // determine whether the user is already authenticated
        Object clientInfoObj = req.getSession().getAttribute(TicketServerConstants.CLIENT_INFO_KEY);
        if(clientInfoObj != null) {
            // already authed, no need to log in
            resp.setStatus(HttpStatus.OK_200);
            resp.setHeader("Connection", "close");
            resp.getWriter().println(TicketServerConstants.PAGE_HEADER);
            resp.getWriter().println("<h1>Something went wrong</h1>");
            resp.getWriter().println("<p><a href=\"/logout\">Logout</a>");
            resp.getWriter().println(TicketServerConstants.PAGE_FOOTER);
            return;
        }

        // retrieve the serverConfig info from the context
        ServerConfig serverConfig = (ServerConfig) req.getServletContext().getAttribute(TicketServerConstants.CONFIG_KEY);

        /** From the OpenID spec:
         * state
         * RECOMMENDED. Opaque value used to maintain state between the request and the callback.
         * Typically, Cross-Site Request Forgery (CSRF, XSRF) mitigation is done by cryptographically
         * binding the value of this parameter with a browser cookie.
         *
         * Use the session ID for this purpose.
         */
        String state = sessionId;

        /** From the Open ID spec:
         * nonce
         * OPTIONAL. String value used to associate a Client session with an ID Token, and to mitigate
         * replay attacks. The value is passed through unmodified from the Authentication Request to
         * the ID Token. Sufficient entropy MUST be present in the nonce values used to prevent attackers
         * from guessing values. For implementation notes, see Section 15.5.2.
         */
        String nonce = LoginUtilities.generateNonce(state);

        // Generate url for request to Slack
        String url = LoginUtilities.generateSlackAuthorizeURL(serverConfig.getClient_id(),
                state,
                nonce,
                serverConfig.getRedirect_url());

        resp.setStatus(HttpStatus.OK_200);
        PrintWriter writer = resp.getWriter();
        writer.println(TicketServerConstants.PAGE_HEADER);
        writer.println("<h1>Welcome to the Login of Ticket Seller</h1>");
        writer.println("<a href=\""+url+"\"><img src=\"" + TicketServerConstants.SLACK_SIGN_IN_BUTTON_URL +"\"/></a>");
        writer.println(TicketServerConstants.PAGE_FOOTER);

    }

}
