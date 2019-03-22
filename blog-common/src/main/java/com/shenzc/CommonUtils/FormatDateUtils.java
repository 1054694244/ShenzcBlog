package com.shenzc.CommonUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author shenzc
 * @create 2019-03-14-9:38
 */

//日期格式转换器
public class FormatDateUtils {

    //转换成日期+时间
    public static String formatDateTime(Date dataTime){
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
        String date = sdf.format(dataTime);
        return date;
    }


    //转换成日期+时间
    public static String formatDate(Date dataTime){
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
        String date = sdf.format(dataTime);
        return date;
    }

    //转换成日期+时间
    public static Date dateFormat(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD");
        Date date1 = sdf.parse(date);
        return date1;
    }

}
