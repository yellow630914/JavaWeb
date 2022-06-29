package fruit.service.impl;

import fruit.Fruit;
import fruit.service.FruitService;
import sql.JDBCUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class FruitServiceImpl implements FruitService {
    @Override
    public List<Fruit> getFruitList(Connection conn,String keyword, Integer pageNo) throws SQLException {
        return JDBCUtils.getFruitListByPageNo(conn,keyword,pageNo);
    }

    @Override
    public void addFruit(Connection conn,Fruit fruit) throws SQLException {
        JDBCUtils.insertFruit(conn,fruit);
        JDBCUtils.setFruit(conn,fruit);
    }

    @Override
    public Fruit getFruitByFid(Connection conn,Integer fid) throws SQLException {
        return JDBCUtils.getFruitByFid(conn,fid);
    }

    @Override
    public void delFruit(Connection conn,Integer fid) throws SQLException {
        JDBCUtils.delFruitById(conn,fid);
    }

    @Override
    public Long getPageCount(Connection conn, String keyword) throws SQLException {
        long count = JDBCUtils.getFruitCount(conn,keyword);
        long pageCount = (count+5-1)/5 ;
        return pageCount;
    }

    @Override
    public void updateFruit(Connection conn,Fruit fruit) throws SQLException {
        JDBCUtils.setFruit(conn,fruit);
    }
}
