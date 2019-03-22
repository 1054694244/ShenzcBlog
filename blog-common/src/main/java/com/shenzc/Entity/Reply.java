package com.shenzc.Entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.util.List;

/**
 * @author shenzc
 * @create 2019-01-18-17:20
 */
@Data
@TableName("reply")
public class Reply {

    private String replyId;

    private String articleId;

    @TableField(exist = false)
    private String articleTitle;

    private String parentId;

    private String content;

    private String authorId;

    private String createTime;

    @TableField(exist = false)
    private String username;

    @TableField(exist = false)
    private List<Reply> replyList;

}
