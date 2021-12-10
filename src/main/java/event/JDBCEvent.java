package event;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;

/**
 * A class that contains SQL queries to connect to a MySQL database using JDBC
 * SQL queries for Event Servlet
 */
public class JDBCEvent {
    /**
     * A method to insert new event in the events table
     * @param con
     * @param userId
     * @param title
     * @param description
     * @param eventDate
     * @param numOfTickets
     * @param price
     * @param location
     * @throws SQLException
     */
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

    /**
     * A method that updates one event in the events table
     * @param con
     * @param eventId
     * @param title
     * @param description
     * @param eventDate
     * @param numOfTickets
     * @param price
     * @param location
     * @throws SQLException
     */
    public static void updateEvent(Connection con, int eventId, int availableTickets, String title, String description, Date eventDate, int numOfTickets, double price, String location) throws SQLException {
        String updateEventSql = "UPDATE events SET title=?, description=?, event_date=?, tickets_avaiable=?, total_tickets=?, price=?, location=? WHERE id=?;";
        PreparedStatement updateEventStmt = con.prepareStatement(updateEventSql);
        updateEventStmt.setString(1, title);
        updateEventStmt.setString(2, description);
        updateEventStmt.setDate(3, eventDate);
        updateEventStmt.setInt(4, availableTickets);
        updateEventStmt.setInt(5, numOfTickets);
        updateEventStmt.setDouble(6, price);
        updateEventStmt.setString(7, location);
        updateEventStmt.setInt(8, eventId);
        updateEventStmt.executeUpdate();
    }


    /**
     * A method to retrieve the event title in the events table, given the eventId
     * @param con
     * @param eventId
     * @return
     * @throws SQLException
     */
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

    /**
     * Retrieve all events after current day
     * @param con
     * @param currDate
     * @return
     * @throws SQLException
     */
    public static ResultSet getAllEvents(Connection con, Date currDate) throws SQLException {
        String selectAllEvents = "SELECT * FROM events WHERE event_date>?;";
        PreparedStatement selectAllEventsStmt = con.prepareStatement(selectAllEvents);
        selectAllEventsStmt.setDate(1, currDate);
        return selectAllEventsStmt.executeQuery();
    }

    /**
     * Retrieve limited events to display on one page
     * @param con
     * @param currDate
     * @param offset
     * @return
     * @throws SQLException
     */
    public static ResultSet getLimitEvents(Connection con, Date currDate, int offset) throws SQLException {
        String selectLimitEvents = "SELECT * FROM events WHERE event_date>? LIMIT 5 OFFSET ?;";
        PreparedStatement selectLimitEventsStmt = con.prepareStatement(selectLimitEvents);
        selectLimitEventsStmt.setDate(1, currDate);
        selectLimitEventsStmt.setInt(2, offset);
        return selectLimitEventsStmt.executeQuery();
    }

    /**
     * Retrieve event given event id
     * @param con
     * @param eventId
     * @return
     * @throws SQLException
     */
    public static ResultSet getEventGivenEventId(Connection con, int eventId) throws SQLException {
        String selectAllEvents = "SELECT * FROM events WHERE id=?;";
        PreparedStatement selectAllEventsStmt = con.prepareStatement(selectAllEvents);
        selectAllEventsStmt.setInt(1, eventId);
        return selectAllEventsStmt.executeQuery();
    }

    /**
     * Get all events that a user created from events table
     * @param con
     * @param userId
     * @return
     * @throws SQLException
     */
    public static ResultSet getEventGivenUserId(Connection con, int userId) throws SQLException {
        String selectEvents = "SELECT * FROM events WHERE creator_id=?;";
        PreparedStatement selectEventsStmt = con.prepareStatement(selectEvents);
        selectEventsStmt.setInt(1, userId);
        return selectEventsStmt.executeQuery();
    }

    /**
     * Retrieve the number of tickets for a event that a user have
     * @param con
     * @param eventId
     * @param userId
     * @return
     * @throws SQLException
     */
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

    /**
     * Return the current available ticket for an event
     * @param con
     * @param eventId
     * @return
     * @throws SQLException
     */
    public static int getAvailableTicketsGivenEventId(Connection con, int eventId) throws SQLException {
        String selectNumTicket = "SELECT tickets_avaiable FROM events WHERE id=?;";
        PreparedStatement selectNumTicketStmt = con.prepareStatement(selectNumTicket);
        selectNumTicketStmt.setInt(1, eventId);
        ResultSet results = selectNumTicketStmt.executeQuery();
        if (results.next()) {
            return results.getInt("tickets_avaiable");
        }
        return -1;
    }

