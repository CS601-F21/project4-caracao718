package event;

import java.sql.*;

public class JDBCSearchMulti {
    /**
     * Retrieve all events after given date
     * @param con
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
     * Retrieve all events which contains title key terms, along with the date
     * @param con
     * @param title
     * @param date
     * @return
     * @throws SQLException
     */
    public static ResultSet getAllEventsGivenTitleDate(Connection con, String title, Date date) throws SQLException {
        String selectAllEvents = "SELECT * FROM events WHERE title LIKE ? AND event_date>?;";
        PreparedStatement selectAllEventsStmt = con.prepareStatement(selectAllEvents);
        selectAllEventsStmt.setString(1, "%" + title + "%");
        selectAllEventsStmt.setDate(2, date);
        return selectAllEventsStmt.executeQuery();
    }

    /**
     * Retrieve all events which contains description key terms, along with the date
     * @param con
     * @param description
     * @param date
     * @return
     * @throws SQLException
     */
    public static ResultSet getAllEventsGivenDescriptionDate(Connection con, String description, Date date) throws SQLException {
        String selectAllEvents = "SELECT * FROM events WHERE description LIKE ? AND event_date>?;";
        PreparedStatement selectAllEventsStmt = con.prepareStatement(selectAllEvents);
        selectAllEventsStmt.setString(1, "%" + description + "%");
        selectAllEventsStmt.setDate(2, date);
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
        String selectAllEvents = "SELECT * FROM events WHERE description LIKE ? AND event_date>? AND location LIKE ?;";
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
        String selectAllEvents = "SELECT * FROM events WHERE location LIKE ? AND event_date>?;";
        PreparedStatement selectAllEventsStmt = con.prepareStatement(selectAllEvents);
        selectAllEventsStmt.setDate(2, date);
        selectAllEventsStmt.setString(1, "%" + location + "%");
        return selectAllEventsStmt.executeQuery();
    }

    /**
     * Retrieve all events which contains title, description key terms, along with the date
     * @param con
     * @param title
     * @param date
     * @return
     * @throws SQLException
     */
    public static ResultSet getAllEventsGivenTitleDescriptionDate(Connection con, String title, String description, Date date) throws SQLException {
        String selectAllEvents = "SELECT * FROM events WHERE title LIKE ? AND event_date>? AND description LIKE ?;";
        PreparedStatement selectAllEventsStmt = con.prepareStatement(selectAllEvents);
        selectAllEventsStmt.setString(1, "%" + title + "%");
        selectAllEventsStmt.setDate(2, date);
        selectAllEventsStmt.setString(3, "%" + description + "%");
        return selectAllEventsStmt.executeQuery();
    }

    /**
     * Retrieve all events which contains title, location key terms, along with the date
     * @param con
     * @param title
     * @param date
     * @return
     * @throws SQLException
     */
    public static ResultSet getAllEventsGivenTitleLocationDate(Connection con, String title, String location, Date date) throws SQLException {
        String selectAllEvents = "SELECT * FROM events WHERE title LIKE ? AND event_date>? AND location LIKE ? ;";
        PreparedStatement selectAllEventsStmt = con.prepareStatement(selectAllEvents);
        selectAllEventsStmt.setString(1, "%" + title + "%");
        selectAllEventsStmt.setDate(2, date);
        selectAllEventsStmt.setString(3, "%" + location + "%");
        return selectAllEventsStmt.executeQuery();
    }

    /**
     * Retrieve all events which contains title, description, location key terms, along with the date
     * @param con
     * @param title
     * @param date
     * @return
     * @throws SQLException
     */
    public static ResultSet getAllEventsGivenTitleLocationDescriptionDate(Connection con, String title, String location, String description, Date date) throws SQLException {
        String selectAllEvents = "SELECT * FROM events WHERE title LIKE ? AND event_date>? AND location LIKE ? AND description LIKE ?;";
        PreparedStatement selectAllEventsStmt = con.prepareStatement(selectAllEvents);
        selectAllEventsStmt.setString(1, "%" + title + "%");
        selectAllEventsStmt.setDate(2, date);
        selectAllEventsStmt.setString(3, "%" + location + "%");
        selectAllEventsStmt.setString(4, "%" + description + "%");
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
        String selectAllEvents = "SELECT * FROM events WHERE title LIKE ? AND event_date>? AND description LIKE ? AND location LIKE ?;";
        PreparedStatement selectAllEventsStmt = con.prepareStatement(selectAllEvents);
        selectAllEventsStmt.setString(1, "%" + title + "%");
        selectAllEventsStmt.setDate(2, date);
        selectAllEventsStmt.setString(3, "%" + description + "%");
        selectAllEventsStmt.setString(4, "%" + location + "%");
        return selectAllEventsStmt.executeQuery();
    }


