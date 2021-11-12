package user;

import java.sql.*;

/**
 * A class that connect to MySQL User database using JDBC
 */
public class JDBCUsers {
    /**
     * A method using a PreparedStatement to execute a database insert.
     * @param con
     * @param name
     * @param email
     * @throws SQLException
     */
    public static void executeInsert(Connection con, String name, String email, int eventID) throws SQLException {
        String insertContactSql = "INSERT INTO users (name, email, event_id) VALUES (?, ?, ?);";
        PreparedStatement insertContactStmt = con.prepareStatement(insertContactSql);
        insertContactStmt.setString(1, name);
        insertContactStmt.setString(2, email);
        insertContactStmt.setInt(3, eventID);
        insertContactStmt.executeUpdate();
    }

    public static void executeInsert(Connection con, String name, String email) throws SQLException {
        String insertContactSql = "INSERT INTO users (name, email) VALUES (?, ?);";
        PreparedStatement insertContactStmt = con.prepareStatement(insertContactSql);
        insertContactStmt.setString(1, name);
        insertContactStmt.setString(2, email);
        insertContactStmt.executeUpdate();
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

    public static ResultSet executeSelectUsers(Connection con) throws SQLException {
        String selectAllContactsSql = "SELECT * FROM users;";
        PreparedStatement selectAllContactsStmt = con.prepareStatement(selectAllContactsSql);
        return selectAllContactsStmt.executeQuery();
    }

//    public static void main(String[] args){
//
//        DatabaseConfig databaseConfig = DatabaseUtilities.readConfig();
//        if(databaseConfig == null) {
//            System.exit(1);
//        }
//
//        // Make sure that mysql-connector-java is added as a dependency.
//        // Force Maven to Download Sources and Documentation
//        try (Connection con = DriverManager
//                .getConnection("jdbc:mysql://localhost:3306/" + databaseConfig.getDatabase(), databaseConfig.getUsername(), databaseConfig.getPassword())) {
//
////            executeSelectAndPrint(con);
////            System.out.println("*****************");
////
////            executeInsert(con,"Sami", 2024, "srollins", "2006-09-01");
////
////            executeSelectAndPrint(con);
////            System.out.println("*****************");
////
////            executeInsert(con,"Bertha", 9876, "bzuniga", "2009-09-01");
////
////            executeSelectAndPrint(con);
////            System.out.println("*****************");
//
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//    }
}
