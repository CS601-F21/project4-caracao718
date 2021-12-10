package event;

import event.DisplaySearchServlet;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import utilities.DBCPDataSource;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DisplaySearchServletTest {
    @Test
    public void testInputCorrectly200() {
        try {
            String URI = "page=3&title=event&description=this&date=2021-10-2&location=NewYork";
            String[] expectedRequestURI = {"page", "3", "title", "event", "description", "this", "date", "2021-10-2", "location", "NewYork"};
            HttpServletRequest mockedRequest = mock(HttpServletRequest.class);
            HttpServletResponse mockedResponse = mock(HttpServletResponse.class);
            PrintWriter writer = mock(PrintWriter.class);

            when(mockedRequest.getQueryString()).thenReturn(URI);
            when(mockedResponse.getWriter()).thenReturn(writer);

            DisplaySearchServlet test = new DisplaySearchServlet();
            test.doGet(mockedRequest, mockedResponse);

            verify(mockedResponse).setStatus(HttpStatus.OK_200);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void testInputIncorrectly400() {
        try {
            String URI = "=3&title=event&description=this&date=2021-10-2&location=";
            HttpServletRequest mockedRequest = mock(HttpServletRequest.class);
            HttpServletResponse mockedResponse = mock(HttpServletResponse.class);

            PrintWriter writer = mock(PrintWriter.class);

            when(mockedRequest.getQueryString()).thenReturn(URI);
            when(mockedResponse.getWriter()).thenReturn(writer);

            DisplaySearchServlet test = new DisplaySearchServlet();
            test.doGet(mockedRequest, mockedResponse);
            verify(mockedResponse).setStatus(HttpStatus.BAD_REQUEST_400);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSplitQuery() {
        try {
            String URI = "page=3&title=event&description=this&date=2021-10-2&location=NewYork";
            String[] expectedRequestURI = {"page", "3", "title", "event", "description", "this", "date", "2021-10-2", "location", "NewYork"};
            HttpServletRequest mockedRequest = mock(HttpServletRequest.class);
            HttpServletResponse mockedResponse = mock(HttpServletResponse.class);
            PrintWriter writer = mock(PrintWriter.class);

            when(mockedRequest.getQueryString()).thenReturn(URI);
            when(mockedResponse.getWriter()).thenReturn(writer);

            DisplaySearchServlet test = new DisplaySearchServlet();
            test.doGet(mockedRequest, mockedResponse);

            assertEquals(Arrays.toString(mockedRequest.getQueryString().split("=|&", 10)), Arrays.toString(expectedRequestURI));
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }
}
