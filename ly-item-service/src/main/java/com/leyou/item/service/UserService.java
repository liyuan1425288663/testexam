package com.leyou.item.service;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.excption.LyExcption;
import com.leyou.common.utills.MD5;
import com.leyou.entity.User;
import com.leyou.item.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User getUser(String username){
        User user = new User();
        user.setUsername(username);
        User userget = userMapper.selectOne(user);

        //判断结果
        if (userget==null){
            throw   new LyExcption(ExceptionEnum.USER_NULL);
        }
        return userget;
    }
}
