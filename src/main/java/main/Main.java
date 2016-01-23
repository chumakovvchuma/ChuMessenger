package main;

import frontend.Frontend;
import frontend.SignInServlet;
import frontend.SignUpServlet;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;

import javax.servlet.Servlet;


public class Main {
    public static void main(String[] args) throws Exception {

        Frontend frontend = new Frontend();
        int port = 8080;
        if (args.length == 1) {
            String portString = args[0];
            port = Integer.valueOf(portString);
        }

        System.out.append("Starting at porttttt: ").append(String.valueOf(port)).append('\n');
        System.out.append("Starting at portt: ");

        AccountService accountService = new AccountService();

        Servlet signIn = new SignInServlet(accountService);
        Servlet signUp = new SignUpServlet(accountService);



        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(signIn), "/api/v1/auth/signin");
        context.addServlet(new ServletHolder(signUp), "/api/v1/auth/signup");
        context.addServlet(new ServletHolder(frontend), "/authform");

        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(true);
        resource_handler.setResourceBase("public_html");


        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_handler, context});

        Server server = new Server(port);
        server.setHandler(handlers);

        server.start();
        server.join();
    }
}