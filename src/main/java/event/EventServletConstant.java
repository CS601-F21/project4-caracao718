package event;

public class EventServletConstant {
    public static final String PAGE_HEADER = "<!DOCTYPE html>\n" +
            "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
            "<head>\n" +
            "  <title>Events</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "\n";

    public static final String PAGE_FOOTER = "\n" +
            "</body>\n" +
            "</html>";

    public static final String ERROR_PAGE = PAGE_HEADER +
            "<h3>Something went wrong, please try again</h3>\n" +
            "<p><a href=\"/login\">My Home Page</a>" +
            PAGE_FOOTER;
}
