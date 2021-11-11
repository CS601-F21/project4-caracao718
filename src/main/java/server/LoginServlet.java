package server;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.http.HttpStatus;
import user.UserInfo;
import utilities.Config;
import utilities.HTTPFetcher;
import utilities.LoginUtilities;

import java.io.IOException;
import java.util.Map;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // retrieve the ID of this session
        String sessionId = req.getSession(true).getId();

        // determine whether the user is already authenticated
        Object clientInfoObj = req.getSession().getAttribute(TicketServerConstants.CLIENT_INFO_KEY);
        if(clientInfoObj != null) {
            // already authed, no need to log in
            resp.getWriter().println(TicketServerConstants.PAGE_HEADER);
            resp.getWriter().println("<h1>You have already been authenticated</h1>");
            addButtons(resp);
            resp.getWriter().println(TicketServerConstants.PAGE_FOOTER);
            return;
        }

        // retrieve the config info from the context
        Config config = (Config) req.getServletContext().getAttribute(TicketServerConstants.CONFIG_KEY);

        // retrieve the code provided by Slack
        String code = req.getParameter(TicketServerConstants.CODE_KEY);

        // generate the url to use to exchange the code for a token:
        // After the user successfully grants your app permission to access their Slack profile,
        // they'll be redirected back to your service along with the typical code that signifies
        // a temporary access code. Exchange that code for a real access token using the
        // /openid.connect.token method.
        String url = LoginUtilities.generateSlackTokenURL(config.getClient_id(), config.getClient_secret(), code, config.getRedirect_url());

        // Make the request to the token API
        String responseString = HTTPFetcher.doGet(url, null);
        Map<String, Object> response = LoginUtilities.jsonStrToMap(responseString);

        UserInfo userInfo = LoginUtilities.verifyTokenResponse(response, sessionId);

        if(userInfo == null) {
            resp.setStatus(HttpStatus.OK_200);
            resp.getWriter().println(TicketServerConstants.PAGE_HEADER);
            resp.getWriter().println("<h1>Oops, login unsuccessful</h1>");
            resp.getWriter().println(TicketServerConstants.PAGE_FOOTER);
        } else {
            req.getSession().setAttribute(TicketServerConstants.CLIENT_INFO_KEY, userInfo);
            resp.setStatus(HttpStatus.OK_200);
            resp.getWriter().println(TicketServerConstants.PAGE_HEADER);
            resp.getWriter().println("<h1>Hello, " + userInfo.getName() + "</h1>");
            addButtons(resp);
            resp.getWriter().println(TicketServerConstants.PAGE_FOOTER);

        }
    }

    /**
     * Add the buttons for the functionalities of the web page
     * @param resp
     * @throws IOException
     */
    private void addButtons(HttpServletResponse resp) throws IOException {
        resp.getWriter().println("<p><a href=\"/user\">My Account</a>");
        resp.getWriter().println("<p><a href=\"/create\">Create Event</a>");
        resp.getWriter().println("<p><a href=\"/events\">Events</a>");
        resp.getWriter().println("<p><a href=\"/purchase\">Purchase Tickets</a>");
        resp.getWriter().println("<p><a href=\"/transfer\">Transfer Tickets</a>");
        resp.getWriter().println("<p><a href=\"/logout\">Signout</a>");
    }


}
