package qqzone.service.impl;

import qqzone.dao.UserBasicDAO;
import qqzone.pojo.UserBasic;
import qqzone.service.UserBasicService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserBasicServiceImpl implements UserBasicService {
    private UserBasicDAO userBasicDAO = null;

    @Override
    public UserBasic login(String loginId, String pwd) throws SQLException {
        UserBasic userBasic = userBasicDAO.getUserBasic(loginId,pwd);
        return  userBasic;
    }

    @Override
    public List<UserBasic> getFriendList(UserBasic userBasic) throws SQLException {
        List<UserBasic> userBasicList = userBasicDAO.getUserBasicList(userBasic);
        List<UserBasic> friendsList = new ArrayList<>(userBasicList.size());
        for (int i = 0; i < userBasicList.size(); i++) {
            UserBasic friend = userBasicList.get(i);
            friend = userBasicDAO.getUserBasicById(friend.getId());
            friendsList.add(friend);
        }
        return friendsList;
    }

    @Override
    public UserBasic getUserBasicById(Integer id) throws SQLException {
        return userBasicDAO.getUserBasicById(id);
    }


}
