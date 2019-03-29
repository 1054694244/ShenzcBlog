package com.shenzc.controller;

import com.shenzc.CommonUtils.FormatDateUtils;
import com.shenzc.Entity.Picture;
import com.shenzc.commonEntity.Blog;
import com.shenzc.controller.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author shenzc
 * @create 2019-03-09-13:11
 */
@RestController
public class PictureController {

    @Autowired
    private PictureService pictureService;

    @RequestMapping("/findAllActivePicture")
    public List<Picture> findAllActivePicture(HttpSession session){
        List<Picture> pictureList = pictureService.findAllActivePicture();
        session.setAttribute("pictureList",pictureList);
        return pictureList;
    }

    @RequestMapping("/findAllPictureByCategory")
    public List<Picture> findAllPictureByCategory(String category){
        return pictureService.findAllPictureByCategory(category);
    }


    @RequestMapping("/editPicture")
    public Blog editPicture(Picture picture, BindingResult bindingResult ,
                            @RequestParam(value = "picture") MultipartFile file) throws IOException {
        //判断是否是图片，不能为其他文件
        String[] split = file.getOriginalFilename().split("\\.");
        if(!"jpg".equals(split[1]) && !"png".equals(split[1])){
            return new Blog(false,"文件不是图片");
        }
        Date date = new Date(System.currentTimeMillis());
        split[0] += date.getTime();
        String name = split[0] +"."+split[1];
        picture.setPicture(name);
        Blog blog = pictureService.editPicture(picture);
        savePicture(file,name);
        return blog;
    }
    private void savePicture(MultipartFile file, String name)throws IOException {
        String imagePath = "D:\\Blog\\image";
        File targetFile = new File(imagePath,name);
        file.transferTo(targetFile);
    }
}
