package qqzone.dao;

import qqzone.pojo.UserBasic;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface UserBasicDAO {
    //根据账号和密码获取特定用户信息
    public UserBasic getUserBasic(String loginId , String pwd ) throws SQLException;
    //获取指定用户的所有好友列表
    public List<UserBasic> getUserBasicList(UserBasic userBasic) throws SQLException;
    //根据id查询UserBasic的信息
    UserBasic getUserBasicById(Integer id) throws SQLException;
}
