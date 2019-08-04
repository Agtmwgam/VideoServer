package com.tw.controller;

import com.tw.config.ConfigProperties;
import com.tw.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: John
 * @Description:    用于测试的接口
 * @Date:  2019/8/2 21:44
 * @param: null
 * @return: 
 */
@RequestMapping("/debug")
@Controller
public class DebugController {

    @Autowired
    private ConfigProperties configProperties;

    @Autowired
    private MessageService messageService;

    @GetMapping("/test")
    public String test() {
        System.out.println("============="+configProperties.toString());
        Boolean isSend = messageService.sendMessage("18814373836");
        System.out.println("=========发送短信结果:"+isSend);
        System.out.println("============调用debug的test方法");
        return "调用debug的test方法";
    }
}
