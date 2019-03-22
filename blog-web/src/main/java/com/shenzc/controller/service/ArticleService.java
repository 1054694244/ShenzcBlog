package com.shenzc.controller.service;


import com.shenzc.Entity.Article;
import com.shenzc.Entity.Reply;
import com.shenzc.commonEntity.Blog;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author shenzc
 * @create 2019-03-12-14:41
 */
@FeignClient(value = "BLOG-SERVICE-ARTICLE")
public interface ArticleService {

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
    Object findArticleByUserId(@RequestParam(value = "authorId") String authorId,
                               @RequestParam(value = "title") String title,
                               @RequestParam(value = "articleId") String articleId,
                               @RequestParam(value = "page") int page,
                               @RequestParam(value = "rows") int rows);



    /**
     * 通过作者authorId查询所有文章，不分页
     * @param authorId ：作者ID
     * @param title ：文章标题
     * @param articleId ：文章ID
     * @return
     */
    @RequestMapping("/findAllArticleByAuthorIdNoPage")
    List<Article> findAllArticleNoPage(@RequestParam(value = "authorId") String authorId,
                                       @RequestParam(value = "title") String title,
                                       @RequestParam(value = "articleId") String articleId);



    /**
     * 通过分类ID查询所有文章
     * @param categoryId ：分类ID
     * @return
     */
    @RequestMapping("/findArticleByCategoryId")
    List<Article> findArticleByCategoryId(@RequestParam(value = "categoryId") String categoryId);



    /**
     * 通过文章ID查询文章
     * @param articleId : 文章ID
     * @return
     */
    @RequestMapping("/findArticleByArticleId")
    Article findArticleByArticleId(@RequestParam(value = "articleId") String articleId);



    /**
     * 添加一篇文章
     * @param article ：文章
     * @return
     */
    @RequestMapping("/submitArticle")
    Blog submitArticle(@RequestBody Article article);




    /**
     * 前端方法：
     * 修改文章内容
     * id：文章ID
     * @param article
     * @param articleId
     * @return
     */
    @RequestMapping("/editArticle")
    Blog editArticle(@RequestBody Article article,
                     @RequestParam(value = "articleId") String articleId);



    /**
     * 编辑文章，不需要审核
     * @param article : 文章
     * @param articleId ：文章ID
     * @return
     */
    @RequestMapping("/editArticleNoPass")
    Blog editArticleNoPass(@RequestBody Article article,
                     @RequestParam(value = "articleId") String articleId);


    /**
     * 通过文章ID删除文章
     * @param articleId
     * @return
     */
    @RequestMapping("/deleteArticleByarticleId")
    Blog deleteArticleByarticleId(@RequestParam(value = "articleId") String articleId);



    /**
     * 通过文章ID查询所有回复
     * @param articleId ：文章ID
     * @return
     */
    @RequestMapping("/findReplyByArticleId")
    List<Reply> findReplyByArticleId(@RequestParam(value = "articleId") String articleId);



    /**
     * 添加一条评论
     * @param articleId ：评论所属的文章ID
     * @param parentId ：评论的父类ID
     * @param content ：评论内容
     * @param authroId ：评论的作者ID
     * @return
     */
    @RequestMapping("/reply")
    Blog reply(@RequestParam(value = "articleId") String articleId,
               @RequestParam(value = "parentId") String parentId,
               @RequestParam(value = "content") String content,
               @RequestParam(value = "authroId") String authroId);

}
