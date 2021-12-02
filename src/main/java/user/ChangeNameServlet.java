package user;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.http.HttpStatus;
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
        char[] bodyArr = new char[100];
        // get the firstName from the text box
        req.getReader().read(bodyArr,0, bodyArr.length);
        String firstBody = new String(bodyArr);
        LOGGER.info("first name input: " + firstBody);
        //fname=([^&]*)
        //fname=Cara
        String firstName = URLDecoder.decode(firstBody.substring(firstBody.indexOf("=")+1, firstBody.length()), StandardCharsets.UTF_8.toString());
        LOGGER.info("first name: " + firstName);

        // get the lastName from the text box
        char[] secondArr = new char[100];
        req.getReader().read(secondArr,0, secondArr.length);
        String secondBody = new String(secondArr);
        LOGGER.info("last name input: " + secondBody);
        String lastName = URLDecoder.decode(secondBody.substring(secondBody.indexOf("=")+1, secondBody.length()), StandardCharsets.UTF_8.toString());
        LOGGER.info("last name: " + lastName);

        if (!firstName.isEmpty() && !lastName.isEmpty()) {
            CurrentUser.userInfo.setFirstName(firstName);
            CurrentUser.userInfo.setLastName(lastName);
            try (Connection connection = DBCPDataSource.getConnection()) {
                JDBCUsers.changeFirstName(connection, firstName, CurrentUser.userInfo.getEmail());
                JDBCUsers.changeLastName(connection, lastName, CurrentUser.userInfo.getEmail());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            resp.setStatus(HttpStatus.OK_200);
            resp.getWriter().println(ChangeNameConstant.PAGE_HEADER);
            resp.getWriter().println("<h3>Your first name has been updated to: " + CurrentUser.userInfo.getFirstName() + "</h3>");
            resp.getWriter().println("<h3>Your last name has been updated to: " + CurrentUser.userInfo.getLastName() + "</h3>");
        } else if (!firstName.isEmpty()) {
            CurrentUser.userInfo.setFirstName(firstName);
            try (Connection connection = DBCPDataSource.getConnection()) {
                JDBCUsers.changeFirstName(connection, firstName, CurrentUser.userInfo.getEmail());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            resp.setStatus(HttpStatus.OK_200);
            resp.getWriter().println(ChangeNameConstant.PAGE_HEADER);
            resp.getWriter().println("<h3>Your first name has been updated to: " + CurrentUser.userInfo.getFirstName() + "</h3>");
        } else if (!lastName.isEmpty()) {
            try (Connection connection = DBCPDataSource.getConnection()) {
                JDBCUsers.changeLastName(connection, lastName, CurrentUser.userInfo.getEmail());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            resp.setStatus(HttpStatus.OK_200);
            resp.getWriter().println(ChangeNameConstant.PAGE_HEADER);
            resp.getWriter().println("<h3>Your last name has been updated to: " + CurrentUser.userInfo.getLastName() + "</h3>");
        }

        resp.getWriter().println("<p><a href=\"/user\">Back</a>");
        resp.getWriter().println(ChangeNameConstant.PAGE_FOOTER);
        //add buttons
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sessionId = req.getSession(false).getId();
        resp.setStatus(HttpStatus.OK_200);
        resp.getWriter().println(ChangeNameConstant.GET_CHANGE_NAME_PAGE);
    }
}
