package servlet;

import fruit.Fruit;
import servlet.thymeleaf.ViewBaseServlet;
import sql.JDBCUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/fruit.do")
public class FruitServlet extends ViewBaseServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    }

    private void index(HttpServletRequest req, HttpServletResponse resp) throws IOException{
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

    protected void add(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String fname = req.getParameter("fname");
        String priceStr = req.getParameter("price");
        int price = Integer.parseInt(priceStr);
        String fcountStr = req.getParameter("fcount");
        int fcount = Integer.parseInt(fcountStr);
        String remark = req.getParameter("remark");

        Fruit fruit = new Fruit();
        fruit.setFname(fname);
        fruit.setPrice(price);
        fruit.setFcount(fcount);
        fruit.setRemark(remark);
        Connection conn = null;
        try {
            conn = JDBCUtils.getDruidConnection();
            JDBCUtils.insertFruit(conn,fruit);
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
            resp.sendRedirect("fruit.do");
        }


    }

    protected void del(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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

            resp.sendRedirect("fruit.do");
        }
    }

    protected void edit(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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

    protected void update(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String fidStr = req.getParameter("fid");
        int fid = Integer.parseInt(fidStr);
        String fname = req.getParameter("fname");
        String priceStr = req.getParameter("price");
        int price = Integer.parseInt(priceStr);
        String fcountStr = req.getParameter("fcount");
        int fcount = Integer.parseInt(fcountStr);
        String remark = req.getParameter("remark");

        Connection conn = null;
        try {
            Fruit fruit = new Fruit(fid,fname,price,fcount,remark);
            conn = JDBCUtils.getDruidConnection();
            JDBCUtils.setFruit(conn,fruit);
            resp.sendRedirect("fruit.do");
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
        }


    }
}
