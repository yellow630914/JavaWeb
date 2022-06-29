package qqzone.dao;

import qqzone.pojo.Topic;
import qqzone.pojo.UserBasic;

import java.sql.SQLException;
import java.util.List;

public interface TopicDAO {
    //获取指定用户的日志列表
    public List<Topic> getTopicList(UserBasic userBasic) throws SQLException;
    //添加日志
    public void addTopic(Topic topic)  throws SQLException;
    //删除日志
    void delTopic(Topic topic)  throws SQLException;
    //获取特定日志信息
    Topic getTopic(Integer id)  throws SQLException;
}
