package com.shenzc.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.shenzc.Entity.Reply;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author shenzc
 * @create 2019-01-18-17:23
 */
@Mapper
@Repository
public interface ReplyMapper extends BaseMapper<Reply> {

    /**
     * 通过文章ID查询所有回复
     * @param articleId ：文章ID
     * @return
     */
    List<Reply> findReplyByArticleId(String articleId);

    /**
     * 通过父类ID查询所有回复
     * @param replyId : 回复ID
     * @return
     */
    List<Reply> findReplyByParentId(String replyId);

}
