package com.shenzc.commonEntity;

import lombok.Data;

import java.util.List;

/**
 * @author shenzc
 * @create 2019-01-07-14:08
 */
@Data
public class Blog<T> {

    private boolean success;
    private String message;
    private List<T> list;

    public Blog(){ }

    public Blog(boolean success, String message){
        this.success = success;
        this.message = message;
    }

    public Blog(boolean success, String message, List<T> list){
        this.success = success;
        this.message = message;
        this.list = list;
    }


}
