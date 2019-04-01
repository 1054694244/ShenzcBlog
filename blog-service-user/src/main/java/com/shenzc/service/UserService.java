package com.shenzc.service;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.shenzc.CommonUtils.BlogUtils;
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


    /**
     * 查询所有用户
     * @param userId ：用户ID
     * @param username ：用户名
     * @return
     */
    public List<User> findAllUser(String userId, String username) {
        return userDao.findAllUser(userId,username);
    }


    /**
     * 添加用户
     * @param user ：用户
     * @return
     */
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


    /**
     * 通过用户ID修改用户
     * @param user ：用户
     * @param id ：用户ID
     * @return
     */
    public Blog updateUser(User user, String id) {
        Integer count = userDao.update(user, new EntityWrapper<User>().eq("user_id", id));
        return BlogUtils.blog(count,"更新成功","跟新失败");
    }




    /**
     * 通过用户ID删除用户
     * @param id ：用户ID
     * @return
     */
    public Blog deleteUser(String id) {
        Integer count = userDao.delete(new EntityWrapper<User>().eq("user_id", id));
        return BlogUtils.blog(count,"删除成功","删除失败");
    }


    /**
     * 通过用户ID查找用户
     * @param id ：用户ID
     * @return
     */
    public User findUserById(String id){
        return userDao.selectUserById(id);
    }


    /**
     * 修改用户基本信息
     * @param user ：用户
     */
    public void updateBasicUser(User user){
        userDao.updateBasicUser(user.getPassword(),user.getHead(),user.getBirthday(),user.getUserId());
    }



    /**
     * 查询用户关注的人
     * @param userId ：用户ID
     * @return
     */
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



    /**
     * 查询用户关注的文章
     * @param userId ：用户ID
     * @return
     */
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


    /**
     * 取消关注某一个人
     * 登陆用户的FollowNum字段-1（表示当前用户关注的人少一个）
     * 被取消关注的人的isFollow字段-1（表示被取消关注的那个人的被关注人数少了一个）
     * @param userId ：取消关注的那个人的ID
     * @param loginId ：登陆用户的ID
     */
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


    /**
     * 取消关注一遍文章
     * user表中的articleNum字段-1（表示关注的文章数少一个）
     * article表中的Goods字段-1（表示article的关注的人少了一个）（在controller中实现，此处不实现）
     * @param article ：文章ID
     * @param loginId ：登陆用户的ID
     */
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


    /**
     * 关注一个人
     * 登陆的人的user表中的Follow字段+1（表示关注的人多了一个）
     * 被关注的人的user表中的isFollow字段+1（表示被关注的人多了一个粉丝）
     * @param loginId ：登陆用户的ID
     * @param userId : 用户ID
     */
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


    /**
     * 关注一边文章
     * 用户登陆的user表中articleNum字段+1（表示关注的文章多了一遍）
     * 被关注的文章article表中的goods字段+1（表示关注的文章粉丝多了一个）（此处不实现，在controller中实现）
     * @param loginId
     * @param article
     */
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
     * 充值超值会员
     * @param id ：用户ID
     * @param vip : 充值的vip是什么等级的
     * @return
     * @throws ParseException
     */
    public Blog vip(String id,String vip) throws ParseException {
        User user = userDao.selectUserById(id);
        String s = null;
        if("N".equals(user.getIsSupper())){
            user.setIsSupper("Y");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            if("12".equals(vip)){
                calendar.add(Calendar.YEAR,1);
            }else if("3".equals(vip)){
                calendar.add(Calendar.MONTH,3);
            }else if("1".equals(vip)){
                calendar.add(Calendar.MONTH,1);
            }
            Date date = calendar.getTime();
            s = FormatDateUtils.formatDate(date);
        }else {
            s = user.getSupperTime();
            Date date = FormatDateUtils.dateFormat(s);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            if("12".equals(vip)){
                calendar.add(Calendar.YEAR,1);
            }else if("3".equals(vip)){
                calendar.add(Calendar.MONTH,3);
            }else if("1".equals(vip)){
                calendar.add(Calendar.MONTH,1);
            }
            Date date1 = calendar.getTime();
            s = FormatDateUtils.formatDate(date1);
        }
        user.setSupperTime(s);
        Integer integer = userDao.update(user, new EntityWrapper<User>().eq("user_id", id));
        return BlogUtils.blog(integer,"充值成功","充值失败");
    }

}
