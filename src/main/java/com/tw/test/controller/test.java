package com.tw.test.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: haizhi
 * @Description:
 * @Date: 2019/8/11
 * @Param:
 * @return:
 */
public class test {

    public static void main(String[] args){

        SimpleDateFormat FMT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String beat="2019-07-22T092312";
        System.out.println(Integer.valueOf(beat.substring(0,4)));
        System.out.println(Integer.valueOf(beat.substring(5,7)));
        System.out.println(Integer.valueOf(beat.substring(8,10)));
        System.out.println(Integer.valueOf(beat.substring(11,13)));
        System.out.println(Integer.valueOf(beat.substring(13,15)));
        System.out.println(Integer.valueOf(beat.substring(15,17)));
        Date date = new Date(Integer.valueOf(beat.substring(0,4))-1900,
                Integer.valueOf(beat.substring(5,7))-1, Integer.valueOf(beat.substring(8,10)),
                Integer.valueOf(beat.substring(11,13)), Integer.valueOf(beat.substring(13,15)),
                Integer.valueOf(beat.substring(15,17)));

        System.out.println(FMT.format(date));

    }

}
