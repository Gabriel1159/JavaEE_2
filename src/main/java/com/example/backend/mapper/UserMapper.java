package com.example.backend.mapper;


import com.example.backend.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

@Mapper
public interface UserMapper {
    int addUser(User user);
    int delUser(User user);
    int updateUser(User user);
    User findUserBYA(@Param("account") String account);
    User findUserBYU(@Param("uid") int uid);
    User findUserBYN(@Param("userName") String userName);
    User findUserBYM(@Param("mail") String mail);
    ArrayList<Integer> reqAllUser();
    ArrayList<Integer> vagueFind(@Param("in") String in);
}
