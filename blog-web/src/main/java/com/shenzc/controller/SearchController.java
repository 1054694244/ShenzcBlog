package com.shenzc.controller;

import com.shenzc.Entity.Article;
import com.shenzc.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * @author shenzc
 * @create 2019-04-10-8:45
 */
@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    @RequestMapping("/search")
    public String search(String title , Map map){
        List<Article> search = searchService.search(title);
        map.put("articleList",search);
        return "showArticle";
    }

}
