package com.shenzc.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.shenzc.Entity.File;
import com.shenzc.commonEntity.Blog;
import com.shenzc.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author shenzc
 * @create 2019-01-16-11:01
 */
@Controller
public class FileController {

    @Autowired
    private FileService fileService;


    @RequestMapping("/findAllFileById")
    @ResponseBody
    public Object findAllFileById(String fileId, String id, String fileTitle,String username,int rows,int page){
        Page<com.shenzc.Entity.File> filePage = PageHelper.startPage(page, rows);
        List<com.shenzc.Entity.File> fileList = fileService.findAllFileById(fileId, id, fileTitle,username);
        Map<String,Object> map = new HashMap<>();
        map.put("rows",fileList);
        map.put(  "total",filePage.getTotal());
        return map;
    }

    @RequestMapping("/findAllFile")
    @ResponseBody
    public Object findAllFile(String fileId, String fileTitle,String username,int rows,int page){
        Page<com.shenzc.Entity.File> filePage = PageHelper.startPage(page, rows);
        List<com.shenzc.Entity.File> fileList = fileService.findAllFile(fileId, fileTitle,username);
        Map<String,Object> map = new HashMap<>();
        map.put("rows",fileList);
        map.put("total",filePage.getTotal());
        return map;
    }

    @RequestMapping("/deleteFile")
    @ResponseBody
    public Blog deleteFile(String id){
        return fileService.deleteFile(id);
    }

    @RequestMapping("/findFileByFileId")
    @ResponseBody
    public File findFileByFileId(String id){
        return fileService.findFileByFileId(id);
    }

}
