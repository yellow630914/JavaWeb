import fruit.Fruit;
import org.apache.commons.dbutils.QueryRunner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class AddServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String fname = req.getParameter("fname");
        String priceStr = req.getParameter("price");
        Integer price = Integer.parseInt(priceStr);
        String fcountStr = req.getParameter("fcount");
        Integer fcount = Integer.parseInt(fcountStr);
        String remark = req.getParameter("remark");

        System.out.println(fname);
        System.out.println(price);
        System.out.println(fcount);
        System.out.println(remark);

        Fruit fruit = new Fruit(99,fname,price,fcount,remark);
        insertPostToMysql(fruit);
    }

    static public void insertPostToMysql(Fruit f){
        Connection conn = null;
        try {
            QueryRunner qr = new QueryRunner();
            conn = JDBCUtils.getDruidConnection();
            String sql = "insert into t_fruit(fname,price,fcount,remark)values(?,?,?,?)";
            int insertCount = qr.update(conn,sql,f.getFname(),f.getPrice(),f.getFcount(),f.getRemark());
            System.out.println("添加了" + insertCount + "條紀錄");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(conn != null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
