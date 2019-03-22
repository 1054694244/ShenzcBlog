package com.shenzc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author shenzc
 * @create 2019-03-09-11:26
 */
@Controller
public class PageController {

    @RequestMapping("/")
    public String getLogin(){
        return "login";
    }

    @RequestMapping("/manager/{page}")
    public String getUri(@PathVariable("page") String page){
        return page;
    }

}
