package com.shenzc.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.shenzc.Entity.Article;
import com.shenzc.Entity.Reply;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author shenzc
 * @create 2019-01-15-10:24
 */
@Mapper
@Repository
public interface ArticleMapper extends BaseMapper<Article> {

    /**
     * 通过作者ID查询所有文章
     * @param authorId：作者ID
     * @param title ：文章标题
     * @param articleId ：文章ID
     * @return
     */
    List<Article> findArticleById(@Param("authorId") String authorId,
                                  @Param("title") String title,
                                  @Param("articleId") String articleId);

    /**
     * 查询所有文章
     * @param username ：文章作者
     * @param title ：文章标题
     * @param articleId ：文章ID
     * @return
     */
    List<Article> findAllArticle(@Param("username") String username,
                                 @Param("title") String title,
                                 @Param("articleId") String articleId);

    /**
     * 通过分类查ID询所有文章
     * @param categoryId ：分类ID
     * @return
     */
    List<Article> findArticleByCategoryId(String categoryId);

    /**
     * 通过文章ID查询单个文章
     * @param articleId ：文章ID
     * @return
     */
    Article findArticleByArticleId(String articleId);


}
