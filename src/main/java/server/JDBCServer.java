package server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCServer {

    /**
     * A method using a PreparedStatement to execute a database insert.
     * @param con
     * @param sessionID
     * @throws SQLException
     */
    public static void executeInsertSession(Connection con, String sessionID) throws SQLException {
        String insertContactSql = "INSERT INTO sessions (session_id) VALUES (?);";
        PreparedStatement insertContactStmt = con.prepareStatement(insertContactSql);
        insertContactStmt.setString(1, sessionID);
        insertContactStmt.executeUpdate();
    }

    public static void executeInsertSession(Connection con, String sessionID, String email) throws SQLException {
        String insertContactSql = "UPDATE sessions SET email=? WHERE session_id=?;";
        PreparedStatement insertContactStmt = con.prepareStatement(insertContactSql);
        insertContactStmt.setString(1, email);
        insertContactStmt.setString(2, sessionID);
        insertContactStmt.executeUpdate();
    }

    public static boolean ifSessionExists(Connection con, String sessionID) throws SQLException {
        String checkExistSession = "SELECT EXISTS(SELECT * FROM sessions WHERE session_id=?);";
        PreparedStatement checkExistSessionStmt = con.prepareStatement(checkExistSession);
        checkExistSessionStmt.setString(1, sessionID);
        ResultSet resultSet = checkExistSessionStmt.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt(1) != 0;
        }
        return false;
    }

    public static String getFirstNameGivenSession(Connection con, String sessionID) throws SQLException {
        String selectFirstName = "SELECT first_name FROM users WHERE email=(SELECT email FROM sessions WHERE session_id=?);";
        PreparedStatement selectFirstNameStmt = con.prepareStatement(selectFirstName);
        selectFirstNameStmt.setString(1, sessionID);
        ResultSet results = selectFirstNameStmt.executeQuery();
        if (results.next()) {
            return results.getString("first_name");
        }
        return null;
    }

    public static String getEmailGivenSession(Connection con, String sessionID) throws SQLException {
        String selectEmail = "SELECT email FROM sessions WHERE session_id=?;";
        PreparedStatement selectEmailStmt = con.prepareStatement(selectEmail);
        selectEmailStmt.setString(1, sessionID);
        ResultSet result = selectEmailStmt.executeQuery();
        if (result.next()) {
            return result.getString("email");
        }
        return null;
    }

    public static void deleteSession(Connection con, String sessionID) throws SQLException {
        String deleteSession = "DELETE FROM sessions WHERE session_id=?;";
        PreparedStatement deleteSessionStmt = con.prepareStatement(deleteSession);
        deleteSessionStmt.setString(1, sessionID);
        deleteSessionStmt.executeUpdate();
    }
}
