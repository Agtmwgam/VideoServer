package com.tw.test.controller;

import com.tw.util.HEXUtil;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * @Author: haizhi
 * @Description:
 * @Date: 2019/8/11
 * @Param:
 * @return:
 */
public class test {

    public static void main(String[] args) throws UnsupportedEncodingException {

        String serial = "UVWXZY";
        System.out.println("原始验证码:"+serial);

        String encode = encode(serial);
        String decode = decode(encode);


    }


    public static String encode(String serialEncode) throws UnsupportedEncodingException {

        int rand = 3;

        StringBuilder resultStr = new StringBuilder();

        // 将验证码转成16进制数
        String hexSerialEncode = HEXUtil.encode(serialEncode);

        // 将验证码的十六进制拆出来
        ArrayList<String> allHex16 = HEXUtil.splitByBytes(hexSerialEncode, 2);

        // 对每一个十六进制进行逆运算，加上rand值
        for (String everyHex16 : allHex16) {
//            System.out.println("everyHex16:"+everyHex16);
            //十六进制转十进制
            int tempC = Integer.valueOf(everyHex16, 16);
            //将随机数加上，得到加密后的报文的十进制
            int resultC = tempC + rand;
            //再将得到的加密前的十进制转成十六进制
            String resultHex = Integer.toHexString(resultC);
            resultStr.append(resultHex);
//            System.out.println("==========resultHex:"+resultHex);
//            System.out.println("==========resultStr:"+resultStr);
        }

        // 转回字符串
        String result = HEXUtil.decode(resultStr.toString());
        System.out.println("encode result:" + result);
        return result;
    }

    public static String decode(String serialEncode) throws UnsupportedEncodingException {

        int rand = 3;

        StringBuilder resultStr = new StringBuilder();

        // 将验证码转成16进制数
        String hexSerialEncode = HEXUtil.encode(serialEncode);

        // 将验证码的十六进制拆出来
        ArrayList<String> allHex16 = HEXUtil.splitByBytes(hexSerialEncode, 2);

        // 对每一个十六进制进行逆运算，减去rand值
        for (String everyHex16 : allHex16) {
//            System.out.println("everyHex16:"+everyHex16);
            //十六进制转十进制
            int tempC = Integer.valueOf(everyHex16, 16);
            //将随机数减去，得到加密前的报文的十进制
            int resultC = tempC - rand;
            //再将得到的加密前的十进制转成十六进制
            String resultHex = Integer.toHexString(resultC);
            resultStr.append(resultHex);
//            System.out.println("==========resultHex:"+resultHex);
//            System.out.println("==========resultStr:"+resultStr);
        }

        // 转回字符串
        String result = HEXUtil.decode(resultStr.toString());
        System.out.println("decode result:" + result);
        return result;
    }

}
