package com.shenzc.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.shenzc.CommonUtils.FormatDateUtils;
import com.shenzc.Entity.Reply;
import com.shenzc.commonEntity.Blog;
import com.shenzc.Entity.Article;
import com.shenzc.mapper.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author shenzc
 * @create 2019-01-15-10:25
 */
@Service
public class ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

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
     * @param categoryId : 分类ID
     * @return
     */
    public List<Article> findArticleByCategoryId(String categoryId){
        return articleMapper.findArticleByCategoryId(categoryId);
    }


    /**
     * 通过文章ID查询文章
     * @param articleId : 文章ID
     * @return
     */
    public Article findArticleByArticleId(String articleId){
        return articleMapper.findArticleByArticleId(articleId);
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
        if(isPass == null){
            //修改文章之后，重新进行审核
            article.setIsPass("3");
        }else {
            article.setIsPass(isPass);
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
        if(integer>0){
            return new Blog(true,"修改成功");
        }else {
            return new Blog(false,"修改失败");
        }
    }



    /**
     * 通过文章ID删除文章
     * @param ArticleId : 文章ID
     * @return
     */
    public Blog deleteArticleByarticleId(String ArticleId){
        Integer integer = articleMapper.delete(new EntityWrapper<Article>().eq("article_id",ArticleId));
        if(integer>0){
            return new Blog(true,"删除成功");
        }else {
            return new Blog(false,"删除失败");
        }
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
     * @param article ：文章
     * @return
     */
    private int addArticle(Article article){
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
