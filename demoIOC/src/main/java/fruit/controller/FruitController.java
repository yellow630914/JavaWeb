package fruit.controller;

import fruit.Fruit;
import fruit.service.FruitService;
import fruit.service.impl.FruitServiceImpl;
import sql.JDBCUtils;
import util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public class FruitController{

    private FruitService fruitService = new FruitServiceImpl();

    protected String update(Integer fid,String fname,Integer price,Integer fcount,String remark) throws SQLException {

        Connection conn = JDBCUtils.getDruidConnection();
        fruitService.updateFruit(conn,new Fruit(fid,fname,price,fcount,remark));
        return "redirect:fruit.do";

    }

    protected String edit(HttpServletRequest req,Integer fid) throws SQLException {
        String fidStr = req.getParameter("fid");

        Connection conn = JDBCUtils.getDruidConnection();
        if (fid != null) {
            Fruit fruit = fruitService.getFruitByFid(conn,fid);
            req.setAttribute("fruit",fruit);
            return "edit";
        }
        return "error";



    }

    protected String del(Integer fid) throws SQLException {
        if(fid != null){
            Connection conn = JDBCUtils.getDruidConnection();
            fruitService.delFruit(conn,fid);

            return "redirect:fruit.do";
        }
        return "error";
    }

    protected String add(String fname,Integer price,Integer fcount,String remark) throws SQLException,ArithmeticException {

        Fruit fruit = new Fruit();
        fruit.setFname(fname);
        fruit.setPrice(price);
        fruit.setFcount(fcount);
        fruit.setRemark(remark);

        Connection conn = JDBCUtils.getDruidConnection();
        fruitService.addFruit(conn,fruit);

        return "redirect:fruit.do";

    }

    private String index(String oper,String keyword,Integer pageNo,HttpServletRequest req) throws SQLException {
        if(pageNo == null){
            pageNo = 1;
        }

        Connection conn = JDBCUtils.getDruidConnection();

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

        Long pageCount = fruitService.getPageCount(conn,keyword);
        session.setAttribute("pageCount",pageCount);

        return "index";
    }

}







