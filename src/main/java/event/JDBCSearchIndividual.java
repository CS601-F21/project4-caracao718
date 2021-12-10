package event;

import java.sql.*;

/**
 * A class that contains SQL queries to connect to a MySQL database using JDBC
 * SQL queries for Search Servlet given a single parameter
 */
public class JDBCSearchIndividual {

    /**
     * Search all events given title
     * @param con
     * @param title
     * @return
     * @throws SQLException
     */
    public static ResultSet getAllEventsGivenTitle(Connection con, String title) throws SQLException {
        String selectAllEvents = "SELECT * FROM events WHERE title LIKE ?;";
        PreparedStatement selectAllEventsStmt = con.prepareStatement(selectAllEvents);
        selectAllEventsStmt.setString(1, "%" + title + "%");
        return selectAllEventsStmt.executeQuery();
    }

    /**
     * Search all events given description
     * @param con
     * @param description
     * @return
     * @throws SQLException
     */
    public static ResultSet getAllEventsGivenDescription(Connection con, String description) throws SQLException {
        String selectAllEvents = "SELECT * FROM events WHERE description LIKE ?;";
        PreparedStatement selectAllEventsStmt = con.prepareStatement(selectAllEvents);
        selectAllEventsStmt.setString(1, "%" + description + "%");
        return selectAllEventsStmt.executeQuery();
    }

    /**
     * Search all events given location
     * @param con
     * @param location
     * @return
     * @throws SQLException
     */
    public static ResultSet getAllEventsGivenLocation(Connection con, String location) throws SQLException {
        String selectAllEvents = "SELECT * FROM events WHERE location LIKE ?;";
        PreparedStatement selectAllEventsStmt = con.prepareStatement(selectAllEvents);
        selectAllEventsStmt.setString(1, "%" + location + "%");
        return selectAllEventsStmt.executeQuery();
    }

    /**
     * Search all events given date
     * @param con
     * @param date
     * @return
     * @throws SQLException
     */
    public static ResultSet getAllEventsGivenDate(Connection con, Date date) throws SQLException {
        String selectAllEvents = "SELECT * FROM events WHERE event_date>?;";
        PreparedStatement selectAllEventsStmt = con.prepareStatement(selectAllEvents);
        selectAllEventsStmt.setDate(1, date);
        return selectAllEventsStmt.executeQuery();
    }

    /**
     * Search limit (page) events given title
     * @param con
     * @param title
     * @return
     * @throws SQLException
     */
    public static ResultSet getLimitEventsGivenTitle(Connection con, String title, int offset) throws SQLException {
        String selectLimitEvents = "SELECT * FROM events WHERE title LIKE ? LIMIT 5 OFFSET ?;";
        PreparedStatement selectLimitEventsStmt = con.prepareStatement(selectLimitEvents);
        selectLimitEventsStmt.setString(1, "%" + title + "%");
        selectLimitEventsStmt.setInt(2, offset);
        return selectLimitEventsStmt.executeQuery();
    }

    /**
     * Search limit (page) events given description
     * @param con
     * @param description
     * @return
     * @throws SQLException
     */
    public static ResultSet getLimitEventsGivenDescription(Connection con, String description, int offset) throws SQLException {
        String selectLimitEvents = "SELECT * FROM events WHERE description LIKE ? LIMIT 5 OFFSET ?;";
        PreparedStatement selectLimitEventsStmt = con.prepareStatement(selectLimitEvents);
        selectLimitEventsStmt.setString(1, "%" + description + "%");
        selectLimitEventsStmt.setInt(2, offset);
        return selectLimitEventsStmt.executeQuery();
    }

    /**
     * Search limit (page) events given location
     * @param con
     * @param location
     * @return
     * @throws SQLException
     */
    public static ResultSet getLimitEventsGivenLocation(Connection con, String location, int offset) throws SQLException {
        String selectLimitEvents = "SELECT * FROM events WHERE location LIKE ? LIMIT 5 OFFSET ?;";
        PreparedStatement selectLimitEventsStmt = con.prepareStatement(selectLimitEvents);
        selectLimitEventsStmt.setString(1, "%" + location + "%");
        selectLimitEventsStmt.setInt(2, offset);
        return selectLimitEventsStmt.executeQuery();
    }

    /**
     * Search limit (page) events given date
     * @param con
     * @param date
     * @return
     * @throws SQLException
     */
    public static ResultSet getLimitEventsGivenDate(Connection con, Date date, int offset) throws SQLException {
        String selectLimitEvents = "SELECT * FROM events WHERE event_date>? LIMIT 5 OFFSET ?;";
        PreparedStatement selectLimitEventsStmt = con.prepareStatement(selectLimitEvents);
        selectLimitEventsStmt.setDate(1, date);
        selectLimitEventsStmt.setInt(2, offset);
        return selectLimitEventsStmt.executeQuery();
    }



}
