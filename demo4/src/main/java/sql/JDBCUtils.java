package sql;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import fruit.Fruit;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

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
        String sql = "select fid,fname,price,fcount,remark from t_fruit";
        BeanListHandler<Fruit> handler = new BeanListHandler<>(Fruit.class);
        List<Fruit> fruitList = runner.query(conn,sql,handler);
        return fruitList;
    }

    public static List<Fruit> getFruitListByPageNo(Connection conn,String keyword,Integer pageNo) throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sql = "select * from t_fruit where fname like ? or remark like ? limit ?,5";
        BeanListHandler<Fruit> handler = new BeanListHandler<>(Fruit.class);
        List<Fruit> fruitList = runner.query(conn,sql,handler,"%"+keyword+"%","%"+keyword+"%",(pageNo-1)*5);

        return fruitList;
    }

    public static Fruit getFruitByFid(Connection conn,int fid) throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sql = "select fid,fname,price,fcount,remark from t_fruit where fid=?";
        BeanHandler<Fruit> handler = new BeanHandler<>(Fruit.class);
        return runner.query(conn,sql,handler,fid);
    }

    public static void setFruit(Connection conn,Fruit fruit) throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sql = "update t_fruit set fname=?,price=?,fcount=?,remark=? where fid=?";
        runner.update(conn,sql,fruit.getFname(),fruit.getPrice(),fruit.getFcount(),fruit.getRemark(),fruit.getFid());
    }

    public static void delFruitById(Connection conn,int fid) throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sql = "delete from t_fruit where fid = ?";
        runner.update(conn,sql,fid);
    }

    public static void insertFruit(Connection conn,Fruit fruit) throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sql = "insert into t_fruit(fname,price,fcount,remark)values(?,?,?,?)";
        runner.update(conn,sql,fruit.getFname(),fruit.getPrice(),fruit.getFcount(),fruit.getRemark());
    }

    public static Long getFruitCount(Connection conn,String keyword) throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sql = "select count(*) from t_fruit where fname like ? or remark like ?";
        ScalarHandler<Long> handler = new ScalarHandler<>();
        return runner.query(conn,sql,handler,"%"+keyword+"%","%"+keyword+"%");
    }
}
