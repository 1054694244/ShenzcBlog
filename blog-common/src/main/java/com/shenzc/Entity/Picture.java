package com.shenzc.Entity;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author shenzc
 * @create 2019-02-22-14:52
 */
@Data
@TableName("picture")
public class Picture implements Serializable {

    private String pictureId;

    private String title;

    private String picture;

    private String category;

    private String url;

    private String createTime;

    private String isActive;
}
