package com.shenzc;

import com.shenzc.CommonUtils.FormatDateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BlogServiceUserApplicationTests {

    @Test
    public void contextLoads() {
        System.out.println(FormatDateUtils.formatDate(new Date()));
    }

}
