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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Connection conn = null;
        try {
            Integer pageNo = 1;
            String oper = req.getParameter("oper");
            String keyword = null;
            HttpSession session = req.getSession();
            conn = JDBCUtils.getDruidConnection();

            if(StringUtils.isNotEmpty(oper) && "search".equals(oper)){
                pageNo = 1;
                keyword = req.getParameter("keyword");
                if(StringUtils.isEmpty(keyword)){
                    keyword = "";
                }
                session.setAttribute("keyword",keyword);
            }else{
                String pageNoStr = req.getParameter("pageNo");
                if(StringUtils.isNotEmpty(pageNoStr)){
                    pageNo = Integer.parseInt(pageNoStr);
                }
                Object keywordObj = session.getAttribute("keyword");
                if(keywordObj!=null){
                    keyword = (String)keywordObj;
                }else {
                    keyword = "";

                }
            }

            session.setAttribute("pageNo",pageNo);

            List<Fruit> fruitList = JDBCUtils.getFruitListByPageNo(conn,keyword,pageNo);
            session.setAttribute("fruitList",fruitList);

            Long fruitCount = JDBCUtils.getFruitCount(conn,keyword);
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

