package com.shenzc.controller;

import com.shenzc.Entity.User;
import com.shenzc.commonEntity.Blog;
import com.shenzc.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * @author shenzc
 * @create 2019-01-07-11:08
 */
@Controller
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @RequestMapping("/register")
    public String getRegister(){
        return "regist";
    }

    @RequestMapping("/regist")
    public String register(User user, BindingResult bindingResult ,
                           @RequestParam("head") MultipartFile file, HttpServletRequest request)
                                  throws IOException {
        Date date = new Date(System.currentTimeMillis());
        String[] s = file.getOriginalFilename().split("\\.");
        s[0] += date.getTime();
        String name = s[0]+"."+s[1];
        savePicture(file,request,name);
        user.setHead(name);
        Blog blog = registerService.register(user);
        if(blog.isSuccess()){
            return "login";
        }else
            return "regist";
    }

    private void savePicture(MultipartFile file, HttpServletRequest request, String name)throws IOException{
        String imagePath = "D:\\Blog\\image";
        File targetFile = new File(imagePath,name);
        file.transferTo(targetFile);
    }
}