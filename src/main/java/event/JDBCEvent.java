package event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class JDBCEvent {

    public static String getSelectTitleGivenEventId(Connection con, int eventId) throws SQLException {
        String selectAllTitles = "SELECT title FROM events WHERE id=?;";
        PreparedStatement selectAllTitlesStmt = con.prepareStatement(selectAllTitles);
        selectAllTitlesStmt.setInt(1, eventId);
        ResultSet results = selectAllTitlesStmt.executeQuery();
        if (results.next()) {
            return results.getString("title");
        }
        return "";
    }

    public static ResultSet getEventGivenEventId(Connection con, int eventId) throws SQLException {
        String selectAllEvents = "SELECT * FROM events WHERE id=?;";
        PreparedStatement selectAllEventsStmt = con.prepareStatement(selectAllEvents);
        selectAllEventsStmt.setInt(1, eventId);
        return selectAllEventsStmt.executeQuery();

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
}
