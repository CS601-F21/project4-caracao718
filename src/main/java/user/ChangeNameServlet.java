package user;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.http.HttpStatus;
import utilities.DBCPDataSource;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;

public class ChangeNameServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // get the name from the text box
        String sessionId = req.getSession(true).getId();
        char[] bodyArr = new char[100];
        req.getReader().read(bodyArr,0, bodyArr.length);
        String body = new String(bodyArr);
        String name = URLDecoder.decode(body.substring(body.indexOf("=")+1, body.length()), StandardCharsets.UTF_8.toString());

        if (!name.isEmpty()) {
            CurrentUser.userInfo.setName(name);
            //change the name in database
            try (Connection connection = DBCPDataSource.getConnection()) {
                JDBCUsers.changeName(connection, name, CurrentUser.userInfo.getEmail());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        resp.setStatus(HttpStatus.OK_200);
        resp.getWriter().println(ChangeNameConstant.PAGE_HEADER);
        resp.getWriter().println("<h3>Name has been updated to: " + CurrentUser.userInfo.getName() + "</h3>");
        resp.getWriter().println("<p><a href=\"/user\">Back</a>");
        resp.getWriter().println(ChangeNameConstant.PAGE_FOOTER);
        //add buttons
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sessionId = req.getSession(true).getId();
        resp.setStatus(HttpStatus.OK_200);
        resp.getWriter().println(ChangeNameConstant.GET_CHANGE_NAME_PAGE);
    }
}
