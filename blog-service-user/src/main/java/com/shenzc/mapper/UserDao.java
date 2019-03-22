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

    //查询所有用户
    List<User> findAllUser(@Param("userId") String userId,
                           @Param("username") String username);

    User selectUserById(String userId);

    void updateBasicUser(@Param("password") String password,
                         @Param("head") String head,
                         @Param("birthday") String birthday,
                         @Param("userId") String userId);

    User findUserByUsername(@Param("username") String username);

    Article findArticleByTitle(@Param("title") String title);

}
