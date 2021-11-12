package server;

import com.google.gson.Gson;
import event.CreateEventServlet;
import event.EventsServlet;
import event.PurchaseTicketServlet;
import event.TransferTicketServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import user.ChangeNameServlet;
import user.UserServlet;
import utilities.ServerConfig;

import java.io.FileReader;

/**
 * A Ticket Purchase Web Application uses Jetty to implement multiple functionalities
 */
public class TicketServer {

    public static final int PORT = 8080;
    private static final String configFilename = "config.json";

    public static void main(String[] args) {
        try {
            startup();
        } catch(Exception e) {
            // catch generic Exception as that is what is thrown by server start method
            e.printStackTrace();
        }
    }

    public static void startup() throws Exception {

        // read the client id and secret from a serverConfig file
        Gson gson = new Gson();
        ServerConfig serverConfig = gson.fromJson(new FileReader(configFilename), ServerConfig.class);

        // create a new server
        Server server = new Server(PORT);

        // make the serverConfig information available across servlets by setting an
        // attribute in the context
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setAttribute(TicketServerConstants.CONFIG_KEY, serverConfig);

        // the default path will direct to a landing page with
        // "Login with Slack" button
        context.addServlet(LandingServlet.class, "/");

        // Once authenticated, Slack will redirect the user
        // back to /login
        // login page contains:
        // "My account" button
        // "create event" button
        // "Events" button
        // "Purchase Tickets" button
        // "TransferTickets" button
        // "Logout" button
        context.addServlet(LoginServlet.class, "/login");

        // the /user path will direct to the user's account page with
        // "My account" button on login page
        // user page contains:
        // user account information
        // transactions
        context.addServlet(UserServlet.class, "/user");
        context.addServlet(ChangeNameServlet.class, "/change-name");


        // the /create path will direct to a create event page with
        // "Create an Event" button on login page
        context.addServlet(CreateEventServlet.class, "/create"); //create-event

        // the /events path will direct to a page that lists all the events with
        // "Events" button on login page
        // ??? view each event
        context.addServlet(EventsServlet.class, "/events");

        // the /events/
        // context.addServlet(EventServlet.class, "/events/specific-event's-name");


        // the /purchase path will direct to a page that user can purchase tickets with
        // "Purchase Tickets" button on login page
        context.addServlet(PurchaseTicketServlet.class, "/purchase");

        // the /transfer path will direct to a page that user can transfer tickets with
        // "Transfer Tickets" button on login page
        context.addServlet(TransferTicketServlet.class, "/transfer");

        // handle logout
        context.addServlet(LogoutServlet.class, "/logout");


        // start it up
        server.setHandler(context);
        server.start();
    }
}
