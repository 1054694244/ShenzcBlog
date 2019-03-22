package com.shenzc.controller;


import com.shenzc.Entity.User;
import com.shenzc.commonEntity.Blog;
import com.shenzc.controller.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author shenzc
 * @create 2019-03-09-12:02
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/findAllUser")
    public Object findAllUser(int rows, int page,String userId,String username){
        return userService.findAllUser(rows,page,userId,username);
    }

    @RequestMapping("/addUser")
    public Blog addUser(User user){
        return userService.addUser(user);
    }

    @RequestMapping("/updateUser")
    public Blog updateUser(User user,  @RequestParam(value = "id") String id){
        return userService.updateUser(user,id);
    }

    @RequestMapping("/deleteUser")
    public Blog deleteUser(String id){
        return userService.deleteUser(id);
    }

    @RequestMapping("/searchUser")
    public List<User> searchUser(String userId, String username){
        return userService.searchUser(userId,username);
    }

}
