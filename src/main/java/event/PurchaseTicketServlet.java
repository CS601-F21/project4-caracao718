package event;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utilities.DBCPDataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PurchaseTicketServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sessionId = req.getSession(false).getId();


        // get current date in sql format
        long millis = System.currentTimeMillis();
        Date currDate = new Date(millis);
        ResultSet events = null;

        try {
            Connection con = DBCPDataSource.getConnection();
            events = JDBCEvent.getAllEvents(con, currDate);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // display all the events available

        // next to each event, have a button to purchase
    }
}
