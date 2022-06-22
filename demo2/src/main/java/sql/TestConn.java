package sql;

import org.apache.commons.dbutils.QueryRunner;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class TestConn {
    @Test
    public void test1() {

        Connection conn = null;
        try {
            QueryRunner qr = new QueryRunner();
            conn = JDBCUtils.getDruidConnection();


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
