package fruit.controller;

import fruit.Fruit;
import util.StringUtils;
import sql.JDBCUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public class FruitController{

    protected String update(Integer fid,String fname,Integer price,Integer fcount,String remark) {

        Connection conn = null;
        try {
            Fruit fruit = new Fruit(fid,fname,price,fcount,remark);
            conn = JDBCUtils.getDruidConnection();
            JDBCUtils.setFruit(conn,fruit);
            return "redirect:fruit.do";
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
        return "error";

    }

    protected String edit(HttpServletRequest req,Integer fid) {
        String fidStr = req.getParameter("fid");
        Connection conn = null;

        try {
            conn = JDBCUtils.getDruidConnection();
            if (fid != null) {
                Fruit fruit = JDBCUtils.getFruitByFid(conn,fid);
                req.setAttribute("fruit",fruit);
                return "edit";
            }
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
        return "error";



    }

    protected String del(Integer fid) {
        if(fid != null){
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
                return "redirect:fruit.do";
            }
        }
        return "error";
    }

    protected String add(String fname,Integer price,Integer fcount,String remark) {

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
            return "redirect:fruit.do";
        }
    }

    private String index(String oper,String keyword,Integer pageNo,HttpServletRequest req) {
        Connection conn = null;

        if(pageNo == null){
            pageNo = 1;
        }

        try {
            conn = JDBCUtils.getDruidConnection();

            HttpSession session = req.getSession();

            if(StringUtils.isNotEmpty(oper) && "search".equals(oper)){
                pageNo = 1;
                if(StringUtils.isEmpty(keyword)){
                    keyword = "";
                }
                session.setAttribute("keyword",keyword);
            }else{
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
            Long pageCount = (fruitCount+5-1)/5;
            session.setAttribute("pageCount",pageCount);
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
            return "index";
        }

    }






}
