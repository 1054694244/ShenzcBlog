package com.shenzc.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.shenzc.Entity.User;
import com.shenzc.commonEntity.Blog;
import com.shenzc.Entity.Category;
import com.shenzc.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author shenzc
 * @create 2019-01-09-16:58
 */
@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/findAllCategoryPage")
    public Object findAllCategoryPage(int rows,int page,String categoryName,String categoryId){
        Page<Category> categoryPage = PageHelper.startPage(page,rows);
        List<Category> categoryList = categoryService.findAllCategoryPage(categoryName,categoryId);
        Map<String,Object> map = new HashMap<>();
        map.put("total",categoryPage.getTotal());
        map.put("rows",categoryList);
        return map;
    }

    @RequestMapping("/findAllCategory")
    public List<Category> findAllCategory(){
        List<Category> categoryList = categoryService.findAllCategory();
        return categoryList;
    }

    @RequestMapping("/addCategory")
    public Blog addCategory(@RequestBody Category category){
        return categoryService.addCategory(category);
    }
    @RequestMapping("/updateCategory")
    public Blog updateCategory(@RequestBody Category category ,String id){
        return categoryService.updateCategory(category,id);
    }
    @RequestMapping("/deleteCategory")
    public Blog deleteCategory(String id){
        return categoryService.deleteCategory(id);
    }

    @RequestMapping("/searchCategory")
    public List<Category> searchCategory(String categoryId,String categoryname){
        return categoryService.findByCondition(categoryId,categoryname);
    }


}
