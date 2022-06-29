package qqzone.dao.daoImpl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import qqzone.dao.ReplyDAO;
import qqzone.pojo.Reply;
import qqzone.pojo.Topic;
import ssm.sql.JDBCUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ReplyDAOImpl implements ReplyDAO {
    private Connection conn = JDBCUtils.getDruidConnection();

    public ReplyDAOImpl() throws SQLException {
    }

    @Override
    public List<Reply> getReplyList(Integer topicId) throws SQLException {
        QueryRunner queryRunner = new QueryRunner();
        String sql = "select * from t_reply where topic = ?";
        BeanListHandler<Reply> handler = new BeanListHandler<>(Reply.class);
        List<Reply> list = queryRunner.query(conn,sql,handler,topicId);
        System.out.println(list);
        return list;
    }

    @Override
    public void addReply(Reply reply) throws SQLException {
        QueryRunner queryRunner = new QueryRunner();
        String sql = "insert into t_reply values(0,?,?,?,?)";
        queryRunner.update(conn,sql,reply.getContent(),reply.getReplyDate(),reply.getAuthor(),reply.getTopic());
    }

    @Override
    public void delReply(Integer id) throws SQLException {
        QueryRunner queryRunner = new QueryRunner();
        String sql = "delete from t_reply where id = ?";
        queryRunner.update(conn,sql,id);
    }

    @Override
    public Reply getReply(Integer id) throws SQLException {
        QueryRunner queryRunner = new QueryRunner();
        String sql = "select * from t_reply where id = ?";
        BeanHandler<Reply> handler = new BeanHandler<>(Reply.class);
        return queryRunner.query(conn,sql,handler,id);
    }
}
