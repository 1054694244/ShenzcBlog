package com.shenzc.controller;

import com.shenzc.Entity.User;
import com.shenzc.controller.service.UserService;
import com.shenzc.service.UploadService;
import com.shenzc.controller.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * @author shenzc
 * @create 2019-03-13-9:47
 */
@Controller
public class UploadController {

    @Autowired
    private FileService fileService;
    @Autowired
    private UploadService uploadService;
    @Autowired
    private UserService userService;


    @RequestMapping("/uploadFile")
    public String uploadFile(com.shenzc.Entity.File uploadFile , BindingResult bindingResult, MultipartFile file )throws IOException {

        Date date = new Date(System.currentTimeMillis());
        String[] s = file.getOriginalFilename().split("\\.");
        s[0] = s[0]+date.getTime();
        String name = s[0]+"."+s[1];
        saveFile(file,name);
        uploadFile.setFile(name);
        uploadService.uploadFile(uploadFile);
        return "file";
    }

    private void saveFile(MultipartFile file, String name) throws IOException {
        String filePath = "D:\\Blog\\MyFile";
        File targetFile = new File(filePath,name);
        file.transferTo(targetFile);
    }


    @RequestMapping("/download/{fileId}")
    public String download(@PathVariable(value = "fileId") String fileId, HttpSession session, Map map){
        com.shenzc.Entity.File file = fileService.findFileByFileId(fileId);
        map.put("file",file);
        User user = (User) session.getAttribute("user");
        if("N".equals(user.getIsSupper())){
            if(!user.getUserId().equals(file.getAuthorId())){
                //在非会员，有零钱的情况下 。下载文件的同时，扣除下载文件所需要到零钱.
                user.setMoney(user.getMoney()-file.getMoney());
                userService.updateUser(user,user.getUserId());
                //发布本文件的人可以得到所需下载文件一半的零钱
                User fileUser = userService.findUserById(file.getAuthorId());
                fileUser.setMoney(fileUser.getMoney()+file.getMoney()/2);
                userService.updateUser(fileUser,fileUser.getUserId());
            }
        }
        return "download";
    }


}
