package com.shenzc.controller.service;

import com.shenzc.Entity.Article;
import com.shenzc.Entity.User;
import com.shenzc.commonEntity.Blog;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.List;

/**
 * @author shenzc
 * @create 2019-03-12-11:09
 */
@FeignClient(value = "BLOG-SERVICE-USER")
public interface UserService {

    @RequestMapping("/findFollowArticle")
    List<Article> findFollowArticle(@RequestParam(value = "id") String id);

    @RequestMapping("/findFollowUser")
    List<User> findFollowUser(@RequestParam(value = "id") String id);

    @RequestMapping("/updateBasicUser")
    void updateBasicUser(@RequestBody User user);

    @RequestMapping("/findUserById")
    User findUserById(@RequestParam(value = "id") String id);

    //取消关注人
    @RequestMapping("/cancelFollow")
    void cancelFollow(@RequestParam(value = "userId") String userId,
                      @RequestParam(value = "loginId") String loginId);

    //取消关注文章
    @RequestMapping("/cancelArticle")
    void cancelArticle(@RequestBody Article article,
                      @RequestParam(value = "loginId") String loginId);

    //关注人
    @RequestMapping("/updateUserFollow")
    void updateUserFollow(@RequestParam(value = "userId") String userId,
                          @RequestParam(value = "loginId") String loginId);

    //关注文章
    @RequestMapping("/updateUserArticle")
    void updateUserArticle(@RequestBody Article article,
                          @RequestParam(value = "loginId") String loginId);


    @RequestMapping("/updateUser")
    Blog updateUser(@RequestBody User user,
                    @RequestParam(value = "id") String id);


    @RequestMapping("/becomeVip")
    Blog becomeVip(@RequestParam(value = "id") String id,
                   @RequestParam(value = "vip") String vip);

}
