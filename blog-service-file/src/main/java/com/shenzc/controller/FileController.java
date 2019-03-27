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
import org.springframework.web.bind.annotation.RestController;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author shenzc
 * @create 2019-01-16-11:01
 */
@RestController
public class FileController {

    @Autowired
    private FileService fileService;


    /**
     * 通过作者ID查询所有文件
     * @param fileId ：文件ID
     * @param id ：作者ID
     * @param fileTitle ：文件标题
     * @param username ：作者姓名
     * @param rows ：每页行数
     * @param page : 第几页
     * @return
     */
    @RequestMapping("/findAllFileById")
    public Object findAllFileById(String fileId, String id, String fileTitle,String username,int rows,int page){
        Page<com.shenzc.Entity.File> filePage = PageHelper.startPage(page, rows);
        List<com.shenzc.Entity.File> fileList = fileService.findAllFileById(fileId, id, fileTitle,username);
        Map<String,Object> map = new HashMap<>();
        map.put("rows",fileList);
        map.put(  "total",filePage.getTotal());
        return map;
    }


    /**
     * 查询所有文件
     * @param fileId ：文件ID
     * @param fileTitle ：文件标题
     * @param username ：作者姓名
     * @param rows ：每页行数
     * @param page ：第几页
     * @return
     */
    @RequestMapping("/findAllFile")
    public Object findAllFile(String fileId, String fileTitle,String username,int rows,int page){
        Page<com.shenzc.Entity.File> filePage = PageHelper.startPage(page, rows);
        List<com.shenzc.Entity.File> fileList = fileService.findAllFile(fileId, fileTitle,username);
        Map<String,Object> map = new HashMap<>();
        map.put("rows",fileList);
        map.put("total",filePage.getTotal());
        return map;
    }


    /**
     * 通过文件ID删除文件
     * @param id ：文件ID
     * @return
     */
    @RequestMapping("/deleteFile")
    public Blog deleteFile(String id){
        return fileService.deleteFile(id);
    }


    /**
     * 通过文件ID查询一个文件
     * @param id ：文件ID
     * @return
     */
    @RequestMapping("/findFileByFileId")
    public File findFileByFileId(String id){
        return fileService.findFileByFileId(id);
    }

}
