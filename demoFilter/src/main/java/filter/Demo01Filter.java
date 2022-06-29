package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter("*.do")
public class Demo01Filter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("Hello,Im Demo01Filter");
        //放行
        chain.doFilter(request,response);
        //在執行被攔截的Servlet後的再經過
        System.out.println("comeback");
    }

    @Override
    public void destroy() {
    }
}
