package com.shenzc.controller;


import com.shenzc.Entity.Article;
import com.shenzc.commonEntity.Blog;
import com.shenzc.controller.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author shenzc
 * @create 2019-03-09-12:48
 */
@RestController
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     *
     * 查询所有文章
     * title : 文章标题
     * username : 作者名字
     * articleId : 文章编号
     * rows : 每页显示的行数
     * page : 第几页
     * */
    @RequestMapping("/findAllArticle")
    public Object findAllArticle(String title,String username,String articleId,int rows,int page){
        return articleService.findAllArticle(title,username,articleId,rows,page);
    }

    /**
     *编辑文章，审核通过还是未通过
     * */
    @RequestMapping("/editArticle")
    public Blog editArticle(Article article, String articleId,String isPass){
        return articleService.editArticle(article,articleId,isPass);
    }


    /**
     * 通过文章ID删除文章
     * @param articleId ：文章ID
     * @return
     */
    @RequestMapping("/deleteArticleByArticleId")
    public Blog deleteArticle(String articleId){
        return articleService.deleteArticle(articleId);
    }


}
