package com.shenzc.service;

import com.shenzc.CommonUtils.FormatDateUtils;
import com.shenzc.Entity.Article;
import com.shenzc.Entity.Reply;
import com.shenzc.commonEntity.Blog;
import com.shenzc.mapper.ReplyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
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
    @Autowired
    @Qualifier("replyRedisTemplate")
    RedisTemplate<Object,Reply> redisTemplate;

    /**
     * 通过文章ID查询所有评论（包括一级评论下的所有二级评论）
     * @param articleId ：文章ID
     * @return
     */
    public List<Reply> findReplyByArticleId(String articleId){
        try{
            List<Reply> jsonReplyList = redisTemplate.opsForList().range("replyList:"+articleId, 0, -1);
            if(jsonReplyList == null || jsonReplyList.size()==0){
                List<Reply> replyList = replyMapper.findReplyByArticleId(articleId);
                for (Reply reply:replyList) {
                    List<Reply> replyList1 = replyMapper.findReplyByParentId(reply.getReplyId());
                    reply.setReplyList(replyList1);
                }
                redisTemplate.opsForList().rightPushAll("replyList:"+articleId,replyList);
                return replyList;
            }else {
                System.out.println("reply调用了缓存");
                return jsonReplyList;
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
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
        try {
            List<Reply> replyList = replyMapper.findReplyByArticleId(articleId);
            for (Reply reply1:replyList) {
                List<Reply> replyList1 = replyMapper.findReplyByParentId(reply1.getReplyId());
                reply1.setReplyList(replyList1);
            }
            redisTemplate.delete("replyList:"+articleId);
            redisTemplate.opsForList().rightPushAll("replyList:"+articleId,replyList);
            System.out.println("reply跟新了缓存");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        if(integer>0){
            return new Blog(true,"评论成功");
        }else {
            return new Blog(false,"评论失败");
        }
    }

}
