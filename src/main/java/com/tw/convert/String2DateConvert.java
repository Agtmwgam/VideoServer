package com.tw.convert;

import java.util.Date;

/**
 * @Author: haizhi
 * @Description:
 * @Date: 2019/8/13
 * @Param:
 * @return:
 */
public class String2DateConvert {

    public static Date convert(String str){

        // 此处的str仅表示告警以及心跳中的日期格式 (2019-8-12T22-21-52)
        // 解析时间格式
        String[] strstmp = str.split("T");
        String[] strs1 = strstmp[0].split("-");
        String[] strs2 = strstmp[1].split("-");
        String year = strs1[0];
        String month = strs1[1];
        String day = strs1[2];
        String hour = strs2[0];
        String min = strs2[1];
        String sed = strs2[2];
        return new Date(Integer.valueOf(year)-1900,
                Integer.valueOf(month)-1, Integer.valueOf(day),
                Integer.valueOf(hour), Integer.valueOf(min),
                Integer.valueOf(sed));

    }
}
