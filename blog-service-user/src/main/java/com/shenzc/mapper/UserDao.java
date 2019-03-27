package com.shenzc.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.shenzc.Entity.Article;
import com.shenzc.Entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author shenzc
 * @create 2019-02-22-14:56
 */
@Mapper
@Repository
public interface UserDao extends BaseMapper<User> {


    /**
     * 查询所有用户
     * @param userId : 用户ID（可以为null）
     * @param username ：用户姓名（可以为null）
     * @return
     */
    List<User> findAllUser(@Param("userId") String userId,
                           @Param("username") String username);


    /**
     * 通过ID查询用户
     * @param userId : 用户ID
     * @return
     */
    User selectUserById(String userId);


    /**
     * 跟新用户基本信息
     * @param password ：密码
     * @param head ：头像
     * @param birthday ：生日
     * @param userId ：用户ID
     */
    void updateBasicUser(@Param("password") String password,
                         @Param("head") String head,
                         @Param("birthday") String birthday,
                         @Param("userId") String userId);


    /**
     * 通过用户名查询用户
     * @param username ：用户名
     * @return
     */
    User findUserByUsername(@Param("username") String username);



    /**
     * 通过文章标题查询文章
     * @param title ：文章标题
     * @return
     */
    Article findArticleByTitle(@Param("title") String title);

}
