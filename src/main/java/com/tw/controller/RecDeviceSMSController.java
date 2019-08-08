package com.tw.controller;

import com.tw.service.RecDeviceSMSService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recMessage")
@Slf4j
public class RecDeviceSMSController {


    @Autowired
    RecDeviceSMSService service;

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

        //

        return message;
    }

    /**
     * @Author: John
     * @Description: 从设备中接收心跳消息
     * @Date:  2019/8/4 23:49
     * @param: message 报文消息
     *
     * @return: 0*10#001#SQZN001#OK
     */
    @RequestMapping("/recDeviceBeat")
    public String recDeviceBeat(@RequestParam("message") String message) {
        System.out.println("======从设备端接收到的心跳消息为：" + message);

        // 1.调用登陆验证

        // 2.将心跳信息入库
        Boolean isSuccess = service.beatMessageSave(message);

        return "======" + message;
    }


    /**
     * @Author: John
     * @Description: 从设备中接收警告信息
     * @Date:  2019/8/4 23:50
     * @param: message 报文消息
     *      0*FF#001#2019-07-22T092312#SK01#SQZN001#1#192.168.1.2003
     * @return: 0*10#001#SQZN001#OK
     */
    @RequestMapping("/recDeviceWarn")
    public String recDeviceWarn(@RequestParam("message") String message) {
        System.out.println("======从设备端接收到的告警消息为："+message);

        // 1.调用登陆验证

        // 2.将告警信息入库,以时间和序列号做唯一标识,如果已存在就更新,如果不存在即插入(防止和告警视频重复插入)
        Boolean isSuccess = service.warningMessageSave(message);

        return "======"+message;
    }
}
