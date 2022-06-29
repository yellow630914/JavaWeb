package ssm.trans;

import ssm.sql.JDBCUtils;

import java.sql.SQLException;

public class TransactionManager {

    //開啟事務
    public static void beginTrans() throws SQLException {
        JDBCUtils.getDruidConnection().setAutoCommit(false);
    }

    public static void commit() throws SQLException {
        JDBCUtils.getDruidConnection().commit();
    }

    public static void rollback() throws SQLException {
        JDBCUtils.getDruidConnection().rollback();
    }
}
