package event;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;

public class JDBCEvent {

    public static void executeInsertEvent(Connection con, int userId, String title, String description, Date eventDate, int numOfTickets, double price, String location) throws SQLException {
        String insertEventSql = "INSERT INTO events (title, description, event_date, tickets_avaiable, total_tickets, price, location, creator_id) VALUES (?,?,?,?,?,?,?,?);";
        PreparedStatement insertEventStmt = con.prepareStatement(insertEventSql);
        insertEventStmt.setString(1, title);
        insertEventStmt.setString(2, description);
        insertEventStmt.setDate(3, eventDate);
        insertEventStmt.setInt(4, numOfTickets);
        insertEventStmt.setInt(5, numOfTickets);
        insertEventStmt.setDouble(6, price);
        insertEventStmt.setString(7, location);
        insertEventStmt.setInt(8, userId);
        insertEventStmt.executeUpdate();
    }

    public static void executeInsertEventWithImage(Connection con, int userId, String title, String description, Date eventDate, int numOfTickets, double price, String location, InputStream image) throws SQLException {
        String insertEventSql = "INSERT INTO events (title, description, event_date, tickets_avaiable, total_tickets, price, location, creator_id, event_image) VALUES (?,?,?,?,?,?,?,?,?);";
        PreparedStatement insertEventStmt = con.prepareStatement(insertEventSql);
        insertEventStmt.setString(1, title);
        insertEventStmt.setString(2, description);
        insertEventStmt.setDate(3, eventDate);
        insertEventStmt.setInt(4, numOfTickets);
        insertEventStmt.setInt(5, numOfTickets);
        insertEventStmt.setDouble(6, price);
        insertEventStmt.setString(7, location);
        insertEventStmt.setInt(8, userId);
        insertEventStmt.setBlob(9, image);
        insertEventStmt.executeUpdate();
    }

    public static void executeInsertImage(Connection con, String image, String title) throws SQLException {
        String insertImageSql = "UPDATE events SET event_image=? WHERE title=?;";
        PreparedStatement insertImageStmt = con.prepareStatement(insertImageSql);
        insertImageStmt.setString(1, image);
        insertImageStmt.setString(2, title);
        insertImageStmt.executeUpdate();
    }

    public static String getTitleGivenEventId(Connection con, int eventId) throws SQLException {
        String selectAllTitles = "SELECT title FROM events WHERE id=?;";
        PreparedStatement selectAllTitlesStmt = con.prepareStatement(selectAllTitles);
        selectAllTitlesStmt.setInt(1, eventId);
        ResultSet results = selectAllTitlesStmt.executeQuery();
        if (results.next()) {
            return results.getString("title");
        }
        return "";
    }

    public static ResultSet getAllEvents(Connection con, Date currDate) throws SQLException {
        String selectAllEvents = "SELECT * FROM events WHERE event_date>?;";
        PreparedStatement selectAllEventsStmt = con.prepareStatement(selectAllEvents);
        selectAllEventsStmt.setDate(1, currDate);
        return selectAllEventsStmt.executeQuery();
    }

    public static ResultSet getEventGivenEventId(Connection con, int eventId) throws SQLException {
        String selectAllEvents = "SELECT * FROM events WHERE id=?;";
        PreparedStatement selectAllEventsStmt = con.prepareStatement(selectAllEvents);
        selectAllEventsStmt.setInt(1, eventId);
        return selectAllEventsStmt.executeQuery();

    }

    public static int getNumTicketsGivenEventId(Connection con, int eventId, int userId) throws SQLException {
        String selectNumTicket = "SELECT ticket_num FROM user_to_event WHERE event_id=? AND user_id=?;";
        PreparedStatement selectNumTicketStmt = con.prepareStatement(selectNumTicket);
        selectNumTicketStmt.setInt(1, eventId);
        selectNumTicketStmt.setInt(2, userId);
        ResultSet results = selectNumTicketStmt.executeQuery();
        if (results.next()) {
            return results.getInt("ticket_num");
        }
        return -1;
    }

    public static ArrayList<Integer> getEventIdsGivenSession(Connection con, String sessionId) throws SQLException {
        String selectAllEventId = "SELECT * FROM user_to_event WHERE user_id=(SELECT user_id FROM users WHERE email=(SELECT email FROM sessions WHERE session_id=?));";
        PreparedStatement selectAllEventIdStmt = con.prepareStatement(selectAllEventId);
        selectAllEventIdStmt.setString(1, sessionId);
        ResultSet results = selectAllEventIdStmt.executeQuery();
        ArrayList<Integer> eventIds = new ArrayList<>();
        while (results.next()) {
            eventIds.add(results.getInt("event_id"));
        }
        return eventIds;
    }

    public static int getEventIdGivenUserId(Connection con, int userId) throws SQLException {
        String selectEventId = "SELECT event_id FROM user_to_event WHERE user_id=?;";
        PreparedStatement selectEventIdStmt = con.prepareStatement(selectEventId);
        selectEventIdStmt.setInt(1, userId);
        ResultSet result = selectEventIdStmt.executeQuery();
        if (result.next()) {
            return result.getInt("event_id");
        }
        return -1;
    }




}
