package qqzone.service.impl;

import qqzone.dao.TopicDAO;
import qqzone.pojo.Reply;
import qqzone.pojo.Topic;
import qqzone.pojo.UserBasic;
import qqzone.service.ReplyService;
import qqzone.service.TopicService;
import qqzone.service.UserBasicService;

import java.sql.SQLException;
import java.util.List;

public class TopicServiceImpl implements TopicService {
    private TopicDAO topicDAO = null;
    private ReplyService replyService;
    private UserBasicService userBasicService;
    @Override
    public List<Topic> getTopicList(UserBasic userBasic) throws SQLException {
        return topicDAO.getTopicList(userBasic);
    }

    public Topic getAuthorDetails(Integer id) throws SQLException {
        Topic topic = topicDAO.getTopic(id);
        UserBasic userBasic = userBasicService.getUserBasicById(topic.getAuthor());
        topic.setAuthorDetails(userBasic);
        return topic;
    }

    @Override
    public Topic getTopicById(Integer id) throws SQLException {
        Topic topic = getAuthorDetails(id);
        List<Reply> replyList = replyService.getReplyListByTopicId(topic.getId());
        topic.setReplyList(replyList);

        return topic;
    }

    @Override
    public void delTopic(Integer topicId) throws SQLException {
        Topic topic = topicDAO.getTopic(topicId);
        if(topic!=null){
            replyService.delReplyList(topic);
            topicDAO.delTopic(topic);
        }
    }


}
