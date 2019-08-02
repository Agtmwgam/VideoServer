package com.tw.controller;

import com.tw.config.ConfigProperties;
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

    @GetMapping("/test")
    public String test() {
        System.out.println("============调用debug的test方法");
        System.out.println(configProperties.toString());;
        return "========debug - test";
    }
}
