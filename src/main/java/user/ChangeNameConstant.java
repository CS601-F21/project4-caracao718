package user;

public class ChangeNameConstant {

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


    public static final String GET_CHANGE_NAME_PAGE = PAGE_HEADER +
            "<form action=\"/change-name\" method=\"post\">\n" +
            "  <label for=\"msg\">New Name:</label><br/>\n" +
            "  <input type=\"text\" id=\"msg\" name=\"msg\"/><br/>\n" +
            "  <input type=\"submit\" value=\"Submit\"/>\n" +
            "</form>\n" +
            PAGE_FOOTER;
}
