package qqzone.service;

import qqzone.pojo.Topic;
import qqzone.pojo.UserBasic;

import java.sql.SQLException;
import java.util.List;

public interface TopicService {
    //查询特定用户的日志列表
    List<Topic> getTopicList(UserBasic userBasic) throws SQLException;
    Topic getTopicById(Integer id) throws SQLException;

    void delTopic(Integer topicId) throws SQLException;
}
