package com.shenzc.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.shenzc.Entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author shenzc
 * @create 2019-01-07-11:09
 */
@Mapper
@Repository
public interface RegisterMapper extends BaseMapper<User> {

    /**
     * 通过名字查询用户
     * */
    User findUserByName(@Param("username") String username);

}
