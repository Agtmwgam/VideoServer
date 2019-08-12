package com.tw.util;


import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * @Author: John
 * @Description: 进制转换工具类
 * @Date:  2019/8/11 10:58
 * @param: null
 * @return:
 */
public class HEXUtil {

    //16进制数字字符集
    public static String hexString = "0123456789ABCDEF";


    //转化字符串为十六进制编码
    public static String toHexString(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str + s4;
        }
        return str;
    }


    // 转化十六进制编码为字符串
    public static String toStringHex1(String s) {
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(
                        i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword, "utf-8");// UTF-16le:Not
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }


    /*
     * 将字符串编码成16进制数字,适用于所有字符（包括中文）
     */
    public static String encode(String str) {
        // 根据默认编码获取字节数组
        byte[] bytes = str.getBytes();
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        // 将字节数组中每个字节拆解成2位16进制整数
        for (int i = 0; i < bytes.length; i++) {
            sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
            sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0));
        }
        return sb.toString();
    }


    /*
     * 将16进制数字解码成字符串,适用于所有字符（包括中文）
     */
    public static String decode(String bytes) {
        byte[] baKeyword = new byte[bytes.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(bytes.substring(
                        i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            bytes = new String(baKeyword, "utf-8");// UTF-16le:Not
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return bytes;
    }


    //功能：按固定长度来分割字符串 chenst
    public static ArrayList<String> splitByBytes(String text, int length) throws UnsupportedEncodingException {

        String encode="GBK";
        if (text == null) {
            return null;
        }
        ArrayList<String> list=new ArrayList<String>();
        StringBuilder sb = new StringBuilder();
        int currentLength = 0;
        for (int i=0;i<text.toCharArray().length;i++)
        {
            char c=text.charAt(i);
            currentLength += String.valueOf(c).getBytes(encode).length;
            if ( currentLength <= length) {
                sb.append(c);
            } else {
                currentLength=0;
                currentLength += String.valueOf(c).getBytes(encode).length;
                list.add(sb.toString());
                sb.replace(0,sb.length(),"");
                sb.append(c);
            }
            if(i==text.toCharArray().length-1)
                list.add(sb.toString());
        }
        return list;
    }


    public static int hexToDecimal(String hex)
    {
        int decimalValue=0;
        for(int i=0;i<hex.length();i++)
        {
            char hexChar=hex.charAt(i);
            decimalValue=decimalValue*16+hexCharToDecimal(hexChar);
        }
        return decimalValue;
    }


    public static int hexCharToDecimal(char hexChar)
    {
        if(hexChar>='A'&&hexChar<='F')
            return 10+hexChar-'A';
        else
            //切记不能写成int类型的0，因为字符'0'转换为int时值为48
            return hexChar-'0';
    }


    public static void main(String[] args) {
        System.out.println(encode("A"));
        System.out.println(decode("444546474849"));
        System.out.println("============:"+hexCharToDecimal('A'));
    }
}