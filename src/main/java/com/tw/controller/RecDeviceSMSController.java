package com.tw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/recMessage")
public class RecDeviceSMSController {


    /**
     * @Author: John
     * @Description:
     * @Date:  2019/8/6 23:01
     * @param: message
     * @return:
     */
    @RequestMapping("/recDeviceLogin")
    public String recDeviceLogin(@RequestParam("message") String message) {
        System.out.println("======从设备端接收到的消息为："+message);
        return message;
    }


    /**
     * @Author: John
     * @Description: 从设备中接收心跳消息
     * @Date:  2019/8/4 23:49
     * @param: message 报文消息
     * @return:
     */
    @RequestMapping("/recDeviceBeat")
    public String recDeviceBeat(@RequestParam("message") String message) {
        System.out.println("======从设备端接收到的消息为：" + message);
        return "======" + message;
    }


    /**
     * @Author: John
     * @Description: 从设备中接收警告信息
     * @Date:  2019/8/4 23:50
     * @param: message 报文消息
     * @return:
     */
    @RequestMapping("/recDeviceWarn")
    public String recDeviceWarn(@RequestParam("message") String message) {
        System.out.println("======从设备端接收到的消息为："+message);
        return "======"+message;
    }
}
