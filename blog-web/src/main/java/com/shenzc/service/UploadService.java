package com.shenzc.service;

import com.shenzc.commonEntity.Blog;
import com.shenzc.mapper.UploadMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author shenzc
 * @create 2019-03-13-9:50
 */
@Service
public class UploadService {

    @Autowired
    private UploadMapper uploadMapper;

    public Blog uploadFile(com.shenzc.Entity.File file){
        file.setFileId(UUID.randomUUID().toString());
        Integer integer = uploadMapper.insert(file);
        if(integer>0){
            return new Blog(true,"添加成功");
        }else {
            return new Blog(false,"添加失败");
        }
    }

}
