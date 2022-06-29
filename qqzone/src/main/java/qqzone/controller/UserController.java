package qqzone.controller;

import qqzone.pojo.Topic;
import qqzone.pojo.UserBasic;
import qqzone.service.TopicService;
import qqzone.service.UserBasicService;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

public class UserController {
    private UserBasicService userBasicService;
    private TopicService topicService ;

    public String login(String loginId, String pwd, HttpSession session) throws SQLException {
        //1.登錄驗證
        UserBasic userBasic = userBasicService.login(loginId, pwd);
        if(userBasic!=null){
            //1-1 獲取好友
            List<UserBasic> friendList = userBasicService.getFriendList(userBasic);
            //1-2 獲取日志
            List<Topic> topicList = topicService.getTopicList(userBasic);

            userBasic.setFriendList(friendList);
            userBasic.setTopicList(topicList);

            //userBasic这个key保存的是登陆者的信息
            //friend这个key保存的是当前进入的是谁的空间
            session.setAttribute("userBasic",userBasic);
            session.setAttribute("friend",userBasic);
            return "index";
        }else{
            return "login";
        }
    }

    public String friend(Integer id,HttpSession session) throws SQLException {
        UserBasic currFriend = userBasicService.getUserBasicById(id);
        List<Topic> topicList = topicService.getTopicList(currFriend);
        currFriend.setTopicList(topicList);
        session.setAttribute("friend",currFriend);
        return "index";
    }
}
