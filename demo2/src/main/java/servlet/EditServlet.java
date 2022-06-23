package servlet;

import fruit.Fruit;
import servlet.thymeleaf.ViewBaseServlet;
import sql.JDBCUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/edit.do")
public class EditServlet extends ViewBaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fidStr = req.getParameter("fid");
        if (StringUtils.isNotEmpty(fidStr)){
            Connection conn = null;
            try {
                conn = JDBCUtils.getDruidConnection();
                int fid = Integer.parseInt(fidStr);
                Fruit fruit = JDBCUtils.getFruitByFid(conn,fid);
                req.setAttribute("fruit",fruit);
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if(conn != null){
                        conn.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                super.processTemplate("edit",req,resp);
            }


        }
    }
}
