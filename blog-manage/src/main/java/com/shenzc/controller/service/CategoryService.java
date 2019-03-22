package com.shenzc.controller.service;

import com.shenzc.Entity.Category;
import com.shenzc.commonEntity.Blog;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author shenzc
 * @create 2019-03-12-16:40
 */
@FeignClient(value = "BLOG-SERVICE-CATEGORY")
public interface CategoryService {

    @RequestMapping("/findAllCategoryPage")
    Object findAllCategoryPage(@RequestParam(value = "page") int page,
                            @RequestParam(value = "rows") int rows,
                           @RequestParam(value = "categoryName") String categoryName,
                           @RequestParam(value = "categoryId") String categoryId);

    @RequestMapping("/findAllCategory")
    Object findAllCategory();

    @RequestMapping("/addCategory")
    Blog addCategory(@RequestBody Category category);

    @RequestMapping("/updateCategory")
    Blog updateCategory(@RequestBody Category category ,
                        @RequestParam(value = "id") String id);

    @RequestMapping("/deleteCategory")
    Blog deleteCategory(@RequestParam(value = "id") String id);

}
