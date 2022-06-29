package qqzone.dao;

import qqzone.pojo.HostReply;

import java.sql.SQLException;

public interface HostReplyDAO {
    //根据replyId查询关联的HostReply实体
    HostReply getHostReplyByReplyId(Integer replyId) throws SQLException;
    //删除特定的HostReply
    void delHostReply(Integer id) throws SQLException;
}
