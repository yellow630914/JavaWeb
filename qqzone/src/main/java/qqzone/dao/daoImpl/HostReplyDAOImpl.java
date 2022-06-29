package qqzone.dao.daoImpl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import qqzone.dao.HostReplyDAO;
import qqzone.pojo.HostReply;
import ssm.sql.JDBCUtils;

import java.sql.Connection;
import java.sql.SQLException;

public class HostReplyDAOImpl implements HostReplyDAO {
    private Connection conn = JDBCUtils.getDruidConnection();

    public HostReplyDAOImpl() throws SQLException {
    }

    @Override
    public HostReply getHostReplyByReplyId(Integer replyId) throws SQLException {
        QueryRunner queryRunner = new QueryRunner();
        String sql = "select * from t_host_reply where reply = ?";
        BeanHandler<HostReply> handler = new BeanHandler<>(HostReply.class);
        return queryRunner.query(conn,sql,handler,replyId);
    }

    @Override
    public void delHostReply(Integer id) throws SQLException {
        QueryRunner queryRunner = new QueryRunner();
        String sql = "delete from t_host_reply where id = ?";
        queryRunner.update(conn,sql,id);
    }
}
