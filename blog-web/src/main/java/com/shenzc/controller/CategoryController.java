package com.shenzc.controller;

import com.shenzc.Entity.Category;
import com.shenzc.Entity.User;
import com.shenzc.commonEntity.Blog;
import com.shenzc.controller.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author shenzc
 * @create 2019-03-12-16:54
 */
@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/findAllCategoryPage")
    public Object findAllCategoryPage(int page,int rows,String categoryName,String categoryId){
        return categoryService.findAllCategoryPage(rows,page,categoryName,categoryId);
    }

    @RequestMapping("/findAllCategory")
    public List<Category> findAllCategory(){
        return categoryService.findAllCategory();
    }

    @RequestMapping("/addCategory")
    public Blog addCategory(Category category, HttpSession session){
        String username = ((User) session.getAttribute("user")).getUsername();
        category.setCreateBy(username);
        return categoryService.addCategory(category);
    }

    @RequestMapping("/updateCategory")
    public Blog updateCategory(Category category ,String id){
        return categoryService.updateCategory(category,id);
    }

    @RequestMapping("/deleteCategory")
    public Blog deleteCategory(String id){
        return categoryService.deleteCategory(id);
    }

}
