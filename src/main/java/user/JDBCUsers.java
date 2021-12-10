package user;

import java.sql.*;

/**
 * A class that connect to MySQL User database using JDBC
 */
public class JDBCUsers {
    /**
     * A method using a PreparedStatement to execute a database insert.
     * @param con
     * @param email
     * @throws SQLException
     */
    public static void executeInsertFirstnameEmail(Connection con, String firstName, String email) throws SQLException {
        String insertContactSql = "INSERT INTO users (first_name, email) VALUES (?, ?);";
        PreparedStatement insertContactStmt = con.prepareStatement(insertContactSql);
        insertContactStmt.setString(1, firstName);
        insertContactStmt.setString(2, email);
        insertContactStmt.executeUpdate();
    }

    /**
     * Insert a new user given parameters into the database
     * @param con
     * @param username
     * @param lastName
     * @param location
     * @param eventType
     * @param email
     * @throws SQLException
     */
    public static void executeSignupInsert(Connection con, String username, String lastName, String location, String eventType, String email) throws SQLException {
        String insertContactSql = "UPDATE users SET username=?, last_name=?, location=?, event_type=? WHERE email=?;";
        PreparedStatement insertContactStmt = con.prepareStatement(insertContactSql);
        insertContactStmt.setString(1, username);
        insertContactStmt.setString(2, lastName);
        insertContactStmt.setString(3, location);
        insertContactStmt.setString(4, eventType);
        insertContactStmt.setString(5, email);
        insertContactStmt.executeUpdate();
    }


    /**
     * A method to check if a user is already in the database or not
     */
    public static boolean checkUserExistence(Connection con, String userEmail) throws SQLException {
        String user = "SELECT EXISTS(SELECT * FROM users WHERE email=?);";
        PreparedStatement checkUser = con.prepareStatement(user);
        checkUser.setString(1, userEmail);
        ResultSet results = checkUser.executeQuery();
        if (results.next()) {
            return results.getInt(1) != 0;
        }
        return false;
    }

    /**
     * A method to retrieve user_id from users table given email
     */
    public static int getUserIdGivenEmail(Connection con, String email) throws SQLException {
        String getEmail = "SELECT user_id FROM users WHERE email=?;";
        PreparedStatement getEmailStmt = con.prepareStatement(getEmail);
        getEmailStmt.setString(1, email);
        ResultSet result = getEmailStmt.executeQuery();
        if (result.next()) {
            return result.getInt("user_id");
        }
        return -1;
    }


    /**
     * A method that changes first name for user
     * @param con
     * @param name
     */
    public static void changeFirstName(Connection con, String name, String email) throws SQLException{
        String changeName = "UPDATE users SET first_name=? WHERE email=?;";
        PreparedStatement changeNameStmt = con.prepareStatement(changeName);
        changeNameStmt.setString(1, name);
        changeNameStmt.setString(2, email);
        changeNameStmt.executeUpdate();
    }

    /**
     * A method that changes last name for user
     * @param con
     * @param name
     * @param email
     * @throws SQLException
     */
    public static void changeLastName(Connection con, String name, String email) throws SQLException{
        String changeName = "UPDATE users SET last_name=? WHERE email=?;";
        PreparedStatement changeNameStmt = con.prepareStatement(changeName);
        changeNameStmt.setString(1, name);
        changeNameStmt.setString(2, email);
        changeNameStmt.executeUpdate();
    }

    /**
     * A method to demonstrate using a PrepareStatement to execute a database select
     * @param con
     * @throws SQLException
     */
    public static void executeSelectAndPrint(Connection con, String table) throws SQLException {
        String selectAllContactsSql = "SELECT * FROM users;";
        PreparedStatement selectAllContactsStmt = con.prepareStatement(selectAllContactsSql);
        ResultSet results = selectAllContactsStmt.executeQuery();
        while(results.next()) {
            System.out.printf("Name: %s\n", results.getString("name"));
            System.out.printf("Email: %s\n", results.getInt("email"));
            System.out.printf("Event ID: %s\n", results.getString("event_id"));
        }
    }

}
