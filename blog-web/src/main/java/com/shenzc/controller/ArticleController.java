package com.shenzc.controller;


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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;


/**
 * @author shenzc
 * @create 2019-03-09-12:48
 */
@Controller
public class ArticleController<controller> {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private UserService userService;



    @RequestMapping("/findArticleByUserId")
    @ResponseBody
    public Object findArticleByUserId(String id,String title,String articleId,int rows,int page){
        return articleService.findArticleByUserId(id,title,articleId,rows,page);
    }

    @RequestMapping("/editArticle")
    @ResponseBody
    public Blog editArticle(Article article, String id){
        return articleService.editArticle(article,id);
    }

    @RequestMapping("/deleteArticle")
    @ResponseBody
    public Blog deleteArticle(String id){
        Article article = articleService.findArticleByArticleId(id);
        Blog blog = articleService.deleteArticleByarticleId(id);
        //添加一遍文章的同时，修改user表中的myarticleNum字段-1
        //添加文章之后，跟新user表中的myArticle字段
        increaseUser(article);
        return blog;
    }



    @RequestMapping("/findArticleAndRepley")
    public String findArticleAndRepley(String id, Map map,HttpSession session){
        Article article = articleService.findArticleByArticleId(id);
        User loginUser = (User)session.getAttribute("user");
        //只有登陆，浏览量才增加
        if(loginUser != null){
            String loginId = loginUser.getUserId();
            //如过浏览帖子的是本人，则浏览量不增加
            if(!article.getAuthorId().equals(loginId)){
                //将单个文章的浏览量+1
                article.setSingleSeePerson(article.getSingleSeePerson()+1);
                articleService.editArticleNoPass(article,article.getArticleId());
                //获得该文章的作者信息，然后将总浏览量+1
                User user = userService.findUserById(article.getAuthorId());
                user.setSeePerson(user.getSeePerson()+1);
                userService.updateUser(user,user.getUserId());
            }
        }
        List<Reply> replyList = articleService.findReplyByArticleId(id);
        if(loginUser != null){
            String loginUserArticle = loginUser.getArticle();
            List<MyArticleJson> myArticleJsons = JsonUtils.jsonToList(loginUserArticle, MyArticleJson.class);
            if(myArticleJsons != null){
                for (MyArticleJson myArticleJson:myArticleJsons) {
                    if(myArticleJson.getName().equals(article.getTitle())){
                        article.setUtf1("已经被关注了");
                    }
                }
            }
        }
        map.put("article",article);
        map.put("replyList",replyList);
        return "oneArticle";
    }

    @RequestMapping("/show/{url}")
    public String  findArticleByCategoryId(@PathVariable("url") String id, Map map){
        List<Article> articleList = articleService.findArticleByCategoryId(id);
        map.put("articleList",articleList);
        return "showArticle";
    }


    @RequestMapping("/submitArticle")
    public String submitArticle(Article article,HttpSession session){
        String loginId = ((User)session.getAttribute("user")).getUserId();
        article.setAuthorId(loginId);
        Blog blog = articleService.submitArticle(article);
        //添加一遍文章的同时，修改user表中的myarticleNum字段+1
        //添加文章之后，跟新user表中的myArticle字段
        updateUser(article);
        return "article";
    }

    public void updateUser(Article article){
        User user = userService.findUserById(article.getAuthorId());
        user.setMyArticleNum(user.getMyArticleNum()+1);
        String articleJson = user.getMyArticle();
        if(articleJson == "" || articleJson == null){
            articleJson = "[{\"name\":\""+article.getTitle()+"\"}]";
        }else {
            List<MyArticleJson> myJsonList = JsonUtils.jsonToList(articleJson, MyArticleJson.class);
            MyArticleJson myJson = new MyArticleJson();
            myJson.setName(article.getTitle());
            myJsonList.add(myJson);
            articleJson = JsonUtils.objectToJson(myJsonList);
        }
        user.setMyArticle(articleJson);
        userService.updateUser(user,article.getAuthorId());
    }

    public void increaseUser(Article article){
        User user = userService.findUserById(article.getAuthorId());
        user.setMyArticleNum(user.getMyArticleNum()-1);
        String articleJson = user.getMyArticle();
        List<MyArticleJson> myJsonList = JsonUtils.jsonToList(articleJson, MyArticleJson.class);
        MyArticleJson myJson = new MyArticleJson();
        myJson.setName(article.getTitle());
        myJsonList.remove(myJson);
        articleJson = JsonUtils.objectToJson(myJsonList);
        user.setMyArticle(articleJson);
        userService.updateUser(user,article.getAuthorId());
    }


    @RequestMapping("/reply")
    @ResponseBody
    public Blog reply(String articleId,String parentId,String content,HttpSession session){
        String loginId = ((User)session.getAttribute("user")).getUserId();
        return articleService.reply(articleId,parentId,content,loginId);
    }

}
