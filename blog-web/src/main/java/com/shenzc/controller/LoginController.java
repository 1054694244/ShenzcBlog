package com.shenzc.controller;

import com.shenzc.CommonUtils.FormatDateUtils;
import com.shenzc.Entity.User;
import com.shenzc.controller.service.UserService;
import com.shenzc.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author shenzc
 * @create 2019-03-09-12:00
 */
@Controller
public class LoginController {

    @Autowired
    LoginService loginService;
    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public String login(User user, HttpServletRequest request){
        if(user.getUsername()==null){
            return "login";
        }
        User loginUser = loginService.findByUser(user);
        if(loginUser!= null){
            loginUser.setLastLoginTime(FormatDateUtils.formatDateTime(new Date()));
            userService.updateUser(loginUser,loginUser.getUserId());
            request.getSession().setAttribute("user",loginUser);
            return "index";
        }else{
            return "login";
        }
    }

    @RequestMapping("/exit")
    public String exit(HttpServletRequest request){
        request.getSession().removeAttribute("user");
        return "index";
    }


}
