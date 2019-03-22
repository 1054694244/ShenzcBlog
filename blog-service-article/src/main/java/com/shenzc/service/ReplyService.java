package com.shenzc.service;

import com.shenzc.CommonUtils.FormatDateUtils;
import com.shenzc.Entity.Reply;
import com.shenzc.commonEntity.Blog;
import com.shenzc.mapper.ReplyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author shenzc
 * @create 2019-01-18-20:05
 */
@Service
public class ReplyService {

    @Autowired
    private ReplyMapper replyMapper;


    /**
     * 通过文章ID查询所有评论（包括一级评论下的所有二级评论）
     * @param articleId ：文章ID
     * @return
     */
    public List<Reply> findReplyByArticleId(String articleId){
        List<Reply> replyList = replyMapper.findReplyByArticleId(articleId);
        for (Reply reply:replyList) {
            List<Reply> replyList1 = replyMapper.findReplyByParentId(reply.getReplyId());
            reply.setReplyList(replyList1);
        }
        return replyList;
    }


    /**
     * 添加一条评论
     * @param articleId ：评论所属的文章ID
     * @param parentId ：评论的父类ID
     * @param content ：评论的内容
     * @param authroId ：评论的人ID
     * @return
     */
    public Blog reply(String articleId, String parentId, String content, String authroId){
        Reply reply = new Reply();
        reply.setReplyId(UUID.randomUUID().toString());
        reply.setArticleId(articleId);
        reply.setParentId(parentId);
        reply.setContent(content);
        reply.setAuthorId(authroId);
        String date = FormatDateUtils.formatDateTime(new Date());
        reply.setCreateTime(date);
        Integer integer = replyMapper.insert(reply);
        if(integer>0){
            return new Blog(true,"评论成功");
        }else {
            return new Blog(false,"评论失败");
        }
    }

}
