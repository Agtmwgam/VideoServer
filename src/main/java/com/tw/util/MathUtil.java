package com.tw.util;

import java.util.Random;

/**
 * @Author: John
 * @Description:    生成指定位数的随机数
 * @Date:  2019/8/3 19:37
 * @param: null
 * @return:
 */
public class MathUtil {


    /**
     * 生成指定位数的随机整数
     * 如果指定位数不足两位，均返回十以内的整数
     * @param size 位数（需大于零）
     * @return 随机整数
     *
     */
    public static int random(int size) {

        Random random = new Random();
        if(size < 2){
            return random.nextInt(10);
        }
        Double pow = Math.pow(10, size - 1);
        int base = pow.intValue();
        int number = base + random.nextInt(base * 9);

        return number;
    }


/** 
 * @Author: John
 * @Description:    生成String类型的随机数
 * @Date:  2019/8/3 19:58
 * @param: null
 * @return: 
 */
    public static String randomStr(int size) {
        return String.valueOf(random(6));
    }
}
