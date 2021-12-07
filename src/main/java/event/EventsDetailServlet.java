package event;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.http.HttpStatus;
import utilities.DBCPDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class EventsDetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sessionId = req.getSession(false).getId();

        String[] requestURI = req.getQueryString().split("=", 2);
        int eventId = Integer.parseInt(requestURI[1]);

        // get the event details from DB
        ResultSet event = null;
        try {
            Connection con = DBCPDataSource.getConnection();
            event = JDBCEvent.getEventGivenEventId(con, eventId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        resp.setStatus(HttpStatus.OK_200);
        resp.getWriter().println(CreateEventConstants.PAGE_HEADER);
        try {
            if (event.next()) {
                resp.getWriter().print("<h2> " + event.getString("title") + " </h2>");
                resp.getWriter().print("<h3> Event Location: " + event.getString("location") + " </h3>");
                resp.getWriter().print("<p> " + event.getString("description") + " </p>");
                resp.getWriter().print("<h3> The date of the event: " + event.getDate("event_date") + " </h3>");
                resp.getWriter().print("<h3> Tickets available: " + event.getInt("tickets_avaiable") + " </h3>");
                resp.getWriter().print("<h3> Price per ticket: " + event.getDouble("price") + " </h3>");
                if (event.getString("event_image") != null) {
                    // display the image
                    resp.getWriter().print(event.getBlob("event_image"));
                }
                InputStream inputStream = (InputStream) event.getBlob("event_image");


                String imgLength = event.getString("event_image");
                int length = imgLength.length();
                byte[] image = new byte[length];
                InputStream inputStream1 = event.getBinaryStream("event_image");
                int index = inputStream1.read(image, 0, length);
                System.out.println("index"+index);
                inputStream1.close();
                resp.reset();
                resp.setContentType("image/jpg");
                resp.getOutputStream().write(image, 0, length);
                resp.getOutputStream().flush();

                // link to purchase ticket servlet
                resp.getWriter().println("<p><a href=\"/purchase?event_id=" + eventId + "\">Buy Ticket(s)</a>");
                resp.getWriter().println();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        resp.getWriter().println("<p><a href=\"/events\">Back to Events</a>");
        resp.getWriter().println(CreateEventConstants.PAGE_FOOTER);
    }

}
