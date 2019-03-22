package com.shenzc;

import com.shenzc.CommonUtils.FormatDateUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author shenzc
 * @create 2019-03-17-10:44
 */
public class Test {
    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
        System.out.println(sdf.format(new Date()));
        Calendar calendar = Calendar.getInstance();
        System.out.println(FormatDateUtils.formatDate(calendar.getTime()));
    }
}