    /**
     * Retrieve 5 events which contains title and description key terms, date and the offset
     * @param con
     * @param title
     * @param description
     * @param offset
     * @return
     * @throws SQLException
     */
    public static ResultSet getLimitEventsGivenTitleDescriptionDate(Connection con, String title, String description, Date date, int offset) throws SQLException {
        String selectLimitEvents = "SELECT * FROM events WHERE title LIKE ? AND event_date>? AND description LIKE ? LIMIT 5 OFFSET ?;";
        PreparedStatement selectLimitEventsStmt = con.prepareStatement(selectLimitEvents);
        selectLimitEventsStmt.setString(1, "%" + title + "%");
        selectLimitEventsStmt.setDate(2, date);
        selectLimitEventsStmt.setString(3, "%" + description + "%");
        selectLimitEventsStmt.setInt(4, offset);
        return selectLimitEventsStmt.executeQuery();
    }

    /**
     * Retrieve all events which contains title and location key terms, along with the date, and the offset
     * @param con
     * @param title
     * @param location
     * @param date
     * @param offset
     * @return
     * @throws SQLException
     */
    public static ResultSet getLimitEventsGivenTitleLocationDate(Connection con, String title, String location, Date date, int offset) throws SQLException {
        String selectAllEvents = "SELECT * FROM events WHERE title LIKE ? AND event_date>? AND location LIKE ? LIMIT 5 OFFSET ?;";
        PreparedStatement selectAllEventsStmt = con.prepareStatement(selectAllEvents);
        selectAllEventsStmt.setString(1, "%" + title + "%");
        selectAllEventsStmt.setDate(2, date);
        selectAllEventsStmt.setString(3, "%" + location + "%");
        selectAllEventsStmt.setInt(4, offset);
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
        String selectAllEvents = "SELECT * FROM events WHERE title LIKE ? AND event_date>? AND description LIKE ? AND location LIKE ? LIMIT 5 OFFSET ?;";
        PreparedStatement selectAllEventsStmt = con.prepareStatement(selectAllEvents);
        selectAllEventsStmt.setString(1, "%" + title + "%");
        selectAllEventsStmt.setDate(2, date);
        selectAllEventsStmt.setString(3, "%" + description + "%");
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
        String selectAllEvents = "SELECT * FROM events WHERE description LIKE ? AND event_date>? AND location LIKE ? LIMIT 5 OFFSET ?;";
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
        String selectAllEvents = "SELECT * FROM events WHERE location LIKE ? AND event_date>? LIMIT 5 OFFSET ?;";
        PreparedStatement selectAllEventsStmt = con.prepareStatement(selectAllEvents);
        selectAllEventsStmt.setDate(2, date);
        selectAllEventsStmt.setString(1, "%" + location + "%");
        selectAllEventsStmt.setInt(3, offset);
        return selectAllEventsStmt.executeQuery();
    }

    /**
     * Retrieve all events given date and description, and the offset
     * @param con
     * @param date
     * @param description
     * @param offset
     * @return
     * @throws SQLException
     */
    public static ResultSet getLimitEventsGivenDateDescription(Connection con, Date date, String description, int offset) throws SQLException {
        String selectAllEvents = "SELECT * FROM events WHERE description LIKE ? AND event_date>? LIMIT 5 OFFSET ?;";
        PreparedStatement selectAllEventsStmt = con.prepareStatement(selectAllEvents);
        selectAllEventsStmt.setDate(2, date);
        selectAllEventsStmt.setString(1, "%" + description + "%");
        selectAllEventsStmt.setInt(3, offset);
        return selectAllEventsStmt.executeQuery();
    }

    /**
     * Retrieve limit events given date and title, and the offset
     * @param con
     * @param date
     * @param title
     * @param offset
     * @return
     * @throws SQLException
     */
    public static ResultSet getLimitEventsGivenDateTitle(Connection con, Date date, String title, int offset) throws SQLException {
        String selectAllEvents = "SELECT * FROM events WHERE title LIKE ? AND event_date>? LIMIT 5 OFFSET ?;";
        PreparedStatement selectAllEventsStmt = con.prepareStatement(selectAllEvents);
        selectAllEventsStmt.setDate(2, date);
        selectAllEventsStmt.setString(1, "%" + title + "%");
        selectAllEventsStmt.setInt(3, offset);
        return selectAllEventsStmt.executeQuery();
    }

    /**
     * Retrieve limit events after given date, and the offset
     * @param con
     * @return
     * @throws SQLException
     */
    public static ResultSet getLimitEventsGivenDate(Connection con, Date date, int offset) throws SQLException {
        String selectAllEvents = "SELECT * FROM events WHERE event_date>? LIMIT 5 OFFSET ?;";
        PreparedStatement selectAllEventsStmt = con.prepareStatement(selectAllEvents);
        selectAllEventsStmt.setDate(1, date);
        selectAllEventsStmt.setInt(2, offset);
        return selectAllEventsStmt.executeQuery();
    }

}
