package com.shenzc.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.shenzc.commonEntity.Blog;
import com.shenzc.mapper.CategoryDao;
import com.shenzc.Entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
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
    @Autowired
    @Qualifier("categoryRedisTemplate")
    RedisTemplate<Object,Category> redisTemplate;

    /**
     * 查询所有分类
     * @param categoryName ：分类名称
     * @param categoryId ：分类ID
     * @return
     */
    public List<Category> findAllCategoryPage(String categoryName,String categoryId){
        return categoryDao.findAllCategory(categoryName,categoryId);
    }


    /**
     * 查询所有分类
     * 这是主页分类，访问量最大，从数据库中一直查询压力较大，所以存入缓存之中，直接从缓存中取较快
     * @return
     */
    public List<Category> findAllCategory(){
        try {
            List<Category> jsonCategoryList = redisTemplate.opsForList().range("categoryList", 0, -1);
            if(jsonCategoryList == null || jsonCategoryList.size()==0){
                List<Category> categoryList = categoryDao.findAllCategory(null, null);
                redisTemplate.opsForList().rightPushAll("categoryList",categoryList);
                return categoryList;
            }else {
                System.out.println("分类调用了缓存");
                return jsonCategoryList;
            }
        }catch (Exception e){
            return null;
        }
    }


    /**
     * 添加一个分类
     * 添加一个分类的同时，修改缓存中分类的信息
     * @param category
     * @return
     */
    public Blog addCategory(Category category){
        category.setIsDelete("N");
        category.setCreateTime(new Date());
        category.setParentId("0");
        category.setCategoryId(UUID.randomUUID().toString());
        try{
            List<Category> jsonCategoryList = redisTemplate.opsForList().range("categoryList", 0, -1);
            jsonCategoryList.add(category);
            redisTemplate.delete("categoryList");
            redisTemplate.opsForList().rightPushAll("categoryList",jsonCategoryList);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        Integer count = categoryDao.insert(category);
        if(count>0){
            return new Blog(true,"保存成功");
        }else {
            return new Blog(false,"保存失败");
        }
    }


    /**
     * 跟新一个分类
     * @param category ：分类
     * @param id ：分类ID
     * @return
     */
    public Blog updateCategory(Category category,String id){
        Integer count = categoryDao.update(category, new EntityWrapper<Category>().eq("category_id", id));
        if(count>0){
            return new Blog(true,"跟新成功");
        }else {
            return new Blog(false,"跟新失败");
        }
    }


    /**
     * 删除一个分类
     * @param id ：分类ID
     * @return
     */
    public Blog deleteCategory(String id){
        Integer count = categoryDao.delete(new EntityWrapper<Category>().eq("category_id",id));
        if(count>0){
            return new Blog(true,"删除成功");
        }else {
            return new Blog(false,"删除失败");
        }
    }



}
