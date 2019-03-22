package com.shenzc.controller;

import com.shenzc.service.UploadService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * @author shenzc
 * @create 2019-03-13-9:47
 */
@Controller
public class UploadController {

    private UploadService uploadService;

    @RequestMapping("/uploadFile")
    public String uploadFile(com.shenzc.Entity.File uploadFile , BindingResult bindingResult, MultipartFile file )throws IOException {

        Date date = new Date(System.currentTimeMillis());
        String[] s = file.getOriginalFilename().split("\\.");
        s[0] = s[0]+date.getTime();
        String name = s[0]+"."+s[1];
        saveFile(file,name);
        uploadFile.setFile(name);
        uploadService.uploadFile(uploadFile);
        return "index";
    }

    private void saveFile(MultipartFile file, String name) throws IOException {
        String filePath = "D:\\Blog\\MyFile";
        File targetFile = new File(filePath,name);
        file.transferTo(targetFile);
    }


}
