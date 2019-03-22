package com.shenzc.controller.service;

import com.shenzc.Entity.User;
import com.shenzc.commonEntity.Blog;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author shenzc
 * @create 2019-03-12-11:09
 */
@FeignClient(value = "BLOG-SERVICE-USER")
public interface UserService {

    @RequestMapping("/findAllUser")
    Object findAllUser(@RequestParam(value = "rows") int rows,
                       @RequestParam(value = "page") int page,
                       @RequestParam(value = "userId") String userId,
                       @RequestParam(value = "username") String username);

    @RequestMapping("/addUser")
    Blog addUser(@RequestBody User user);

    @RequestMapping("/updateUser")
    Blog updateUser(@RequestBody User user,
                    @RequestParam(value = "id") String id);

    @RequestMapping("/deleteUser")
    Blog deleteUser(@RequestParam(value = "id") String id);

    @RequestMapping("/searchUser")
    List<User> searchUser(@RequestParam(value = "userId") String userId,
                          @RequestParam(value = "username") String username);

}
