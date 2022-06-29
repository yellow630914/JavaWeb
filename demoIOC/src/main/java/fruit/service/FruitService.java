package fruit.service;

import fruit.Fruit;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface FruitService {
    //获取指定页面的库存列表信息
    List<Fruit> getFruitList(Connection conn,String keyword , Integer pageNo) throws SQLException;
    //添加库存记录信息
    void addFruit(Connection conn,Fruit fruit) throws SQLException;
    //根据id查看指定库存记录
    Fruit getFruitByFid(Connection conn,Integer fid) throws SQLException;
    //删除特定库存记录
    void delFruit(Connection conn,Integer fid) throws SQLException;
    //获取总页数
    Long getPageCount(Connection conn,String keyword) throws SQLException;
    //修改特定库存记录
    void updateFruit(Connection conn,Fruit fruit) throws SQLException;
}
