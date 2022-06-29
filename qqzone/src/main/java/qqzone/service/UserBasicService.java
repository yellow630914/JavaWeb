package qqzone.service;

import qqzone.pojo.UserBasic;

import java.sql.SQLException;
import java.util.List;

public interface UserBasicService {

    UserBasic login(String loginId,String pwd) throws SQLException;
    List<UserBasic> getFriendList(UserBasic userBasic) throws SQLException;
    UserBasic getUserBasicById(Integer id) throws SQLException;
}
