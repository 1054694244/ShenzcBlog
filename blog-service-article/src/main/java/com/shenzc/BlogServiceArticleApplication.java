package com.shenzc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.shenzc.mapper")
public class BlogServiceArticleApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogServiceArticleApplication.class, args);
    }

}
