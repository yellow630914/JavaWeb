package qqzone.service;

import qqzone.pojo.HostReply;

import java.sql.SQLException;

public interface HostReplyService {
    HostReply getHostReplyByReplyId(Integer replyId) throws SQLException;
    void delHostReply(Integer id) throws SQLException;
}
