package qqzone.dao.daoImpl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import qqzone.dao.TopicDAO;
import qqzone.pojo.Topic;
import qqzone.pojo.UserBasic;
import ssm.sql.JDBCUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TopicDAOImpl implements TopicDAO {
    private Connection conn = JDBCUtils.getDruidConnection();

    public TopicDAOImpl() throws SQLException {
    }

    @Override
    public List<Topic> getTopicList(UserBasic userBasic) throws SQLException {
        QueryRunner queryRunner = new QueryRunner();
        String sql = "select * from t_topic where author = ?";
        BeanListHandler<Topic> handler = new BeanListHandler<>(Topic.class);
        return queryRunner.query(conn,sql,handler,userBasic.getId());
    }

    @Override
    public void addTopic(Topic topic) {

    }

    @Override
    public void delTopic(Topic topic) throws SQLException {
        QueryRunner queryRunner = new QueryRunner();
        String sql = "delete from t_topic where id = ?";
        queryRunner.update(conn,sql,topic.getId());
    }

    @Override
    public Topic getTopic(Integer id) throws SQLException {
        QueryRunner queryRunner = new QueryRunner();
        String sql = "select * from t_topic where id = ? ";
        BeanHandler<Topic> handler = new BeanHandler<>(Topic.class);
        return queryRunner.query(conn,sql,handler,id);
    }
}
