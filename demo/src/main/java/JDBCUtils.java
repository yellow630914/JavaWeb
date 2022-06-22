
import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
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
}
