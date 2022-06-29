package qqzone.controller;

import qqzone.pojo.Reply;
import qqzone.pojo.UserBasic;
import qqzone.service.ReplyService;

import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.sql.SQLException;

public class ReplyController {
    private ReplyService replyService;

    public String addReply(String content, Integer topicId , HttpSession session) throws SQLException {
        UserBasic author  = (UserBasic) session.getAttribute("userBasic");
        Reply reply = new Reply();
        reply.setContent(content);
        reply.setReplyDate(new Date(System.currentTimeMillis()));
        reply.setAuthor(author.getId());
        reply.setTopic(topicId);
        replyService.addReply(reply);
        return "redirect:topic.do?operate=topicDetail&id="+topicId;
    }

    public String delReply(Integer replyId, Integer topicId) throws SQLException {
        replyService.delReply(replyId);
        return "redirect:topic.do?operate=topicDetail&id="+topicId;
    }
}
