package com.shenzc.controller;

import com.shenzc.Entity.User;
import com.shenzc.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author shenzc
 * @create 2019-03-09-12:00
 */
@Controller
public class LoginController {

    @Autowired
    LoginService loginService;

    @RequestMapping("/login")
    public String login(User user, HttpServletRequest request){
        if(user.getUsername()==null){
            return "login";
        }
        User loginUser = loginService.findByUser(user);
        if(loginUser!= null){
            request.getSession().setAttribute("user",loginUser);
            return "index";
        }else{
            return "login";
        }
    }




}
