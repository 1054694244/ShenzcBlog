package com.shenzc.Entity;


import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("user")
public class User implements Serializable {


    private String userId;

    private String username;

    private String password;

    private String head;

    private String myArticle;

    private int myArticleNum;

    private int seePerson;

    private String follow;

    private int followNum;

    private String article;

    private int articleNum;

    private String createTime;

    private String lastLoginTime;

    private String birthday;

    private String supperTime;

    private String isSupper;

    private String active;

    private Integer money;

    private int isFollow;

    private String utf1;

    private String utf2;

    private String utf3;

}
