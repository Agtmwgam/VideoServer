package com.tw.controller;

import com.tw.entity.common.ConstantParam;
import com.tw.service.LoginService;
import com.tw.service.MessageService;
import com.tw.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
@RequestMapping("/login")
public class LoginController {

    @Autowired
    MessageService messageService;

    @Autowired
    private LoginService loginService;

    @GetMapping("/sendMessage")
    public String sendMessage() {
        Boolean isSend = messageService.sendMessage("18210081211");
        System.out.println("=========发送短信结果:"+isSend);
        return "发送成功";
    }


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
            Map<String, Object> resultMap = loginService.sendMessage(phoneNumber);
            response.setCode(CODE_SUCCESS);
            response.setMessage("send message success!");
            //将hash值和tamp时间返回给前端
            response.setData(resultMap);
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
        ResponseInfo response = loginService.validateNum(requestMap);
        return response;
    }
}