    /**
     * Return the total tickets for an event
     * @param con
     * @param eventId
     * @return
     * @throws SQLException
     */
    public static int getTotalTicketsGivenEventId(Connection con, int eventId) throws SQLException {
        String selectNumTicket = "SELECT total_tickets FROM events WHERE id=?;";
        PreparedStatement selectNumTicketStmt = con.prepareStatement(selectNumTicket);
        selectNumTicketStmt.setInt(1, eventId);
        ResultSet results = selectNumTicketStmt.executeQuery();
        if (results.next()) {
            return results.getInt("total_tickets");
        }
        return -1;
    }

    /**
     * Retrieve event id given session id
     * @param con
     * @param sessionId
     * @return
     * @throws SQLException
     */
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

    /**
     * A method to update the tickets_available number in events DB
     */
    public static void updateTicketsAvailable(Connection con, int eventId, int newNumber) throws SQLException {
        String updateTickets = "UPDATE events SET tickets_avaiable=? WHERE id=?;";
        PreparedStatement updateTicketsStmt = con.prepareStatement(updateTickets);
        updateTicketsStmt.setInt(1, newNumber);
        updateTicketsStmt.setInt(2, eventId);
        updateTicketsStmt.executeUpdate();
    }

    /**
     * A method that retrieves the ticket_num value given user_id and event_id
     */
    public static int getTicketsGivenUserAndEventID(Connection con, int userId, int eventId) throws SQLException {
        String selectTickets = "SELECT ticket_num FROM user_to_event WHERE user_id=? AND event_id=?;";
        PreparedStatement selectTicketsStmt = con.prepareStatement(selectTickets);
        selectTicketsStmt.setInt(1, userId);
        selectTicketsStmt.setInt(2, eventId);
        ResultSet result = selectTicketsStmt.executeQuery();
        if (result.next()) {
            return result.getInt("ticket_num");
        }
        return -1;
    }

    /**
     * A method to Update the ticket_num value given user_id and event_id
     */
    public static void updateTicketsNum(Connection con, int userId, int eventId, int newNumber) throws SQLException {
        String updateTickets = "UPDATE user_to_event SET ticket_num=? WHERE user_id=? AND event_id=?;";
        PreparedStatement updateTicketsStmt = con.prepareStatement(updateTickets);
        updateTicketsStmt.setInt(1, newNumber);
        updateTicketsStmt.setInt(2, userId);
        updateTicketsStmt.setInt(3, eventId);
        updateTicketsStmt.executeUpdate();
    }

    /**
     * Check if the userId and eventId row exists in the user_to_event table
     */
    public static boolean checkUserEvent(Connection con, int userId, int eventId) throws SQLException {
        String checkUserEvent = "SELECT EXISTS(SELECT * FROM user_to_event WHERE user_id=? AND event_id=?);";
        PreparedStatement checkUserEventStmt = con.prepareStatement(checkUserEvent);
        checkUserEventStmt.setInt(1, userId);
        checkUserEventStmt.setInt(2, eventId);
        ResultSet resultSet = checkUserEventStmt.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt(1) != 0;
        }
        return false;
    }

    /**
     * Insert a new row in the user_to_event table after a user purchased a new event
     */
    public static void insertUserAndEvent(Connection con, int userId, int eventId, int tickets) throws SQLException {
        String insertUserAndEvent = "INSERT INTO user_to_event (user_id, event_id, ticket_num) VALUES (?, ?, ?);";
        PreparedStatement insertUserAndEventStmt = con.prepareStatement(insertUserAndEvent);
        insertUserAndEventStmt.setInt(1, userId);
        insertUserAndEventStmt.setInt(2, eventId);
        insertUserAndEventStmt.setInt(3, tickets);
        insertUserAndEventStmt.executeUpdate();
    }

    /**
     * Delete a row when the ticket_num is zero or below
     */
    public static void deleteUserAndEvent(Connection con, int userId, int eventId) throws SQLException {
        String deleteRow = "DELETE FROM user_to_event WHERE user_id=? AND event_id=?;";
        PreparedStatement deleteRowStmt = con.prepareStatement(deleteRow);
        deleteRowStmt.setInt(1, userId);
        deleteRowStmt.setInt(2, eventId);
        deleteRowStmt.executeUpdate();
    }

    /**
     * Delete the event in the events table given event_id
     */
    public static void deleteEventInEvents(Connection con, int eventId) throws SQLException {
        String deleteRow = "DELETE FROM events WHERE id=?;";
        PreparedStatement deleteRowStmt = con.prepareStatement(deleteRow);
        deleteRowStmt.setInt(1, eventId);
        deleteRowStmt.executeUpdate();
    }

    /**
     * Delete all rows with event_id in table user_to_event
     */
    public static void deleteEventInUserToEvent(Connection con, int eventId) throws SQLException {
        String deleteRow = "DELETE FROM user_to_event WHERE event_id=?;";
        PreparedStatement deleteRowStmt = con.prepareStatement(deleteRow);
        deleteRowStmt.setInt(1, eventId);
        deleteRowStmt.executeUpdate();
    }
}
