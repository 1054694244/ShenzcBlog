package com.shenzc.controller;

import com.shenzc.Entity.Article;
import com.shenzc.Entity.User;
import com.shenzc.controller.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author shenzc
 * @create 2019-03-09-11:26
 */
@Controller
public class PageController {


    @RequestMapping("/")
    public String getLogin(){
        return "index";
    }

    @RequestMapping("/{page}")
    public String getPage(@PathVariable("page") String page){
        return page;
    }

}
