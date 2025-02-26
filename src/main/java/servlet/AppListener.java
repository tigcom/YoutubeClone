package servlet;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
@WebListener
public class AppListener implements ServletContextListener {
	@Override
    public void contextInitialized(ServletContextEvent sce) {
//        sce.getServletContext().setSessionTimeout(1);  
    }
}
