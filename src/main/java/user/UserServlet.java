package user;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.http.HttpStatus;
import server.TicketServerConstants;
import utilities.ServerConfig;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * User account page that shows the user's information and has a button directing to purchased tickets
 */
public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // retrieve the ID of this session
        String sessionId = req.getSession(true).getId();


        // retrieve the serverConfig info from the context
        ServerConfig serverConfig = (ServerConfig) req.getServletContext().getAttribute(TicketServerConstants.CONFIG_KEY);

        try (Connection connection = DBCPDataSource.getConnection()){
            ResultSet result = JDBCUsers.executeSelectUsers(connection);
//            req.getSession().setAttribute(TicketServerConstants.CLIENT_INFO_KEY);
            resp.setStatus(HttpStatus.OK_200);
            resp.getWriter().println(TicketServerConstants.PAGE_HEADER);
            resp.getWriter().println("<h1>Hello, " + result.getString("name") + "</h1>");

            //button to change name
            resp.getWriter().println("<h3>Your email is: " + result.getString("email") + "</h3>");
            resp.getWriter().println("<p><a href=\"/change-name\">Change Name</a>");

            resp.getWriter().println(TicketServerConstants.PAGE_FOOTER);

        } catch(SQLException e) {
            e.printStackTrace();
        }


    }
}
