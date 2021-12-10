package event;

import java.sql.Date;

/**
 * A helper class to maintain constants used for the Event Servlets
 */
public class EventConstants {
    private static long millis = System.currentTimeMillis();
    private static Date date = new Date(millis);
    private static String currDate = date.toString();

    public static final String PAGE_HEADER = "<!DOCTYPE html>\n" +
            "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
            "<head>\n" +
            "  <title>Create an Event</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "\n";

    public static final String PAGE_FOOTER = "\n" +
            "</body>\n" +
            "</html>";

    public static final String ERROR_PAGE = PAGE_HEADER +
            "<h2> Bad request, please try again.</h2>\n" +
            "<p><a href=\"/login\">My Home Page</a></p>" +
            PAGE_FOOTER;

    public static final String GET_CREATE_EVENT_PAGE = PAGE_HEADER +
            "<h2> Please fill out all the information in the form below to create an event.</h2>\n" +

            "<form action=\"/create\" method=\"post\">\n" +

            "  <label for=\"title\">Event Title:</label><br/>\n" +
            "  <input type=\"text\" id=\"title\" name=\"title\" required/><br/>\n" +

            "  <p> Please input description of event: </p>\n" +
            "  <textarea name=\"description\" rows=\"10\" cols=\"30\"></textarea><br/>\n" +

            "  <label for=\"date\">Event Date:</label><br/>\n" +
            "  <input type=\"date\" id=\"date\" name=\"date\" min=" + currDate + " required/><br/>\n" +

            "  <label for=\"numticket\">Total Number of Tickets:</label><br/>\n" +
            "  <input type=\"number\" id=\"numticket\" name=\"numticket\" min=\"1\" required/><br/>\n" +

            "  <label for=\"price\">Price:</label><br/>\n" +
            "  <input type=\"number\" id=\"price\" name=\"price\"/><br/>\n" +

            "  <label for=\"location\">Location:</label><br/>\n" +
            "  <select id=\"location\" name=\"location\"/><br/>\n" +
                "  <option value=\"New York City\"> New York City</option>\n" +
                "  <option value=\"San Francisco\"> San Francisco</option>\n" +
                "  <option value=\"Los Angeles\"> Los Angeles</option>\n" +
                "  <option value=\"Boston\"> Boston</option>\n" +
                "  <option value=\"Seattle\"> Seattle</option>\n" +
                "  <option value=\"San Diego\"> San Diego</option>\n" +
            "</select>" +


            "   \n  " +
            "  <input type=\"submit\" value=\"Submit\"/>\n" +
            "</form>\n" +
            PAGE_FOOTER;


    public static final String SEARCH_EVENT_FORM = PAGE_HEADER +
            "<h2> Please fill out all the information in the form below to search an event.</h2>\n" +

            "<form action=\"/search?page=0\" method=\"post\">\n" +

            "  <label for=\"title\">Event Title:</label><br/>\n" +
            "  <input type=\"text\" id=\"title\" name=\"title\"/><br/>\n" +

            "  <p> Please input keywords of the description: </p>\n" +
            "  <textarea name=\"description\" rows=\"10\" cols=\"30\"></textarea><br/>\n" +

            "  <label for=\"date\">Must select Earliest Date:</label><br/>\n" +
            "  <input type=\"date\" id=\"date\" name=\"date\" min=" + currDate + " required/><br/>\n" +


            "  <label for=\"location\">Location:</label><br/>\n" +
            "  <select id=\"location\" name=\"location\"/><br/>\n" +
            "  <option value=\"New York City\"> New York City</option>\n" +
            "  <option value=\"San Francisco\"> San Francisco</option>\n" +
            "  <option value=\"Los Angeles\"> Los Angeles</option>\n" +
            "  <option value=\"Boston\"> Boston</option>\n" +
            "  <option value=\"Seattle\"> Seattle</option>\n" +
            "  <option value=\"San Diego\"> San Diego</option>\n" +
            "  <option value=\"empty\" selected> no location</option>\n" +
            "</select>" +


            "   \n  " +
            "  <input type=\"submit\" value=\"Submit\"/>\n" +
            "</form>\n";


}
