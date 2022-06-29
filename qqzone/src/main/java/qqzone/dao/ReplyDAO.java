package qqzone.dao;

import qqzone.pojo.Reply;
import qqzone.pojo.Topic;

import java.sql.SQLException;
import java.util.List;

public interface ReplyDAO {
    //获取指定日志的回复列表
    List<Reply> getReplyList(Integer topicId) throws SQLException;
    //添加回复
    void addReply(Reply reply) throws SQLException;
    //删除回复
    void delReply(Integer id) throws SQLException;

    Reply getReply(Integer id) throws SQLException;
}
