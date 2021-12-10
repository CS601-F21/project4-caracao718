package event;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.http.HttpStatus;
import utilities.DBCPDataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class SearchEventServlet extends HttpServlet {
    private int eventsPerPage = 5;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sessionId = req.getSession(false).getId();
//        String[] requestURI = req.getQueryString().split("=", 2);
//        System.out.println(requestURI.toString());
//        int currPage = Integer.parseInt(requestURI[1]);

        // display the search area text boxes : title, description, location, date
        resp.setStatus(HttpStatus.OK_200);
        resp.getWriter().println(CreateEventConstants.SEARCH_EVENT_FORM);


//        String title = req.getParameter("title");
//        System.out.println(title);
//        String description = req.getParameter("description");
//        System.out.println(description);
//        Date date = Date.valueOf(req.getParameter("date"));
//        System.out.println(date);
//        String location = req.getParameter("location");
//        System.out.println(location);
//
//
//        int totalNumberOfEvents = searchAll(title, description, date, location);
//        int totalPageNumber = (int)Math.ceil(totalNumberOfEvents / eventsPerPage);
//        ResultSet pageResults = searchPage(title, description, date, location, currPage * eventsPerPage);
//
//        // if there's any pageResults in the ResultSet, display them
//        try {
//            while (pageResults.next()) {
//                resp.getWriter().print("<h3> " + pageResults.getString("title") + " </h3>");
//                resp.getWriter().println("<p>Description: " + pageResults.getString("description") + "</p>");
//                resp.getWriter().println("<p> Event Date and Time: " + pageResults.getDate("event_date") + " </p>");
//                resp.getWriter().print("<h3> Event Location: " + pageResults.getString("location") + " </h3>");
//                resp.getWriter().println("<p><a href=\"/event-detail?event_id=" + pageResults.getInt("id") + "\">details</a>");
//                resp.getWriter().println();
//            }
//            pageResults.beforeFirst();
//
//            if (pageResults.next()) {
//                int displayNumber = currPage + 1;
//                resp.getWriter().println("<h5>This is page " + displayNumber + "</h5>");
//                if (currPage < totalPageNumber) {
//                    resp.getWriter().println();
//                    resp.getWriter().println("<p><a href=\"/events?page=" + ++currPage + "&total=" + totalPageNumber + "\">Next Page</a>");
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        resp.getWriter().println("<p><a href=\"/login\">My Home Page</a>");
        resp.getWriter().println(CreateEventConstants.PAGE_FOOTER);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sessionId = req.getSession(false).getId();
        String[] requestURI = req.getQueryString().split("\\p{Punct}", 10);
        int currPage = Integer.parseInt(requestURI[1]);

        String title = req.getParameter("title");
        String description = req.getParameter("description");
        Date date = Date.valueOf(req.getParameter("date"));
        String location = req.getParameter("location");



        int totalNumberOfEvents = searchAll(title, description, date, location);
        int totalPageNumber = (int)Math.ceil(totalNumberOfEvents / eventsPerPage);
        ResultSet pageResults = searchPage(title, description, date, location, currPage * eventsPerPage);

        resp.setStatus(HttpStatus.OK_200);
        resp.getWriter().println(CreateEventConstants.PAGE_HEADER);
        // if there's any pageResults in the ResultSet, display them
        boolean displayPageNumber = false;
        try {
            while (pageResults.next()) {
                displayPageNumber = true;
                resp.getWriter().print("<h3> " + pageResults.getString("title") + " </h3>");
                resp.getWriter().println("<p>Description: " + pageResults.getString("description") + "</p>");
                resp.getWriter().println("<p> Event Date and Time: " + pageResults.getDate("event_date") + " </p>");
                resp.getWriter().print("<h3> Event Location: " + pageResults.getString("location") + " </h3>");
                resp.getWriter().println("<p><a href=\"/event-detail?event_id=" + pageResults.getInt("id") + "\">details</a>");
                resp.getWriter().println();
            }
            if (displayPageNumber) {
                int displayNumber = currPage + 1;
                resp.getWriter().println(" ");
                resp.getWriter().println("<h5>This is page " + displayNumber + "</h5>");
                if (currPage < totalPageNumber) {
                    resp.getWriter().println();
                    resp.getWriter().print("<p><a href=\"/display-search?page=" + ++currPage + "&title=" + title + "&description=" + description + "&date=" + date + "&location=" + location + "\">Next Page</a>\n");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        resp.getWriter().println("<p><a href=\"/search\">Another Search</a>");
        resp.getWriter().println("<p><a href=\"/login\">My Home Page</a>");
        resp.getWriter().println(CreateEventConstants.PAGE_FOOTER);
    }

    /**
     * Get the total number of events given the parameters
     * @param title
     * @param description
     * @param date
     * @param location
     * @return
     */
    private int searchAll(String title, String description, Date date, String location) {
        ResultSet resultSet = null;
        int result = 0;
        try {
            Connection con = DBCPDataSource.getConnection();
            if (!title.isEmpty() && description.isEmpty() && date == null && Objects.equals(location, "empty")) {
                // only search for title
                resultSet = JDBCSearchIndividual.getAllEventsGivenTitle(con, title);
                while (resultSet.next()) {
                    result++;
                }
                return result;
            } else if (title.isEmpty() && !description.isEmpty() && date == null && Objects.equals(location, "empty")) {
                // only search for description
                resultSet = JDBCSearchIndividual.getAllEventsGivenDescription(con, description);
                while (resultSet.next()) {
                    result++;
                }
                return result;
            } else if (title.isEmpty() && description.isEmpty() && date != null && Objects.equals(location, "empty")) {
                // only search for date
                resultSet = JDBCSearchIndividual.getAllEventsGivenDate(con, date);
                while (resultSet.next()) {
                    result++;
                }
                return result;
            } else if (title.isEmpty() && description.isEmpty() && date == null && !Objects.equals(location, "empty")) {
                // only search for location
                resultSet = JDBCSearchIndividual.getAllEventsGivenLocation(con, location);
                while (resultSet.next()) {
                    result++;
                }
                return result;
            } else if (!title.isEmpty() && !description.isEmpty() && date == null && Objects.equals(location, "empty")) {
                // search for title and description
                resultSet = JDBCSearchMulti.getAllEventsGivenTitleDescription(con, title, description);
                while (resultSet.next()) {
                    result++;
                }
                return result;
            } else if (!title.isEmpty() && !description.isEmpty() && date != null && Objects.equals(location, "empty")) {
                // search for title, description, and date
                resultSet = JDBCSearchMulti.getAllEventsGivenTitleDescriptionDate(con, title, description, date);
                while (resultSet.next()) {
                    result++;
                }
                return result;
            } else if (!title.isEmpty() && !description.isEmpty() && date != null && !Objects.equals(location, "empty")) {
                // search for all
                resultSet = JDBCSearchMulti.getAllEventsGivenTitleDescriptionDateLocation(con, title, description, date, location);
                while (resultSet.next()) {
                    result++;
                }
                return result;
            } else if (title.isEmpty() && !description.isEmpty() && date != null && !Objects.equals(location, "empty")) {
                // search for description, date, and location
                resultSet = JDBCSearchMulti.getAllEventsGivenDescriptionDateLocation(con, description, date, location);
                while (resultSet.next()) {
                    result++;
                }
                return result;
            } else if (title.isEmpty() && description.isEmpty() && date != null && !Objects.equals(location, "empty")) {
                // search for date, location
                resultSet = JDBCSearchMulti.getAllEventsGivenDateLocation(con, date, location);
                while (resultSet.next()) {
                    result++;
                }
                return result;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Get 5 events per page given the parameters
     * @param title
     * @param description
     * @param date
     * @param location
     * @param page
     * @return
     */
    private ResultSet searchPage(String title, String description, Date date, String location, int page) {
        try {
            Connection con = DBCPDataSource.getConnection();
            if (!title.isEmpty() && description.isEmpty() && date == null && Objects.equals(location, "empty")) {
                // only search for title
                return JDBCSearchIndividual.getLimitEventsGivenTitle(con, title, page);
            } else if (title.isEmpty() && !description.isEmpty() && date == null && Objects.equals(location, "empty")) {
                // only search for description
                return JDBCSearchIndividual.getLimitEventsGivenDescription(con, description, page);
            } else if (title.isEmpty() && description.isEmpty() && date != null && Objects.equals(location, "empty")) {
                // only search for date
                return JDBCSearchIndividual.getLimitEventsGivenDate(con, date, page);
            } else if (title.isEmpty() && description.isEmpty() && date == null && !Objects.equals(location, "empty")) {
                // only search for location
                return JDBCSearchIndividual.getLimitEventsGivenLocation(con, location, page);
            } else if (!title.isEmpty() && !description.isEmpty() && date == null && Objects.equals(location, "empty")) {
                // search for title and description
                return JDBCSearchMulti.getLimitEventsGivenTitleDescription(con, title, description, page);
            } else if (!title.isEmpty() && !description.isEmpty() && date != null && Objects.equals(location, "empty")) {
                // search for title, description, and date
                return JDBCSearchMulti.getLimitEventsGivenTitleDescriptionDate(con, title, description, date, page);
            } else if (!title.isEmpty() && !description.isEmpty() && date != null && !Objects.equals(location, "empty")) {
                // search for all
                return JDBCSearchMulti.getLimitEventsGivenTitleDescriptionDateLocation(con, title, description, date, location, page);
            } else if (title.isEmpty() && !description.isEmpty() && date != null && !Objects.equals(location, "empty")) {
                // search for description, date, and location
                return JDBCSearchMulti.getLimitEventsGivenDescriptionDateLocation(con, description, date, location, page);
            } else if (title.isEmpty() && description.isEmpty() && date != null && !Objects.equals(location, "empty")) {
                // search for date, location
                return JDBCSearchMulti.getLimitEventsGivenDateLocation(con, date, location, page);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
