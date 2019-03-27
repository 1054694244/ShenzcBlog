package com.shenzc.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.shenzc.Entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author shenzc
 * @create 2019-01-09-16:41
 */
@Mapper
@Repository
public interface CategoryDao extends BaseMapper<Category> {

    /**
     * 查询所有分类
     * @param categoryName ：分类名称（可以为null）
     * @param categoryId ：分类ID（可以为null）
     * @return
     */
    List<Category> findAllCategory(@Param("categoryName") String categoryName,
                                   @Param("categoryId") String categoryId);



}
