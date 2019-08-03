package com.tw.controller;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.tw.util.*;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.tw.util.ResponseInfo.CODE_ERROR;
import static com.tw.util.ResponseInfo.CODE_SUCCESS;

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


    /**
     * @Author: John
     * @Description: 发送短信的方法
     * @Date:  2019/8/4 0:00
     * @param: requestMap
     * @return:
     */
    @RequestMapping(value = "/smsSendCode", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseInfo sendMsg(@RequestBody Map<String,Object> requestMap) {
        ResponseInfo response = new ResponseInfo();
        String phoneNumber = (String) requestMap.get("phoneNumber");

        //校验前端传过来的手机号码是否是正确的，如果正确就继续，否则就返回格式错误
        Boolean isValidPhoneNumber = PhoneUtil.isNotValidChinesePhone(phoneNumber);
        if (isValidPhoneNumber) {
            //生成随机数
            String randomNum = String.valueOf(MathUtil.random(6));
            SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MINUTE, 5);
            //生成5分钟后时间，用户校验是否过期
            String currentTime = sf.format(calendar.getTime());

            //此处执行发送短信验证码方法
            Boolean isSend = SMSUtil.sendOneMessage(phoneNumber);
            if (isSend) {
                //生成MD5值
                String hash =  MD5Util.toMD5(KEY + "@" + currentTime + "@" + randomNum);
                Map<String, Object> resultMap = new HashMap<>();
                resultMap.put("hash", hash);
                resultMap.put("tamp", currentTime);
                response.setCode(CODE_SUCCESS);
                response.setMessage("send message success!");
                //将hash值和tamp时间返回给前端
                response.setData(resultMap);
            } else {
                response.setCode(CODE_ERROR);
                response.setMessage("send message failed!");
            }
        } else {
            response.setCode(CODE_ERROR);
            response.setMessage("it's not correct phoneNumber!");
        }
        return response;
    }


    /**
     * @Author: John
     * @Description: 校验短信验证是否正确
     * @Date:  2019/8/4 0:01
     * @param: requestMap
     * @return:
     */
    @RequestMapping(value = "/smsValidate", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseInfo validateNum(@RequestBody Map<String,Object> requestMap) {
        ResponseInfo response = new ResponseInfo();
        //从前端传过来的数据
        String requestHash = requestMap.get("hash").toString();
        String tamp = requestMap.get("tamp").toString();
        String msgNum = requestMap.get("msgNum").toString();
        String hash = MD5Util.toMD5(KEY + "@" + tamp + "@" + msgNum);
        if (tamp.compareTo(DateUtils.getDateTime()) > 0) {
            if (hash.equalsIgnoreCase(requestHash)){
                //校验成功
                response.setCode(CODE_SUCCESS);
                response.setMessage("message validate success!");
            }else {
                //验证码不正确，校验失败
                response.setCode(CODE_ERROR);
                response.setMessage("message validate failed!");
            }
        } else {
            // 超时
            response.setCode(CODE_ERROR);
            response.setMessage("code is out of time, please send again!");
        }
        return response;
    }
}