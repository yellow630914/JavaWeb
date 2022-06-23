import sql.JDBCUtils;

import java.sql.Connection;
import java.sql.SQLException;

public class Test {
    @org.testng.annotations.Test
    public void test(){
        Connection connection = null;
        try {
            connection = JDBCUtils.getDruidConnection();
            System.out.println(JDBCUtils.getFruitCount(connection));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }
}
