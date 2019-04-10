package com.shenzc.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.shenzc.Entity.Picture;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author shenzc
 * @create 2019-02-22-14:56
 */
@Mapper
@Repository
public interface PictureMapper extends BaseMapper<Picture> {

    //查询所有启用的图片
    List<Picture> findAllActivePicture();

    List<Picture> findAllPictureByCategory(String category);

    List<Picture> findIndexPicture();

    List<Picture> findArticlePicture();
}
