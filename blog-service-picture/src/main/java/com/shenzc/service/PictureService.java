package com.shenzc.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.shenzc.CommonUtils.BlogUtils;
import com.shenzc.CommonUtils.FormatDateUtils;
import com.shenzc.Entity.Picture;
import com.shenzc.commonEntity.Blog;
import com.shenzc.mapper.PictureMapper;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author shenzc
 * @create 2019-02-22-15:06
 */
@Service
public class PictureService {

    @Autowired
    private PictureMapper pictureMapper;

    public List<Picture> findAllActivePicture(){
        return pictureMapper.findAllActivePicture();
    }

    public List<Picture> findAllPictureByCategory(String category){
        return pictureMapper.findAllPictureByCategory(category);
    }

    public List<Picture> findIndexPicture(){
        return pictureMapper.findIndexPicture();
    }

    public List<Picture> findArticlePicture(){
        return pictureMapper.findArticlePicture();
    }


    /**
     * 通过分类修改图片
     * 1.修改老图片为未启用
     * 2.添加新图片到数据库
     * @param picture：图片
     * @return
     */
    public Blog editPicture(Picture picture){
        Picture insertPicture = picture;
        picture.setIsActive("0");
        pictureMapper.update(picture,new EntityWrapper<Picture>().eq("picture_id",picture.getPictureId()));
        insertPicture.setPictureId(UUID.randomUUID().toString());
        insertPicture.setCreateTime(FormatDateUtils.formatDate(new Date()));
        insertPicture.setIsActive("1");
        Integer integer = pictureMapper.insert(insertPicture);
        return BlogUtils.blog(integer,"修改成功","修改失败");
    }

}
