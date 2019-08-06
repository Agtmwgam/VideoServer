package com.tw.entity.common;

/**
 * @Author: John
 * @Description: 常量类
 * @Date: 2019/8/2 13:06
 * @param: null
 * @return:
 */
public class ConstantParam {

    // KEY为短信验证自定义秘钥
    public static final String KEY = "abc123";

    //是否可用的标记
    public static final char IS_VALID_ALL = ' ';
    public static final char IS_VALID_YES = '1';
    public static final char IS_VALID_NO = '0';

    //    正则表达式
    //    密码要求数字、符号、字母最少2种组合，而且需要8-20位                                    --by liutianwen
    public static final String VERIFYPASSWORD = "(?!^(\\d+|[a-zA-Z]+|[~!@#$%^&*?]+)$)^[\\w~!@#$%^&*?]{8,20}$";
    //    手机号验证                                                                           --by liutianwen
    public static final String VERIFYPHONENUMBER = "^[1][3,4,5,7,8][0-9]{9}$";

}
