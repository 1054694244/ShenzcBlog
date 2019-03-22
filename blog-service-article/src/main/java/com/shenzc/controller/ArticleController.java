package com.shenzc.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.shenzc.commonEntity.Blog;
import com.shenzc.Entity.Article;
import com.shenzc.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author shenzc
 * @create 2019-01-15-10:28
 */
@RestController
public class ArticleController {

    @Autowired
    private ArticleService articleService;


    /**
     * 通过作者authorId查询所有文章
     * @param authorId ：作者ID
     * @param title ：文章标题
     * @param articleId ：文章ID
     * @param rows ：每页行数
     * @param page ：第几页
     * @return
     */
    @RequestMapping("/findAllArticleByAuthorId")
    public Object findAllArticleByAuthorId(String authorId,String title,String articleId,int rows,int page){
        Page<Article> articlePage = PageHelper.startPage(page, rows);
        List<Article> articleList = articleService.findArticleByUserId(authorId,title,articleId);
        Map<String,Object> map = new HashMap<>();
        map.put("total",articlePage.getTotal());
        map.put("rows",articleList);
        return map;
    }


    /**
     * 通过作者authorId查询所有文章，不分页
     * @param authorId ：作者ID
     * @param title ：文章标题
     * @param articleId ：文章ID
     * @return
     */
    @RequestMapping("/findAllArticleByAuthorIdNoPage")
    public List<Article> findAllArticleByAuthorIdNoPage(String authorId, String title, String articleId){
        return articleService.findArticleByUserId(authorId,title,articleId);
    }



    /**
     * 查询所有文章
     * @param title ：文章标题
     * @param username ：作者名称
     * @param articleId ：文章ID
     * @param rows ：每页多少行
     * @param page ：第几页
     * @return
     */
    @RequestMapping("/findAllArticle")
    public Object findAllArticle(String title,String username,String articleId,int rows,int page){
        Page<Article> articlePage = PageHelper.startPage(page,rows);
        List<Article> articleList = articleService.findAllArticle(username,title,articleId);
        Map<String,Object> map = new HashMap<>();
        map.put("total",articlePage.getTotal());
        map.put("rows",articleList);
        return map;
    }


    /**
     * 通过分类ID查询所有文章
     * @param categoryId ：分类ID
     * @return
     */
    @RequestMapping("/findArticleByCategoryId")
    public List<Article> findArticleByCategoryId(String categoryId){
        return articleService.findArticleByCategoryId(categoryId);
    }


    /**
     * 通过文章ID查询文章
     * @param articleId : 文章ID
     * @return
     */
    @RequestMapping("/findArticleByArticleId")
    public Article findArticleByArticleId(String articleId){
        return articleService.findArticleByArticleId(articleId);
    }



    /**
     * 添加一篇文章
     * @param article ：文章
     * @return
     */
    @RequestMapping("/submitArticle")
    public Blog submitArticle(@RequestBody Article article){
        return articleService.submitArticle(article);
    }



    /**
     * 后端方法：
     * isPass=1 :表示审核通过
     * isPass=2 :表示审核未通过
     * ID：文章ID
     *
     * 前端方法：
     * 修改文章内容
     * id：文章ID
     * */
    @RequestMapping("/editArticle")
    public Blog editArticle(@RequestBody Article article, String articleId,String isPass){
        return articleService.editArticle(article,articleId,isPass);
    }

    /**
     * 编辑文章，不需要审核
     * @param article : 文章
     * @param articleId ：文章ID
     * @return
     */
    @RequestMapping("/editArticleNoPass")
    public Blog editArticleNoPass(@RequestBody Article article, String articleId){
        return articleService.editArticleNoPass(article,articleId);
    }


    /**
     * 通过文章ID删除文章
     * @param articleId
     * @return
     */
    @RequestMapping("/deleteArticleByarticleId")
    public Blog deleteArticleByarticleId(String articleId){
        return articleService.deleteArticleByarticleId(articleId);
    }






}
