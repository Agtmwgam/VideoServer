package com.tw.controller;

import com.tw.util.DateUtils;
import com.tw.util.MD5Util;
import com.tw.util.MathUtil;
import com.tw.util.SMSUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: John
 * @Description:    用于校验登录的控制权
 * @Date:  2019/8/3 19:20
 * @param: null
 * @return:
 */
@Controller
public class LoginController {

    private static final String KEY = "abc123"; // KEY为自定义秘钥

    @RequestMapping(value = "/sendMsg", method = RequestMethod.POST, headers = "Accept=application/json")
    public Map<String, Object> sendMsg(@RequestBody Map<String,Object> requestMap) {
        String[] phoneNumbers = (String[])requestMap.get("phoneNumber");
        //生成随机数
        String randomNum = String.valueOf(MathUtil.random(6));
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MINUTE, 5);
        //生成5分钟后时间，用户校验是否过期
        String currentTime = sf.format(c.getTime());
        //此处执行发送短信验证码方法
        SMSUtil.sendOneMessage(phoneNumbers);
        //生成MD5值
        String hash =  MD5Util.toMD5(KEY + "@" + currentTime + "@" + randomNum);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("hash", hash);
        resultMap.put("tamp", currentTime);
        //将hash值和tamp时间返回给前端
        return resultMap;
    }


    @RequestMapping(value = "/validateNum", method = RequestMethod.POST, headers = "Accept=application/json")
    public Map<String, Object> validateNum(@RequestBody Map<String,Object> requestMap) {
        String requestHash = requestMap.get("hash").toString();
        String tamp = requestMap.get("tamp").toString();
        String msgNum = requestMap.get("msgNum").toString();
        String hash = MD5Util.toMD5(KEY + "@" + tamp + "@" + msgNum);
        if (tamp.compareTo(DateUtils.getDateTime()) > 0) {
            if (hash.equalsIgnoreCase(requestHash)){
                //校验成功
            }else {
                //验证码不正确，校验失败
            }
        } else {
            // 超时
        }
        return requestMap;
    }
}
