package com.tw.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: haizhi
 * @Description:
 * @Date: 2019/8/8
 * @Param:
 * @return:
 */
@RestController
public class HelloWorld {

    @RequestMapping("/hello")
    public @ResponseBody Object hellWorld() {
        return "Hello World! This is a protected api";
    }
}