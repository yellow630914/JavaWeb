package qqzone.dao.daoImpl;


import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import qqzone.dao.UserBasicDAO;
import qqzone.pojo.UserBasic;
import ssm.sql.JDBCUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserBasicDAOImpl implements UserBasicDAO {
    private Connection conn = JDBCUtils.getDruidConnection();

    public UserBasicDAOImpl() throws SQLException {
    }

    @Override
    public UserBasic getUserBasic(String loginId, String pwd) throws SQLException {
        QueryRunner queryRunner = new QueryRunner();
        String sql = "select * from t_user_basic where loginId = ? and pwd = ?";
        BeanHandler<UserBasic> handler = new BeanHandler<>(UserBasic.class);
        return queryRunner.query(conn,sql,handler ,loginId , pwd);
    }

    @Override
    public List<UserBasic> getUserBasicList(UserBasic userBasic) throws SQLException {
        QueryRunner queryRunner = new QueryRunner();
        String sql = "SELECT fid as 'id' FROM t_friend WHERE uid = ?";
        BeanListHandler<UserBasic> handler = new BeanListHandler<>(UserBasic.class);
        return queryRunner.query(conn,sql,handler,userBasic.getId());
    }

    @Override
    public UserBasic getUserBasicById(Integer id) throws SQLException {
        QueryRunner queryRunner = new QueryRunner();
        String sql = "select * from t_user_basic where id = ? ";
        BeanHandler<UserBasic> handler = new BeanHandler<>(UserBasic.class);
        return queryRunner.query(conn,sql,handler,id);
    }
}
