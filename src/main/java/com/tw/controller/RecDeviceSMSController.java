package com.tw.controller;

import com.tw.entity.BeatMessage;
import com.tw.entity.WarningMessage;
import com.tw.service.RecDeviceSMSService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    //声明一个Logger，这个是static的方式
    private final static Logger logger = LoggerFactory.getLogger(RecDeviceSMSController.class);

    /**
     * @Author: John
     * @Description:
     * @Date:  2019/8/6 23:01
     * @param: message
     * @return:
     */
    @RequestMapping("/recDeviceLogin")
    public String recDeviceLogin(@RequestParam("message") String message) {
        logger.info("======从设备端接收到的消息为："+message);

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
        logger.info("======从设备端接收到的心跳消息为：" + message);

        // 1.调用登陆验证

//        // 2.将心跳信息入库
//        Boolean isSuccess = service.beatMessageSave(message);

        //先把messge里面的内容解析成bean方便调用
        try {
            String[] beat = message.split("#");
            BeatMessage beatBean = new BeatMessage();
            beatBean.setFrame(beat[0]);
            beatBean.setMesNo(beat[1]);
            beatBean.setMesDate(beat[2]);
            beatBean.setDeviceModel(beat[3]);
            beatBean.setSerial(beat[4]);
            beatBean.setExeStatus(beat[5]);
            beatBean.setIp(beat[6]);
            logger.info("==========收到的心跳内容是"+beatBean.toString());
            logger.info("==========需要返回这样的格式的消息：0*FF#001#T42683512#OK");
            String returnStr = beatBean.getFrame()+"#"+beatBean.getMesNo()+"#"+beatBean.getSerial()+"#OK";
            logger.info("==========真实返回的内容为："+returnStr);
            return returnStr;
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("===========系统异常");
            return "java后台系统异常=======";
        }

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
        logger.info("======从设备端接收到的告警消息为："+message);

//        // 1.调用登陆验证
//
//        // 2.将告警信息入库,以时间和序列号做唯一标识,如果已存在就更新,如果不存在即插入(防止和告警视频重复插入)
//        Boolean isSuccess = service.warningMessageSave(message);
//
//        return "======"+message;

        try {
            String[] warn = message.split("#");
            WarningMessage warnBean = new WarningMessage();
            warnBean.setFrame(warn[0]);
            warnBean.setMesNo(warn[1]);
            warnBean.setMesDate(warn[2]);
            warnBean.setDeviceModel(warn[3]);
            warnBean.setSerial(warn[4]);
            warnBean.setVideoResolution(warn[5]);
            warnBean.setTargetLocation(warn[6]);
            warnBean.setExeStatus(warn[7]);
            warnBean.setIp(warn[8]);
            logger.info("==========收到的告警内容是"+warnBean.toString());
            logger.info("==========需要返回这样的格式的消息：0*10#001#T42683512#OK");
            String returnStr = warnBean.getFrame()+"#"+warnBean.getMesNo()+"#"+warnBean.getSerial()+"#OK";
            logger.info("==========真实返回的内容为："+returnStr);
            return returnStr;
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("===========系统异常");
            return "java后台系统异常=======";
        }
    }


    public static void main(String[] args) {
//          01、心跳消息
//        String beatMessage = "0*FF#001#2019-07-22T092312#ML16#T42683512#1#192.168.1.200";
//        String[] beat = beatMessage.split("#");
//        BeatMessage beatBean = new BeatMessage();
//        beatBean.setFrame(beat[0]);
//        beatBean.setMesNo(beat[1]);
//        beatBean.setMesDate(beat[2]);
//        beatBean.setDeviceModel(beat[3]);
//        beatBean.setSerial(beat[4]);
//        beatBean.setExeStatus(beat[5]);
//        beatBean.setIp(beat[6]);
//
//        logger.info("==="+beatBean.toString());
    }
}
