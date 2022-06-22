
import org.apache.commons.dbutils.QueryRunner;
import org.junit.Test;

import java.sql.*;

public class TestConn {
    @Test
    public void test1() throws Exception {

        Connection conn = null;
        try {
            QueryRunner qr = new QueryRunner();
            conn = JDBCUtils.getDruidConnection();
            String sql = "insert into t_fruit(fname,price,fcount,remark)values(?,?,?,?)";

            int insertCount = qr.update(conn,sql,"西瓜",5,50,"OK");
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
