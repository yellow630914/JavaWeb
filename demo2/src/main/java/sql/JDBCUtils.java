package sql;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import fruit.Fruit;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import javax.management.Query;
import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class JDBCUtils {
    private  static DataSource ds;

    static{
        try {
            Properties pros = new Properties();

            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("druid.properties");
            pros.load(is);

            ds = DruidDataSourceFactory.createDataSource(pros);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Connection getDruidConnection() throws SQLException {return ds.getConnection();}

    /**
     * 取得t_fruit的所有資料
     * @param conn 資料庫連結
     * @return fruit類型的list
     * @throws SQLException
     */
    public static List<Fruit> getFruitList(Connection conn) throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sql = "select fname,price,fcount,remark from t_fruit";
        BeanListHandler<Fruit> handler = new BeanListHandler<>(Fruit.class);
        List<Fruit> fruitList = runner.query(conn,sql,handler);
        return fruitList;
    }
}
