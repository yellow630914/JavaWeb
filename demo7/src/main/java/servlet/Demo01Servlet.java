package servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;


public class Demo01Servlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        ServletConfig config = getServletConfig();
        String initValue = config.getInitParameter("hello");
        System.out.println("initValue = " + initValue);

        ServletContext servletContext =getServletContext();
        String servletContextInitParameter = servletContext.getInitParameter("contextConfigLocation");
        System.out.println(servletContextInitParameter);
    }
}
