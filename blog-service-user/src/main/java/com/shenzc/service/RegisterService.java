package com.shenzc.service;

import com.shenzc.Entity.User;
import com.shenzc.commonEntity.Blog;
import com.shenzc.mapper.RegisterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

/**
 * @author shenzc
 * @create 2019-01-07-14:06
 */
@Service
public class RegisterService {

    @Autowired
    private RegisterMapper registerMapper;

    public Blog register(User user){
        //查询查询出来有用户，证明有名字是一样的用户，则注册失败
        if(registerMapper.findUserByName(user.getUsername())!=null){
            return new Blog(false,"注册失败,名字重复");
        }else{
            User registerUser = new User();
            registerUser.setUserId(UUID.randomUUID().toString());
            registerUser.setUsername(user.getUsername());
            registerUser.setPassword(user.getPassword());
            registerUser.setHead(user.getHead());
            registerUser.setCreateTime(new Date().toString());
            registerUser.setBirthday(user.getBirthday());
            registerUser.setIsSupper("N");
            registerUser.setActive("N");
            registerMapper.insert(registerUser);
            return new Blog(true,"注册成功");
        }
    }

}
