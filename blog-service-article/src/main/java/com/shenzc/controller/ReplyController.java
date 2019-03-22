package com.shenzc.controller;

import com.shenzc.Entity.Reply;
import com.shenzc.Entity.User;
import com.shenzc.commonEntity.Blog;
import com.shenzc.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author shenzc
 * @create 2019-01-18-20:07
 */
@RestController
public class ReplyController {

    @Autowired
    private ReplyService replyService;


    /**
     * 通过文章ID查询所有回复
     * @param articleId ：文章ID
     * @return
     */
    @RequestMapping("/findReplyByArticleId")
    public List<Reply> findReplyByArticleId(String articleId){
        return replyService.findReplyByArticleId(articleId);
    }



    /**
     * 添加一条评论
     * @param articleId ：评论所属的文章ID
     * @param parentId ：评论的父类ID
     * @param content ：评论内容
     * @param authroId ：评论的作者ID
     * @return
     */
    @RequestMapping("/reply")
    public Blog reply(String articleId, String parentId, String content, String authroId){
        return replyService.reply(articleId,parentId,content,authroId);
    }


}
