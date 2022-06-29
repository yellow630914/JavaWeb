package qqzone.service;

import qqzone.pojo.Reply;
import qqzone.pojo.Topic;

import java.sql.SQLException;
import java.util.List;

public interface ReplyService {
    //根据topic的id获取关联的所有的回复
    List<Reply> getReplyListByTopicId(Integer topicId) throws SQLException;
    //添加回复
    void addReply(Reply reply) throws SQLException;
    //删除指定的回复
    void delReply(Integer id) throws SQLException;
    //删除指定的日志关联的所有的回复
    void delReplyList(Topic topic) throws SQLException;
}
