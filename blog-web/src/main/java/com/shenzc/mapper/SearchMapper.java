package com.shenzc.mapper;

import com.shenzc.Entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Null;
import java.util.List;

/**
 * @author shenzc
 * @create 2019-04-10-8:37
 */
@Mapper
@Repository
public interface SearchMapper {

    List<Article> search(String title);

}
