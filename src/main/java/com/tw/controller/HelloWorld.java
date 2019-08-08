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
//    Object hellWorld(HttpServletRequest httpServletRequest) {
//        Map<String, Claim> claimMap = JwtUtil.getToken(httpServletRequest);
//        System.out.println("userId : " + claimMap.get("userId"));
//        System.out.println("userName : " + claimMap.get("userName"));
//        System.out.println("spcodeId : " + claimMap.get("spcodeId"));
//        System.out.println("businessOfficeId : " + claimMap.get("businessOfficeId"));
//        System.out.println("serviceGridId : " + claimMap.get("serviceGridId"));
        return "Hello World! This is a protected api";
    }

}
