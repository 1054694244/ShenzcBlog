package com.shenzc.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.shenzc.CommonUtils.BlogUtils;
import com.shenzc.CommonUtils.FormatDateUtils;
import com.shenzc.Entity.Category;
import com.shenzc.Entity.Reply;
import com.shenzc.commonEntity.Blog;
import com.shenzc.Entity.Article;
import com.shenzc.mapper.ArticleMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author shenzc
 * @create 2019-01-15-10:25
 */
@Service
public class ArticleService {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    @Qualifier("articleRedisTemplate")
    RedisTemplate<Object,Article> redisTemplate;
    @Autowired
    RabbitTemplate rabbitTemplate;
    /**
     * 通过作者ID查询所有文章
     * @param authorId ：作者ID
     * @param title ：文章标题
     * @param articleId ：文章ID
     * @return
     */
    public List<Article> findArticleByUserId(String authorId, String title, String articleId){
        List<Article> allArticle = articleMapper.findArticleById(authorId, title, articleId);
        return convert(allArticle);
    }



    /**
     * 查询所有文章
     * @param username ：用户名（可以为null）
     * @param title ：文章标题（可以为null）
     * @param articleId ：文章ID（可以为null）
     * @return
     */
    public List<Article> findAllArticle(String username,String title,String articleId){
        List<Article> allArticle = articleMapper.findAllArticle(username,title,articleId);
        return convert(allArticle);
    }



    /**
     * 通过分类ID查询所有文章
     * 通过分类ID查询文章属于前端主页，访问量较大，将查询的结果存入缓存之中
     * @param categoryId : 分类ID
     * @return
     */
    public List<Article> findArticleByCategoryId(String categoryId){
        try{
            List<Article> jsonArticleList = redisTemplate.opsForList().range("articleList:"+categoryId+"", 0, -1);
            if(jsonArticleList.size()==0 || jsonArticleList == null){
                List<Article> articleList = articleMapper.findArticleByCategoryId(categoryId);
                redisTemplate.opsForList().rightPushAll("articleList:"+categoryId+"",articleList);
                return articleList;
            }else {
                System.out.println("aritilceList调用了缓存");
                return jsonArticleList;
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }


    }


    /**
     * 通过文章ID查询文章
     * 将查询出来的文章存入缓存之中
     * @param articleId : 文章ID
     * @return
     */
    public Article findArticleByArticleId(String articleId){
        try{
            Article jsonArticle = redisTemplate.opsForValue().get("article:"+articleId);
            if(jsonArticle == null){
                Article article = articleMapper.findArticleByArticleId(articleId);
                redisTemplate.opsForValue().set("article:"+articleId,article);
                return article;
            }else {
                System.out.println("aritle调用了缓存");
                return jsonArticle;
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }



    /**
     * 添加一篇文章
     * 调用addArticle()
     * @param article ：文章
     * @return
     */
    public Blog submitArticle(Article article){
        int integer = addArticle(article);
        if(integer>0){
            return new Blog(true,"保存成功");
        }else {
            return new Blog(false,"保存失败");
        }
    }



    /**
     * 通过文章ID编辑文章
     * 前端调用，isPass为null，所以直接设置IsPass=3，表示修改之后未审核
     * @param article ：文章
     * @param articleId ：文章ID
     * @param isPass ：isPass=1时，表示审核通过，isPass=2时，表示审核未通过
     * @return
     */
    public Blog editArticle(Article article,String articleId,String isPass){
        Article managerArticle = articleMapper.findArticleByArticleId(articleId);
        if(isPass == null){
            //修改文章之后，重新进行审核
            article.setIsPass("3");
        }else {
            article.setIsPass(isPass);
        }
        try{
            redisTemplate.delete("article:"+articleId);
            redisTemplate.delete("replyList:"+articleId);
            redisTemplate.delete("articleList:"+managerArticle.getCategoryId()+"");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        Integer integer = articleMapper.update(article, new EntityWrapper<Article>().eq("article_id", articleId));
        if(integer>0){
            return new Blog(true,"修改成功");
        }else {
            return new Blog(false,"修改失败");
        }
    }



    /**
     * 通过文章ID编辑文章，不需要审核
     * 前端调用：用户修改article中的Goods字段，每关注或者取消关注一篇文章都需要修改Goods字段。
     * @param article : 文章
     * @param articleId ：文章ID
     * @return
     */
    public Blog editArticleNoPass(Article article,String articleId){
        Integer integer = articleMapper.update(article, new EntityWrapper<Article>().eq("article_id", articleId));
        try{
            redisTemplate.delete("article:"+articleId);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        if(integer>0){
            return new Blog(true,"修改成功");
        }else {
            return new Blog(false,"修改失败");
        }
    }



    /**
     * 通过文章ID删除文章，删除文章的同时，
     * 1.修改Myarticle字段以及num字段
     * 2.如果用户有关注这一篇文章，就需要将用户的article字段修改，article_num字段-1
     * 3.清除单个文章以及他的评论的缓存
     * 4.清除articleList缓存
     * @param ArticleId : 文章ID
     * @return
     */
    public Blog deleteArticleByarticleId(String ArticleId){
        Article article1 = articleMapper.findArticleByArticleId(ArticleId);
        Integer integer = articleMapper.delete(new EntityWrapper<Article>().eq("article_id",ArticleId));
        rabbitTemplate.convertAndSend("exchange.user","user.article",article1);

        try{
            Article article = redisTemplate.opsForValue().get("article:" + ArticleId);
            if(article != null){
                redisTemplate.delete("article:" + ArticleId);
                redisTemplate.delete("replyList:"+ArticleId);
                redisTemplate.delete("articleList:"+article1.getCategoryId()+"");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return BlogUtils.blog(integer,"删除失败","删除成功");
    }











    /**
     * 私有方法 转换所有文章的isPass字段
     * @param allArticle : 所有文章
     * @return
     */
    private List<Article> convert(List<Article> allArticle){
        for (Article article:allArticle) {
            if("0".equals(article.getIsPass())){
                article.setIsPass("未审核");
            }else if ("1".equals(article.getIsPass())){
                article.setIsPass("审核通过");
            }else if ("2".equals(article.getIsPass())){
                article.setIsPass("审核未通过");
            }else if("3".equals(article.getIsPass())){
                article.setIsPass("修改之后未审核");
            }
        }
        return allArticle;
    }



    /**
     * 添加文章
     * 添加文章的同时，要告诉user服务，如果用户有redis缓存，要重新设置
     * @param article ：文章
     * @return
     */
    private int addArticle(Article article){
        //rabbitTemplate.convertAndSend("exchange.user","user.basic",article);
        article.setArticleId(UUID.randomUUID().toString());
        String date = FormatDateUtils.formatDateTime(new Date());
        article.setCreateTime(date);
        article.setUpdateTime(date);
        article.setGoods(0);
        article.setIsPass("0");
        article.setSingleSeePerson(0);
        return articleMapper.insert(article);
    }



}
