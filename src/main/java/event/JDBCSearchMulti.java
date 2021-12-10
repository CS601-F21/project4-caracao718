package event;

import java.sql.*;

public class JDBCSearchMulti {
    /**
     * Retrieve all events which contains title and description key terms
     * @param con
     * @param title
     * @param description
     * @return
     * @throws SQLException
     */
    public static ResultSet getAllEventsGivenTitleDescription(Connection con, String title, String description) throws SQLException {
        String selectAllEvents = "SELECT * FROM events WHERE title LIKE ? UNION SELECT * FROM events WHERE description LIKE ?;";
        PreparedStatement selectAllEventsStmt = con.prepareStatement(selectAllEvents);
        selectAllEventsStmt.setString(1, "%" + title + "%");
        selectAllEventsStmt.setString(2, description);
        return selectAllEventsStmt.executeQuery();
    }

    /**
     * Retrieve all events which contains title and description key terms, along with the date
     * @param con
     * @param title
     * @param description
     * @param date
     * @return
     * @throws SQLException
     */
    public static ResultSet getAllEventsGivenTitleDescriptionDate(Connection con, String title, String description, Date date) throws SQLException {
        String selectAllEvents = "SELECT * FROM events WHERE title LIKE ? UNION SELECT * FROM events WHERE description LIKE ? UNION SELECT * FROM events WHERE event_date>?;";
        PreparedStatement selectAllEventsStmt = con.prepareStatement(selectAllEvents);
        selectAllEventsStmt.setString(1, "%" + title + "%");
        selectAllEventsStmt.setString(2, "%" + description + "%");
        selectAllEventsStmt.setDate(3, date);
        return selectAllEventsStmt.executeQuery();
    }

    /**
     * Retrieve all events which contains title and description key terms, along with the date and location
     * @param con
     * @param title
     * @param description
     * @param date
     * @param location
     * @return
     * @throws SQLException
     */
    public static ResultSet getAllEventsGivenTitleDescriptionDateLocation(Connection con, String title, String description, Date date, String location) throws SQLException {
        String selectAllEvents = "SELECT * FROM events WHERE title LIKE ? UNION SELECT * FROM events WHERE description LIKE ? UNION SELECT * FROM events WHERE event_date>? UNION SELECT * FROM events WHERE location LIKE ?;";
        PreparedStatement selectAllEventsStmt = con.prepareStatement(selectAllEvents);
        selectAllEventsStmt.setString(1, "%" + title + "%");
        selectAllEventsStmt.setString(2, "%" + description + "%");
        selectAllEventsStmt.setDate(3, date);
        selectAllEventsStmt.setString(4, "%" + location + "%");
        return selectAllEventsStmt.executeQuery();
    }

    /**
     * Retrieve all events which contains description key terms, along with the date and location
     * @param con
     * @param description
     * @param date
     * @param location
     * @return
     * @throws SQLException
     */
    public static ResultSet getAllEventsGivenDescriptionDateLocation(Connection con, String description, Date date, String location) throws SQLException {
        String selectAllEvents = "SELECT * FROM events WHERE description LIKE ? UNION SELECT * FROM events WHERE event_date>? UNION SELECT * FROM events WHERE location LIKE ?;";
        PreparedStatement selectAllEventsStmt = con.prepareStatement(selectAllEvents);
        selectAllEventsStmt.setString(1, "%" + description + "%");
        selectAllEventsStmt.setDate(2, date);
        selectAllEventsStmt.setString(3, "%" + location + "%");
        return selectAllEventsStmt.executeQuery();
    }

    /**
     * Retrieve all events given date and location
     * @param con
     * @param date
     * @param location
     * @return
     * @throws SQLException
     */
    public static ResultSet getAllEventsGivenDateLocation(Connection con, Date date, String location) throws SQLException {
        String selectAllEvents = "SELECT * FROM events WHERE event_date>? UNION SELECT * FROM events WHERE location LIKE ?;";
        PreparedStatement selectAllEventsStmt = con.prepareStatement(selectAllEvents);
        selectAllEventsStmt.setDate(1, date);
        selectAllEventsStmt.setString(2, "%" + location + "%");
        return selectAllEventsStmt.executeQuery();
    }

