package com.example.backend.service.Impl;

import com.example.backend.mapper.TeamMapper;
import com.example.backend.pojo.User;
import com.example.backend.mapper.UserMapper;
import com.example.backend.service.UserServiceIF;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

@Service("UserService")
public class UserService implements UserServiceIF {

    @Resource
    private UserMapper userMapper;


    @Override
    public int addUser(User user) {
        int account = 0;
        try {
            user.setF_account(String.valueOf(account));
            userMapper.addUser(user);
        }catch (Exception e)
        {
            return -1;
        }
        return 0;
    }

    @Override
    public int delUser(User user) {
        try {
            userMapper.delUser(user);
        }catch (Exception e)
        {
            return -1;
        }
        return 0;
    }

    @Override
    public int updateUser(User user) {

            return userMapper.updateUser(user);

    }

    @Override
    public User findUserBYA(String account) {
        try {
            return userMapper.findUserBYA(account);
        }catch (Exception e)
        {
            return null;
        }
    }

    @Override
    public User findUserBYU(int uid) {
        try {
            return userMapper.findUserBYU(uid);
        }catch (Exception e)
        {
            return null;
        }
    }

    @Override
    public User findUserBYN(String userName) {
        return userMapper.findUserBYN(userName);
    }

    @Override
    public User findUserBYM(String mail) {
        return userMapper.findUserBYM(mail);
    }

    @Override
    public ArrayList<Integer> reqAllUser() {
        return userMapper.reqAllUser();
    }

    @Override
    public ArrayList<Integer> vagueFind(String in) {
        return userMapper.vagueFind(in);
    }
}
