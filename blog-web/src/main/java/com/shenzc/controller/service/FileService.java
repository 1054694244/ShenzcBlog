package com.shenzc.controller.service;

import com.shenzc.Entity.File;
import com.shenzc.commonEntity.Blog;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author shenzc
 * @create 2019-03-13-9:35
 */
@FeignClient(value = "BLOG-SERVICE-FILE")
public interface FileService {

    @RequestMapping("/findAllFile")
    Object findAllFile(@RequestParam(value = "fileId") String fileId,
                       @RequestParam(value = "fileTitle") String fileTitle,
                       @RequestParam(value = "username") String username,
                       @RequestParam(value = "rows") int rows,
                       @RequestParam(value = "page") int page);

    @RequestMapping("/deleteFile")
    Blog deleteFile(@RequestParam(value = "id") String id);

    @RequestMapping("/findAllFileById")
    Object findAllFileById(@RequestParam(value = "id") String id,
                            @RequestParam(value = "fileId") String fileId,
                           @RequestParam(value = "fileTitle") String fileTitle,
                           @RequestParam(value = "username") String username,
                           @RequestParam(value = "rows") int rows,
                           @RequestParam(value = "page") int page);


    @RequestMapping("/findFileByFileId")
    File findFileByFileId(@RequestParam(value = "id") String id);
}