    /**
     * Retrieve 5 events which contains title and description key terms, and the offset
     * @param con
     * @param title
     * @param description
     * @param offset
     * @return
     * @throws SQLException
     */
    public static ResultSet getLimitEventsGivenTitleDescription(Connection con, String title, String description, int offset) throws SQLException {
        String selectLimitEvents = "SELECT * FROM events WHERE title LIKE ? UNION SELECT * FROM events WHERE description LIKE ? LIMIT 5 OFFSET ?;";
        PreparedStatement selectLimitEventsStmt = con.prepareStatement(selectLimitEvents);
        selectLimitEventsStmt.setString(1, "%" + title + "%");
        selectLimitEventsStmt.setString(2, description);
        selectLimitEventsStmt.setInt(3, offset);
        return selectLimitEventsStmt.executeQuery();
    }

    /**
     * Retrieve all events which contains title and description key terms, along with the date, and the offset
     * @param con
     * @param title
     * @param description
     * @param date
     * @param offset
     * @return
     * @throws SQLException
     */
    public static ResultSet getLimitEventsGivenTitleDescriptionDate(Connection con, String title, String description, Date date, int offset) throws SQLException {
        String selectAllEvents = "SELECT * FROM events WHERE title LIKE ? AND event_date>? UNION SELECT * FROM events WHERE description LIKE ? AND event_date>? LIMIT 5 OFFSET ?;";
        PreparedStatement selectAllEventsStmt = con.prepareStatement(selectAllEvents);
        selectAllEventsStmt.setString(1, "%" + title + "%");
        selectAllEventsStmt.setDate(2, date);
        selectAllEventsStmt.setString(3, "%" + description + "%");
        selectAllEventsStmt.setDate(4, date);
        selectAllEventsStmt.setInt(5, offset);
        return selectAllEventsStmt.executeQuery();
    }

    /**
     * Retrieve all events which contains title and description key terms, along with the date and location, and the offset
     * @param con
     * @param title
     * @param description
     * @param date
     * @param location
     * @param offset
     * @return
     * @throws SQLException
     */
    public static ResultSet getLimitEventsGivenTitleDescriptionDateLocation(Connection con, String title, String description, Date date, String location, int offset) throws SQLException {
        String selectAllEvents = "SELECT * FROM events WHERE title LIKE ? UNION SELECT * FROM events WHERE description LIKE ? UNION SELECT * FROM events WHERE event_date>? UNION SELECT * FROM events WHERE location LIKE ? LIMIT 5 OFFSET ?;";
        PreparedStatement selectAllEventsStmt = con.prepareStatement(selectAllEvents);
        selectAllEventsStmt.setString(1, "%" + title + "%");
        selectAllEventsStmt.setString(2, "%" + description + "%");
        selectAllEventsStmt.setDate(3, date);
        selectAllEventsStmt.setString(4, "%" + location + "%");
        selectAllEventsStmt.setInt(5, offset);
        return selectAllEventsStmt.executeQuery();
    }

    /**
     * Retrieve all events which contains description key terms, along with the date and location, and the offset
     * @param con
     * @param description
     * @param date
     * @param location
     * @param offset
     * @return
     * @throws SQLException
     */
    public static ResultSet getLimitEventsGivenDescriptionDateLocation(Connection con, String description, Date date, String location, int offset) throws SQLException {
        String selectAllEvents = "SELECT * FROM events WHERE description LIKE ? UNION SELECT * FROM events WHERE event_date>? UNION SELECT * FROM events WHERE location LIKE ? LIMIT 5 OFFSET ?;";
        PreparedStatement selectAllEventsStmt = con.prepareStatement(selectAllEvents);
        selectAllEventsStmt.setString(1, "%" + description + "%");
        selectAllEventsStmt.setDate(2, date);
        selectAllEventsStmt.setString(3, "%" + location + "%");
        selectAllEventsStmt.setInt(4, offset);
        return selectAllEventsStmt.executeQuery();
    }

    /**
     * Retrieve all events given date and location, and the offset
     * @param con
     * @param date
     * @param location
     * @param offset
     * @return
     * @throws SQLException
     */
    public static ResultSet getLimitEventsGivenDateLocation(Connection con, Date date, String location, int offset) throws SQLException {
        String selectAllEvents = "SELECT * FROM events WHERE event_date>? UNION SELECT * FROM events WHERE location LIKE ? LIMIT 5 OFFSET ?;";
        PreparedStatement selectAllEventsStmt = con.prepareStatement(selectAllEvents);
        selectAllEventsStmt.setDate(1, date);
        selectAllEventsStmt.setString(2, "%" + location + "%");
        selectAllEventsStmt.setInt(3, offset);
        return selectAllEventsStmt.executeQuery();
    }
}
