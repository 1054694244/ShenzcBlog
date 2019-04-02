package com.shenzc.Entity;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author shenzc
 * @create 2019-01-09-16:42
 */
@Data
@TableName("category")
public class Category {

    private String categoryId;

    private String parentId;

    private String categoryName;

    private Date createTime;

    private String createBy;

    private String isDelete;

}
