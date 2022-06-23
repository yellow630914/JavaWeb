package servlet;

import fruit.Fruit;
import servlet.thymeleaf.ViewBaseServlet;
import sql.JDBCUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/index")
public class indexServlet extends ViewBaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection conn = null;
        try {
            Integer pageNo = 1;
            String pageNoStr = req.getParameter("pageNo");
            if(StringUtils.isNotEmpty(pageNoStr)){
                pageNo = Integer.parseInt(pageNoStr);
            }

            HttpSession session = req.getSession();
            session.setAttribute("pageNo",pageNo);

            conn = JDBCUtils.getDruidConnection();
            List<Fruit> fruitList = JDBCUtils.getFruitListByPageNo(conn,pageNo);

            session.setAttribute("fruitList",fruitList);

            Long fruitCount = JDBCUtils.getFruitCount(conn);
            System.out.println(fruitCount);
            Long pageCount = (fruitCount+5-1)/5;

            session.setAttribute("pageCount",pageCount);

            super.processTemplate("index",req,resp);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}

