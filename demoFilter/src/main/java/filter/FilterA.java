package filter;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter("*.do")
public class FilterA implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("Filter A.....");
        chain.doFilter(request,response);
        System.out.println("Filter A2.....");
    }

    @Override
    public void destroy() {
    }
}
