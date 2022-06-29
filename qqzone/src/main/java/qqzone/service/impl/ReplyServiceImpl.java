package qqzone.service.impl;

import qqzone.dao.ReplyDAO;
import qqzone.dao.UserBasicDAO;
import qqzone.pojo.HostReply;
import qqzone.pojo.Reply;
import qqzone.pojo.Topic;
import qqzone.pojo.UserBasic;
import qqzone.service.HostReplyService;
import qqzone.service.ReplyService;
import qqzone.service.UserBasicService;

import java.sql.SQLException;
import java.util.List;

public class ReplyServiceImpl implements ReplyService {

    private ReplyDAO replyDAO;
    //此處引入的是其他的Service,而不是DAO
    //其他POJO對應的業務邏輯是封裝在service層的,我需要調用別人的業務邏輯方法,而不要考慮別的service內部細節
    private HostReplyService hostReplyService;
    private UserBasicService userBasicService;

    @Override
    public List<Reply> getReplyListByTopicId(Integer topicId) throws SQLException {
        List<Reply> replyList = replyDAO.getReplyList(topicId);
        for (int i = 0; i < replyList.size(); i++) {
            Reply reply = replyList.get(i);
            //1.將關聯的author設置
            UserBasic userBasic = userBasicService.getUserBasicById(reply.getAuthor());
            reply.setAuthorDetails(userBasic);
            //2.將關聯的hostReply設置
            HostReply hostReply = hostReplyService.getHostReplyByReplyId(reply.getId());
            reply.setHostReply(hostReply);
        }
        return replyList;
    }

    @Override
    public void addReply(Reply reply) throws SQLException {
        replyDAO.addReply(reply);
    }

    @Override
    public void delReply(Integer id) throws SQLException {
        Reply reply = replyDAO.getReply(id);
        if (reply!=null) {
            HostReply hostReply = hostReplyService.getHostReplyByReplyId(reply.getId());
            if (reply.getHostReply()!=null) {
                hostReplyService.delHostReply(hostReply.getId());
            }
            replyDAO.delReply(id);
        }
    }

    @Override
    public void delReplyList(Topic topic) throws SQLException {
        List<Reply> replyList = replyDAO.getReplyList(topic.getId());
        if(replyList!=null){
            for(Reply reply:replyList){
                delReply(reply.getId());
            }
        }
    }
}
