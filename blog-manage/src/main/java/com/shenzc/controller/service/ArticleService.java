package com.shenzc.controller.service;


import com.shenzc.Entity.Article;
import com.shenzc.commonEntity.Blog;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author shenzc
 * @create 2019-03-12-14:41
 */
@FeignClient(value = "BLOG-SERVICE-ARTICLE")
public interface ArticleService {


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
    Object findAllArticle(@RequestParam(value = "title") String title,
                          @RequestParam(value = "username") String username,
                           @RequestParam(value = "articleId") String articleId,
                           @RequestParam(value = "page") int page,
                           @RequestParam(value = "rows") int rows);


    /**
     * 编辑文章，修改文章状态
     * @param article ：文章
     * @param articleId ：文章ID
     * @param isPass ：isPass=1：表示审核通过 isPass=2：表示审核不通过
     * @return
     */
    @RequestMapping("/editArticle")
    Blog editArticle(@RequestBody Article article,
                     @RequestParam(value = "articleId") String articleId,
                     @RequestParam(value = "isPass") String isPass);


    /**
     * 删除文章
     * @param articleId ：文章ID
     * @return
     */
    @RequestMapping("/deleteArticle")
    Blog deleteArticle(@RequestParam(value = "articleId") String articleId);




}
