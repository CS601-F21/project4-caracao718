package event;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class SearchEventServletTest {
    @Test
    public void testInputCorrectly200() {
        try {
            String URI = "page=3&total=0";
            HttpServletRequest mockedRequest = mock(HttpServletRequest.class);
            HttpServletResponse mockedResponse = mock(HttpServletResponse.class);
            PrintWriter writer = mock(PrintWriter.class);

            when(mockedRequest.getQueryString()).thenReturn(URI);
            when(mockedResponse.getWriter()).thenReturn(writer);

            SearchEventServlet test = new SearchEventServlet();
            test.doPost(mockedRequest, mockedResponse);

            verify(mockedResponse).setStatus(HttpStatus.OK_200);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testInputIncorrectly400() {
        try {
            String URI = "title=event&description=this&date=2021-10-2&location=";
            HttpServletRequest mockedRequest = mock(HttpServletRequest.class);
            HttpServletResponse mockedResponse = mock(HttpServletResponse.class);

            PrintWriter writer = mock(PrintWriter.class);

            when(mockedRequest.getQueryString()).thenReturn(URI);
            when(mockedResponse.getWriter()).thenReturn(writer);

            SearchEventServlet test = new SearchEventServlet();
            test.doPost(mockedRequest, mockedResponse);
            verify(mockedResponse).setStatus(HttpStatus.BAD_REQUEST_400);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSplitQuery() {
        try {
            String URI = "page=3&total=1";
            String[] expectedRequestURI = {"page", "3", "total", "1"};
            HttpServletRequest mockedRequest = mock(HttpServletRequest.class);
            HttpServletResponse mockedResponse = mock(HttpServletResponse.class);
            PrintWriter writer = mock(PrintWriter.class);

            when(mockedRequest.getQueryString()).thenReturn(URI);
            when(mockedResponse.getWriter()).thenReturn(writer);

            SearchEventServlet test = new SearchEventServlet();
            test.doPost(mockedRequest, mockedResponse);

            assertEquals(Arrays.toString(mockedRequest.getQueryString().split("=|&", 10)), Arrays.toString(expectedRequestURI));
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }
}
