package com.shenzc.Entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author shenzc
 * @create 2019-01-16-10:52
 */
@Data
@TableName("file")
public class File implements Serializable {

    private String fileId;

    private String fileTitle;

    private String file;

    private String authorId;

    private String category;

    private Integer money;

    @TableField(exist = false)
    private String username;

}
