package com.shenzc.service;

import com.shenzc.Entity.Picture;
import com.shenzc.mapper.PictureMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
