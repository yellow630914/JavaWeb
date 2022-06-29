package qqzone.service.impl;

import qqzone.dao.HostReplyDAO;
import qqzone.pojo.HostReply;
import qqzone.service.HostReplyService;

import java.sql.SQLException;

public class HostReplyServiceImpl implements HostReplyService {
    private HostReplyDAO hostReplyDAO;
    @Override
    public HostReply getHostReplyByReplyId(Integer replyId) throws SQLException {
        return hostReplyDAO.getHostReplyByReplyId(replyId);
    }

    @Override
    public void delHostReply(Integer id) throws SQLException {
        hostReplyDAO.delHostReply(id);
    }
}
