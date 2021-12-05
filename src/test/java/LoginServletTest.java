import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.RepeatedTest;

import static org.mockito.Mockito.mock;

public class LoginServletTest {
    @RepeatedTest(10)
    public void testGetHTMLResponse() {
        HttpServletRequest mockedRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockedResponse = mock(HttpServletResponse.class);
        ServletContext context = mock(ServletContext.class);


    }
}
