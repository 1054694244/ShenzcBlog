package com.shenzc.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.shenzc.commonEntity.Blog;
import com.shenzc.Entity.File;
import com.shenzc.mapper.FileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author shenzc
 * @create 2019-01-16-10:57
 */
@Service
public class FileService {

    @Autowired
    private FileMapper fileMapper;


    /**
     * 通过作者ID查询文件
     * @param fileId ：文件ID
     * @param id ：作者ID
     * @param title ：文件标题
     * @param username ：作者姓名
     * @return
     */
    public List<File> findAllFileById(String fileId,String id,String title,String username){
        return fileMapper.findAllFileById(fileId,id,title,username);
    }


    /**
     * 查询所有文件
     * @param fileId ：文件ID
     * @param title ：标题
     * @param username ：作者姓名
     * @return
     */
    public List<File> findAllFile(String fileId,String title,String username){
        return fileMapper.findAllFile(fileId,title,username);
    }


    /**
     * 通过文件ID删除文件
     * @param id ：文件ID
     * @return
     */
    public Blog deleteFile(String id){
        Integer integer = fileMapper.delete(new EntityWrapper<File>().eq("file_id", id));
        if(integer>0){
            return new Blog(true,"添加成功");
        }else {
            return new Blog(false,"添加失败");
        }
    }


    /**
     * 通过文件ID查询文件
     * @param id ：文件ID
     * @return
     */
    public File findFileByFileId(String id){
        return fileMapper.findFileByFileId(id);
    }

}
