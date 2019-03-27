package com.shenzc.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.shenzc.Entity.Article;
import com.shenzc.Entity.User;
import com.shenzc.commonEntity.Blog;
import com.shenzc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author shenzc
 * @create 2019-01-09-10:04
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;


    /**
     *
     * @param rows
     * @param page
     * @param userId
     * @param username
     * @return
     */
    @RequestMapping("/findAllUser")
    public Object findAllUser(int rows, int page,String userId,String username) {
        Page<User> userPage = PageHelper.startPage(page, rows);
        List<User> userList = userService.findAllUser(userId,username);
        Map<String, Object> map = new HashMap<>();
        map.put("total", userList.size());
        map.put("rows", userList);
        return map;
    }

    @RequestMapping("/addUser")
    public Blog addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @RequestMapping("/updateUser")
    public Blog updateUser(@RequestBody User user, String id) {
        return userService.updateUser(user, id);
    }

    @RequestMapping("/deleteUser")
    public Blog deleteUser(String id) {
        return userService.deleteUser(id);
    }


    @RequestMapping("/findUserById")
    public User findUserById(String id){
        return userService.findUserById(id);
    }


    @RequestMapping("/findFollowArticle")
    public List<Article> findArticleByTitle(String id){
        return userService.findFollowArtcle(id);

    }

    @RequestMapping("/findFollowUser")
    public List<User> findUserByUsername(String id){
        return userService.findFollowUser(id);
    }

    @RequestMapping("/updateBasicUser")
    public void updateBasicUser(@RequestBody User user){
        userService.updateBasicUser(user);
    }

    //取消关注人
    @RequestMapping("/cancelFollow")
    public void cancelFollow(String userId,String loginId){
        userService.cancelFollow(userId,loginId);
    }

    //取消关注文章
    @RequestMapping("/cancelArticle")
    public void cancelArticle(@RequestBody Article article,String loginId){
        userService.cancelArticle(article,loginId);
    }

    //关注人
    @RequestMapping("/updateUserFollow")
    public void updateUserFollow(String userId,String loginId){
        userService.updateUser(loginId,userId);
    }

    //关注文章
    @RequestMapping("/updateUserArticle")
    public void updateUserArticle(@RequestBody Article article,String loginId){
        userService.updateUserArticle(loginId,article);
    }


    @RequestMapping("/becomeVip")
    public Blog becomeVip(String id,String vip) throws ParseException {
        return userService.vip(id,vip);
    }


}
