package filter;


import sql.JDBCUtils;
import trans.TransactionManager;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.sql.SQLException;

@WebFilter("*.do")
public class OpenSessionViewFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            TransactionManager.beginTrans();
            chain.doFilter(request,response);
            TransactionManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                TransactionManager.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                JDBCUtils.closeConn();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void destroy() {
    }
}
