package com.shenzc.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.shenzc.commonEntity.Blog;
import com.shenzc.mapper.CategoryDao;
import com.shenzc.Entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author shenzc
 * @create 2019-01-09-16:53
 */
@Service
public class CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    public List<Category> findAllCategoryPage(String categoryName,String categoryId){
        //return categoryDao.selectList(new EntityWrapper<Category>());
        return categoryDao.findAllCategory(categoryName,categoryId);
    }


    public List<Category> findAllCategory(){
        return categoryDao.findAllCategory(null,null);
    }

    public Blog addCategory(Category category){
        category.setIsDelete("N");
        category.setCreateTime(new Date());
        category.setParentId("0");
        category.setCategoryId(UUID.randomUUID().toString());
        Integer count = categoryDao.insert(category);
        if(count>0){
            return new Blog(true,"保存成功");
        }else {
            return new Blog(false,"保存失败");
        }
    }

    public Blog updateCategory(Category category,String id){
        Integer count = categoryDao.update(category, new EntityWrapper<Category>().eq("category_id", id));
        if(count>0){
            return new Blog(true,"跟新成功");
        }else {
            return new Blog(false,"跟新失败");
        }
    }

    public Blog deleteCategory(String id){
        Integer count = categoryDao.delete(new EntityWrapper<Category>().eq("category_id",id));
        if(count>0){
            return new Blog(true,"删除成功");
        }else {
            return new Blog(false,"删除失败");
        }
    }

    //按条件查询
    public List<Category> findByCondition(String categoryId,String categoryName){
        List<Category> categoryList = categoryDao.selectList(new EntityWrapper<Category>().
                eq("category_id", categoryId).
                eq("category_name", categoryName));
        return categoryList;
    }


}
