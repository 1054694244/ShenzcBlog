package com.shenzc.controller;

import com.shenzc.Entity.Picture;
import com.shenzc.controller.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author shenzc
 * @create 2019-03-09-13:11
 */
@RestController
public class PictureController {

    @Autowired
    private PictureService pictureService;

    @RequestMapping("/findAllActivePicture")
    List<Picture> findAllActivePicture(){
        return pictureService.findAllActivePicture();
    }

    @RequestMapping("/findAllPictureByCategory")
    List<Picture> findAllPictureByCategory(String category){
        return pictureService.findAllPictureByCategory(category);
    }

}
