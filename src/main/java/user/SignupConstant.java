package user;

import javax.swing.text.html.CSS;

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

            "  <label for=\"location\">Your location (City):</label><br/>\n" +
            "  <input type=\"text\" id=\"location\" name=\"location\" required/><br/>\n" +

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
