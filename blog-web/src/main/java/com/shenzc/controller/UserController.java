package com.shenzc.controller;


import com.netflix.ribbon.proxy.annotation.Http;
import com.shenzc.CommonUtils.FormatDateUtils;
import com.shenzc.CommonUtils.JsonUtils;
import com.shenzc.Entity.Article;
import com.shenzc.Entity.Reply;
import com.shenzc.Entity.User;
import com.shenzc.commonEntity.Blog;
import com.shenzc.commonEntity.MyArticleJson;
import com.shenzc.controller.service.ArticleService;
import com.shenzc.controller.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author shenzc
 * @create 2019-03-09-12:02
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ArticleService articleService;

    @RequestMapping("/user")
    public String findUserById(String id, Map map){
        User user = userService.findUserById(id);
        map.put("user",user);
        return "user";
    }


    @RequestMapping("/basicUser")
    public String basicUser(String id, Map map, HttpSession session){
        return basicUser1(id,map,session);
    }
    public String  basicUser1(String id, Map map, HttpSession session){
        List<Article> articleList = articleService.findAllArticleNoPage(id,null,null);
        map.put("articleList",articleList);
        User user = userService.findUserById(id);
        User loginUser = (User) session.getAttribute("user");
        if(loginUser != null){
            String followJson = loginUser.getFollow();
            List<MyArticleJson> myArticleJsons = JsonUtils.jsonToList(followJson, MyArticleJson.class);
            if (myArticleJsons != null){
                for (MyArticleJson myArticleJson:myArticleJsons) {
                    if (myArticleJson.getName().equals(user.getUsername())){
                        user.setUtf1("已经被关注了");
                    }
                }
            }
        }
        map.put("user",user);
        return "basicUser";
    }

    @RequestMapping("/findFollowArticle")
    public String findArticleByTitle(String id, Map map){
        List<Article> articleList = userService.findFollowArticle(id);
        map.put("articleList",articleList);
        return "myShowArticle";
    }

    @RequestMapping("/findFollowUser")
    public String  findFollowUser(String id, Map map){
        List<User> userList = userService.findFollowUser(id);
        map.put("userList",userList);
        return "showUser";
    }

    //取消关注人
    @RequestMapping("/cancelFollow")
    public String cancelFollow(String id,HttpSession session,Map map){
        String loginId = ((User)session.getAttribute("user")).getUserId();
        userService.cancelFollow(id,loginId);
        List<User> userList = userService.findFollowUser(loginId);
        map.put("userList",userList);
        return "showUser";
    }

    //取消关注人
    @RequestMapping("/cancelFollow2")
    public String cancelFollow2(String id,HttpSession session,Map map){
        String loginId = ((User)session.getAttribute("user")).getUserId();
        userService.cancelFollow(id,loginId);
        User user = userService.findUserById(loginId);
        session.setAttribute("user",user);
        return basicUser1(id,map,session);
    }

    //取消关注文章
    @RequestMapping("/cancelArticle")
    public String cancelArticle(String id,HttpSession session,Map map){
        String loginId = ((User)session.getAttribute("user")).getUserId();
        Article article = articleService.findArticleByArticleId(id);
        userService.cancelArticle(article,loginId);
        article.setGoods(article.getGoods()-1);
        articleService.editArticleNoPass(article,article.getArticleId());
        List<Article> articleList = userService.findFollowArticle(loginId);
        map.put("articleList",articleList);
        return "myShowArticle";
    }

    //取消关注文章
    @RequestMapping("/cancelArticle2")
    public String cancelArticle2(String id,HttpSession session,Map map){
        User loginUser = (User)session.getAttribute("user");
        String loginId = loginUser.getUserId();
        Article article = articleService.findArticleByArticleId(id);
        userService.cancelArticle(article,loginId);
        article.setGoods(article.getGoods()-1);
        articleService.editArticleNoPass(article,article.getArticleId());
        return findArticleAndReply(loginId,id,map);
    }

    //关注人
    @RequestMapping("/updateUserFollow")
    public String updateUserFollow(String id,HttpSession session,Map map){
        String loginId = ((User)session.getAttribute("user")).getUserId();
        userService.updateUserFollow(id,loginId);
        User user = userService.findUserById(loginId);
        session.setAttribute("user",user);
        return basicUser1(id,map,session);
    }

    //关注文章
    @RequestMapping("/updateUserArticle")
    public String updateUserArticle(String id,HttpSession session,Map map){
        User loginUser = (User)session.getAttribute("user");
        String loginId = loginUser.getUserId();
        Article article = articleService.findArticleByArticleId(id);
        article.setGoods(article.getGoods()+1);
        articleService.editArticleNoPass(article,article.getArticleId());
        userService.updateUserArticle(article,loginId);
        User user = userService.findUserById(loginId);
        session.setAttribute("user",user);
        return findArticleAndReply(loginId,id,map);
    }

    public String findArticleAndReply(String loginId,String id,Map map){
        User loginUser = userService.findUserById(loginId);
        //重新获取文章和评论，但浏览量不增加
        Article article1 = articleService.findArticleByArticleId(id);
        String loginUserArticle = loginUser.getArticle();
        List<MyArticleJson> myArticleJsons = JsonUtils.jsonToList(loginUserArticle, MyArticleJson.class);
        if(myArticleJsons != null){
            for (MyArticleJson myArticleJson:myArticleJsons) {
                if(myArticleJson.getName().equals(article1.getTitle())){
                    article1.setUtf1("已经被关注了");
                }
            }
        }
        List<Reply> replyList = articleService.findReplyByArticleId(id);
        map.put("article",article1);
        map.put("replyList",replyList);
        return "oneArticle";
    }

    //成为vip
    @RequestMapping("/becomeVip")
    @ResponseBody
    public Blog becomeVip(String id,String vip){
        return userService.becomeVip(id,vip);
    }


    @RequestMapping("/updateBasicUser")
    public String updateBasicUser(User user, BindingResult bindingResult , @RequestParam(value = "head") MultipartFile file,
                                  HttpSession session,Map map)
            throws IOException {
        User loginUser = (User)session.getAttribute("user");
        user.setUserId(loginUser.getUserId());
        if(!file.getOriginalFilename().isEmpty()){
            Date date = new Date(System.currentTimeMillis());
            String[] s = file.getOriginalFilename().split("\\.");
            s[0] += date.getTime();
            String name = s[0]+"."+s[1];
            savePicture(file,name);
            user.setHead(name);
        }else {
            user.setHead(loginUser.getHead());
        }
        userService.updateBasicUser(user);
        return "index";
    }

    private void savePicture(MultipartFile file, String name)throws IOException {
        String imagePath = "D:\\Blog\\image";
        File targetFile = new File(imagePath,name);
        file.transferTo(targetFile);
    }
}
