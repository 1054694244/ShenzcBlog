package com.shenzc.service;

import com.shenzc.Entity.User;
import com.shenzc.mapper.LoginMaper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author shenzc
 * @create 2019-01-04-14:50
 */
@Service
public class LoginService {

    @Autowired
    private LoginMaper login;

    public User findByUser(User user){
        return login.findByUser(user);
    }

}
