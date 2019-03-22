package com.shenzc.service;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.shenzc.CommonUtils.FormatDateUtils;
import com.shenzc.CommonUtils.JsonUtils;
import com.shenzc.Entity.Article;
import com.shenzc.Entity.User;
import com.shenzc.commonEntity.Blog;
import com.shenzc.commonEntity.MyArticleJson;
import com.shenzc.mapper.UserDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;

/**
 * @author shenzc
 * @create 2019-01-09-9:58
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    //根据条件查询所有用户
    public List<User> findAllUser(String userId, String username) {
        return userDao.findAllUser(userId,username);
    }

    //添加用户
    public Blog addUser(User user) {
        user.setActive("N");
        user.setCreateTime(new Date().toString());
        user.setLastLoginTime(new Date().toString());
        user.setUserId(UUID.randomUUID().toString());
        user.setMoney(0);
        user.setIsFollow(0);
        Integer count = userDao.insert(user);
        if (count > 0) {
            return new Blog(true, "保存成功");
        } else {
            return new Blog(false, "保存失败");
        }
    }

    //编辑用户
    public Blog updateUser(User user, String id) {
        Integer count = userDao.update(user, new EntityWrapper<User>().eq("user_id", id));
        if (count > 0) {
            return new Blog(true, "跟新成功");
        } else {
            return new Blog(false, "跟新失败");
        }
    }

    //删除用户
    public Blog deleteUser(String id) {
        Integer count = userDao.delete(new EntityWrapper<User>().eq("user_id", id));
        if (count > 0) {
            return new Blog(true, "删除成功");
        } else {
            return new Blog(false, "删除失败");
        }
    }

    //按条件查询
    public List<User> findByCondition(String userId, String username) {
        List<User> userList = userDao.selectList(new EntityWrapper<User>().
                eq("user_id", userId).
                eq("username", username));
        return userList;
    }

    //登陆
    public Blog login(User user) {
        List<User> userList = userDao.selectList(new EntityWrapper<User>().
                eq("username", user.getUsername()).
                eq("password", user.getPassword()));
        if (userList != null && userList.size() != 0) {
            return new Blog(true, "登陆成功", userList);
        } else {
            return new Blog(false, "登陆失败");
        }
    }

    //通过用户ID查询用户
    public User findUserById(String id){
        return userDao.selectUserById(id);
    }

    //修改用户信息
    public void updateBasicUser(User user){
        userDao.updateBasicUser(user.getPassword(),user.getHead(),user.getBirthday(),user.getUserId());
    }

    //查询关注的人
    public List<User> findFollowUser(String userId){
        String userJosn = userDao.selectUserById(userId).getFollow();
        List<MyArticleJson> userList = JsonUtils.jsonToList(userJosn, MyArticleJson.class);
        if(userList != null){
            List<User> userArrayList = new ArrayList<>();
            for (MyArticleJson user:userList) {
                User user1 = userDao.findUserByUsername(user.getName());
                userArrayList.add(user1);
            }
            return userArrayList;
        }else {
            return null;
        }
    }

    //查询关注的文章
    public List<Article> findFollowArtcle(String userId){
        String articleJson = userDao.selectUserById(userId).getArticle();
        List<MyArticleJson> userList = JsonUtils.jsonToList(articleJson, MyArticleJson.class);
        if (userList != null){
            List<Article> articleArrayList = new ArrayList<>();
            for (MyArticleJson user:userList) {
                Article article = userDao.findArticleByTitle(user.getName());
                articleArrayList.add(article);
            }
            return articleArrayList;
        }else {
            return null;
        }
    }

    //取消关注人
    public void cancelFollow(String userId,String loginId){
        User user = userDao.selectUserById(loginId);
        User user1 = userDao.selectUserById(userId);
        user.setFollowNum(user.getFollowNum()-1);
        String followJson = user.getFollow();
        List<MyArticleJson> userList = JsonUtils.jsonToList(followJson, MyArticleJson.class);
        MyArticleJson myArticleJson = new MyArticleJson();
        myArticleJson.setName(user1.getUsername());
        userList.remove(myArticleJson);
        followJson = JsonUtils.objectToJson(userList);
        user.setFollow(followJson);
        user1.setIsFollow(user1.getIsFollow()-1);
        userDao.update(user,new EntityWrapper<User>().eq("user_id",loginId));
        userDao.update(user1,new EntityWrapper<User>().eq("user_id",userId));
    }

    //取消关注文章
    public void cancelArticle(Article article,String loginId){
        User user = userDao.selectUserById(loginId);
        user.setArticleNum(user.getArticleNum()-1);
        String articleJson = user.getArticle();
        List<MyArticleJson> userList = JsonUtils.jsonToList(articleJson, MyArticleJson.class);
        MyArticleJson myArticleJson = new MyArticleJson();
        myArticleJson.setName(article.getTitle());
        userList.remove(myArticleJson);
        articleJson = JsonUtils.objectToJson(userList);
        user.setArticle(articleJson);
        userDao.update(user,new EntityWrapper<User>().eq("user_id",loginId));
    }
    
    //关注人
    public void updateUser(String loginId,String userId){
        User user = userDao.selectUserById(userId);
        User loginUser = userDao.selectUserById(loginId);
        String followJson = loginUser.getFollow();
        loginUser.setFollowNum(loginUser.getFollowNum()+1);
        if(followJson == "" || followJson == null){
            followJson = "[{\"name\":\""+user.getUsername()+"\"}]";
        }else {
            List<MyArticleJson> myJsonList = JsonUtils.jsonToList(followJson, MyArticleJson.class);
            MyArticleJson myJson = new MyArticleJson();
            myJson.setName(user.getUsername());
            myJsonList.add(myJson);
            followJson = JsonUtils.objectToJson(myJsonList);
        }
        loginUser.setFollow(followJson);
        user.setIsFollow(user.getIsFollow()+1);
        userDao.update(loginUser,new EntityWrapper<User>().eq("user_id",loginId));
        userDao.update(user,new EntityWrapper<User>().eq("user_id",userId));
    }

    //关注文章
    public void updateUserArticle(String loginId,Article article){
        User loginUser = userDao.selectUserById(loginId);
        String articleJson = loginUser.getArticle();
        loginUser.setArticleNum(loginUser.getArticleNum()+1);
        if(articleJson == "" || articleJson == null){
            articleJson = "[{\"name\":\""+article.getTitle()+"\"}]";
        }else {
            List<MyArticleJson> myJsonList = JsonUtils.jsonToList(articleJson, MyArticleJson.class);
            MyArticleJson myJson = new MyArticleJson();
            myJson.setName(article.getTitle());
            myJsonList.add(myJson);
            articleJson = JsonUtils.objectToJson(myJsonList);
        }
        loginUser.setArticle(articleJson);
        userDao.update(loginUser,new EntityWrapper<User>().eq("user_id",loginId));
    }

/**
 * Calendar curr = Calendar.getInstance()
 *
 * curr.set(Calendar.YEAR,curr.get(Calendar.YEAR)+1);
 *
 * Date date=curr.getTime();
 */
    //充值超级会员
    public Blog vip(String id,String vip) throws ParseException {
        User user = userDao.selectUserById(id);
        String s = null;
        if("N".equals(user.getIsSupper())){
            user.setIsSupper("Y");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            if("12".equals(vip)){
                calendar.add(Calendar.YEAR,+1);
            }else if("3".equals(vip)){
                calendar.add(Calendar.MONTH,+3);
            }else if("1".equals(vip)){
                calendar.add(Calendar.MONTH,+1);
            }
            Date date = calendar.getTime();
            s = FormatDateUtils.formatDate(date);
        }else {
            s = user.getSupperTime();
            Date date = FormatDateUtils.dateFormat(s);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            if("12".equals(vip)){
                calendar.add(Calendar.YEAR,+1);
            }else if("3".equals(vip)){
                calendar.add(Calendar.MONTH,+3);
            }else if("1".equals(vip)){
                calendar.add(Calendar.MONTH,+1);
            }
            Date date1 = calendar.getTime();
            s = FormatDateUtils.formatDate(date1);
        }
        user.setSupperTime(s);
        Integer integer = userDao.update(user, new EntityWrapper<User>().eq("user_id", id));
        if(integer>0){
            return new Blog(true,"充值成功");
        }else {
            return new Blog(false,"充值失败");
        }
    }

}
