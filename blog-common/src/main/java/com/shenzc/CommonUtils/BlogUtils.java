package com.shenzc.CommonUtils;

import com.shenzc.commonEntity.Blog;

/**
 * @author shenzc
 * @create 2019-03-22-11:44
 */
public class BlogUtils {

    public static Blog blog(int blog,String blog1,String blog2){
        if (blog > 0) {
            return new Blog(true, blog1);
        } else {
            return new Blog(false, blog2);
        }
    }
}
