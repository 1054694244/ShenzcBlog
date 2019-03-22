package com.shenzc.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.shenzc.Entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


/**
 * @author shenzc
 * @create 2019-01-04-14:12
 */
@Mapper
@Repository
public interface LoginMaper extends BaseMapper<User> {

    User findByUser(User user);

}
