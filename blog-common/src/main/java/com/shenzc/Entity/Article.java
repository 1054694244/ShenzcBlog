package com.shenzc.Entity;


import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

@Data
@TableName("article")
public class Article {

      private String articleId;

      private String title;

      private String content;

      private String authorId;

      private String createTime;

      private String updateTime;

      private String categoryId;

      private Integer goods;

      private int singleSeePerson;

      private String isPass;

      private String utf1;

      private String utf2;

      private String utf3;

      @TableField(exist = false)
      private String username;

      @TableField(exist = false)
      private String head;

      @TableField(exist = false)
      private String categoryName;

}
