package com.shenzc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.shenzc.mapper")
public class BlogServiceFileApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogServiceFileApplication.class, args);
    }

}
