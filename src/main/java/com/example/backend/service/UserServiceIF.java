package com.example.backend.service;


import com.example.backend.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

public interface UserServiceIF {
    int addUser(User user);
    int delUser(User user);
    int updateUser(User user);
    User findUserBYA(String account);
    User findUserBYU(int uid);
    User findUserBYN(String userName);
    User findUserBYM(String mail);
    ArrayList<Integer> reqAllUser();
    ArrayList<Integer> vagueFind(String in);
}
