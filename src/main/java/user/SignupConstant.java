package user;

/**
 * A helper class to maintain constants used for the Signup Servlet
 */
public class SignupConstant {

    public static final String PAGE_HEADER = "<!DOCTYPE html>\n" +
            "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
            "<head>\n" +
            "  <title>Change My Name</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "\n";

    public static final String PAGE_FOOTER = "\n" +
            "</body>\n" +
            "</html>";


    public static final String SIGN_UP_PAGE = PAGE_HEADER +
            "<form action=\"/signup\" method=\"post\">\n" +

            "  <label for=\"username\">UserName:</label><br/>\n" +
            "  <input type=\"text\" id=\"username\" name=\"username\" required/><br/>\n" +

            "  <label for=\"lname\">LastName:</label><br/>\n" +
            "  <input type=\"text\" id=\"lname\" name=\"lname\" required/><br/>\n" +

            "  <label for=\"location\"> Your Location:</label><br/>\n" +
            "  <select id=\"location\" name=\"location\"/><br/>\n" +
                "  <option value=\"New York City\"> New York City</option>\n" +
                "  <option value=\"San Francisco\"> San Francisco</option>\n" +
                "  <option value=\"Los Angeles\"> Los Angeles</option>\n" +
                "  <option value=\"Boston\"> Boston</option>\n" +
                "  <option value=\"Seattle\"> Seattle</option>\n" +
                "  <option value=\"San Diego\"> San Diego</option>\n" +
            "</select>\n" +

            "   <input type=\"radio\" id=\"concert\" name=\"event_type\" value=\"concert\">\n" +
            "   <label for=\"concert\">Concert</label><br/>\n" +

            "   <input type=\"radio\" id=\"movie\" name=\"event_type\" value=\"movie\">\n" +
            "   <label for=\"movie\">Movie</label><br/>\n" +

            "   <input type=\"radio\" id=\"dance\" name=\"event_type\" value=\"dance\">\n" +
            "   <label for=\"dance\">Dance</label>\n" +

            "  <input type=\"submit\" value=\"Submit\"/>\n" +
            "</form>\n" +
            PAGE_FOOTER;
}
