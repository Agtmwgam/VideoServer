package com.tw.entity.common;

/**
 * @Author: John
 * @Description:    常量类
 * @Date:  2019/8/2 13:06
 * @param: null
 * @return:
 */
public class ConstantParam {

    //    手机号要求数字、符号、字母最少2种组合，而且需要8-20位
    public static final String VERIFYPHONE = "(?!^(\\d+|[a-zA-Z]+|[~!@#$%^&*?]+)$)^[\\w~!@#$%^&*?]{8,20}$";

    //是否可用的标记
    public static final char IS_VALID_ALL = ' ';
    public static final char IS_VALID_YES = '1';
    public static final char IS_VALID_NO = '0';


}
