package listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class MyServletListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Servlet上下文初始化...");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Servlet上下文銷毀...");
    }
}
