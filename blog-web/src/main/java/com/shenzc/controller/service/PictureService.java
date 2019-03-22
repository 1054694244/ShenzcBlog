package com.shenzc.controller.service;

import com.shenzc.Entity.Picture;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author shenzc
 * @create 2019-03-13-11:05
 */
@FeignClient(value = "BLOG-SERVICE-PICTURE")
public interface PictureService {

    @RequestMapping("/findAllActivePicture")
    List<Picture> findAllActivePicture();

    @RequestMapping("/findAllPictureByCategory")
    List<Picture> findAllPictureByCategory(@RequestParam(value = "category") String category);

    @RequestMapping("/findAdPicture")
    List<Picture> findIndexPicture();

}
