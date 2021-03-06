package server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A class that connect to MySQL database using JDBC
 */
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

    /**
     * execute insert user to database given session id and email
     * @param con
     * @param sessionID
     * @param email
     * @throws SQLException
     */
    public static void executeInsertSession(Connection con, String sessionID, String email) throws SQLException {
        String insertContactSql = "UPDATE sessions SET email=? WHERE session_id=?;";
        PreparedStatement insertContactStmt = con.prepareStatement(insertContactSql);
        insertContactStmt.setString(1, email);
        insertContactStmt.setString(2, sessionID);
        insertContactStmt.executeUpdate();
    }

    /**
     * check if current session exists
     * @param con
     * @param sessionID
     * @return
     * @throws SQLException
     */
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

    /**
     * Retrieve first name given session id
     * @param con
     * @param sessionID
     * @return
     * @throws SQLException
     */
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

    /**
     * Retrieve last name given session id
     * @param con
     * @param sessionID
     * @return
     * @throws SQLException
     */
    public static String getLastNameGivenSession(Connection con, String sessionID) throws SQLException {
        String selectLastName = "SELECT last_name FROM users WHERE email=(SELECT email FROM sessions WHERE session_id=?);";
        PreparedStatement selectLastNameStmt = con.prepareStatement(selectLastName);
        selectLastNameStmt.setString(1, sessionID);
        ResultSet results = selectLastNameStmt.executeQuery();
        if (results.next()) {
            return results.getString("last_name");
        }
        return null;
    }

    /**
     * Retrieve username given session id
     * @param con
     * @param sessionID
     * @return
     * @throws SQLException
     */
    public static String getUsernameGivenSession(Connection con, String sessionID) throws SQLException {
        String selectUsername = "SELECT username FROM users WHERE email=(SELECT email FROM sessions WHERE session_id=?);";
        PreparedStatement selectUsernameStmt = con.prepareStatement(selectUsername);
        selectUsernameStmt.setString(1, sessionID);
        ResultSet results = selectUsernameStmt.executeQuery();
        if (results.next()) {
            return results.getString("username");
        }
        return null;
    }

    /**
     * Retrieve user id given session id
     * @param con
     * @param sessionID
     * @return
     * @throws SQLException
     */
    public static int getUserIdGivenSession(Connection con, String sessionID) throws SQLException {
        String selectUserId = "SELECT user_id FROM users WHERE email=(SELECT email FROM sessions WHERE session_id=?);";
        PreparedStatement selectUserIdStmt = con.prepareStatement(selectUserId);
        selectUserIdStmt.setString(1, sessionID);
        ResultSet results = selectUserIdStmt.executeQuery();
        if (results.next()) {
            return results.getInt("user_id");
        }
        return -1;
    }

    /**
     * Retrieve email address given session id
     * @param con
     * @param sessionID
     * @return
     * @throws SQLException
     */
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

    /**
     * Delete session id row
     * @param con
     * @param sessionID
     * @throws SQLException
     */
    public static void deleteSession(Connection con, String sessionID) throws SQLException {
        String deleteSession = "DELETE FROM sessions WHERE session_id=?;";
        PreparedStatement deleteSessionStmt = con.prepareStatement(deleteSession);
        deleteSessionStmt.setString(1, sessionID);
        deleteSessionStmt.executeUpdate();
    }
}
