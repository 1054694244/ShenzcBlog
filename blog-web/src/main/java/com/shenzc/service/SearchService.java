package com.shenzc.service;

import com.netflix.discovery.converters.Auto;
import com.shenzc.Entity.Article;
import com.shenzc.mapper.SearchMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shenzc
 * @create 2019-04-10-8:44
 */
@Service
public class SearchService {

    @Autowired
    private SearchMapper searchMapper;

    public List<Article> search(String title){
        return searchMapper.search(title);
    }

}
