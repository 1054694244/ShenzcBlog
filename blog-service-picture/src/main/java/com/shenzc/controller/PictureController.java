package com.shenzc.controller;

import com.shenzc.Entity.Picture;
import com.shenzc.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author shenzc
 * @create 2019-02-22-15:08
 */
@RestController
public class PictureController {

    @Autowired
    private PictureService pictureService;

    @RequestMapping("/findAllActivePicture")
    public List<Picture> findAllActivePicture(){
        return pictureService.findAllActivePicture();
    }

    @RequestMapping("/findAllPictureByCategory")
    public List<Picture> findAllPictureByCategory(String category){
        return pictureService.findAllPictureByCategory(category);
    }

    @RequestMapping("/findAdPicture")
    public List<Picture> findIndexPicture(){
        List<Picture> pictureList = pictureService.findIndexPicture();
        return pictureList;
    }
}
