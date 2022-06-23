package servlet;

import servlet.StringUtils;
import servlet.thymeleaf.ViewBaseServlet;
import sql.JDBCUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/del.do")
public class DeleteServlet extends ViewBaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fidStr = req.getParameter("fid");
        if(StringUtils.isNotEmpty(fidStr)){
            int fid = Integer.parseInt(fidStr);
            Connection conn = null;
            try {
                conn = JDBCUtils.getDruidConnection();
                JDBCUtils.delFruitById(conn,fid);
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (conn != null){
                        conn.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            resp.sendRedirect("index");
        }
    }
}
