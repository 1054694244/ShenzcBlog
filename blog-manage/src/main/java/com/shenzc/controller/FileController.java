package com.shenzc.controller;

import com.shenzc.commonEntity.Blog;
import com.shenzc.controller.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shenzc
 * @create 2019-03-13-9:44
 */
@RestController
public class FileController {

    @Autowired
    private FileService fileService;

    @RequestMapping("/findAllFile")
    Object findAllFile(int rows,int page,String fileId,String fileTitle,String username){
        return fileService.findAllFile(fileId,fileTitle,username,rows,page);
    }

    @RequestMapping("/deleteFile")
    Blog deleteFile(String id){
        return fileService.deleteFile(id);
    }

}
