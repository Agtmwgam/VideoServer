package com.tw.controller;

import com.tw.entity.BeatMessage;
import com.tw.entity.LoginMessage;
import com.tw.entity.WarningMessage;
import com.tw.service.RecDeviceSMSService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/recMessage")
@Slf4j
public class RecDeviceSMSController {

    final String PATTERN1 = "^[A-Z]\\d{8}$";
    final String PATTERN2 = "^[A-Z]{6}&";

    // 全局统一时间格式化格式
    SimpleDateFormat FMT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    RecDeviceSMSService service;

    //声明一个Logger，这个是static的方式
    private final static Logger logger = LoggerFactory.getLogger(RecDeviceSMSController.class);

    /**
     * @Author: John
     * @Description:
     * @Date:  2019/8/6 23:01
     * @param: message
     *      0*55#T42683512#ERDFAA#ML16#T_ML_V0.0.1#20190731#1234
     * @return:
     */
    @RequestMapping("/recDeviceLogin")
    public String recDeviceLogin(@RequestParam("message") String message) {
        logger.info("======从设备端接收到的消息为："+message);

        String[] mes = message.split("#");

        // 1.校验字段数量
        if (mes.length != 7){
            return "【ERROR】字段数量不正确";
        }

        // 2.解密
        int rand = Integer.valueOf(mes[6].substring(2, 3));

        // 十六进制-3
        // TODO
        String vertifMes = mes[2];

        // 3.校验字段
        if (Pattern.matches(PATTERN1, mes[1])){
            return "设备号不正确";
        }
        if (Pattern.matches(PATTERN2, mes[2])){
            return "设备验证吗不正确";
        }

        // 4.将 message 转换成 loginMessage
        LoginMessage loginMessage = new LoginMessage();
        loginMessage.setFrame(mes[0]);
        loginMessage.setSerial(mes[1]);
        loginMessage.setDeviceVerifyCode(mes[2]);
        loginMessage.setDeviceType(mes[3]);
        loginMessage.setSoftVersion(mes[4]);
        loginMessage.setProductDate(mes[5]);
        loginMessage.setRand(mes[6]);

        // 5.校验数据库
        boolean isCheck = service.checkDevice(mes[1], vertifMes);

        // 6.验证数据无误后写入loginMessage
        service.loginMessageSave(loginMessage);

        return message;
    }

    /**
     * @Author: John
     * @Description: 从设备中接收心跳消息
     * @Date:  2019/8/4 23:49
     * @param: message 报文消息
     *      0*FF#001#2019-07-22T092312#ML16#T42683512#1#192.168.1.200
     * @return: 0*10#001#SQZN001#OK
     */
    @RequestMapping("/recDeviceBeat")
    public String recDeviceBeat(@RequestParam("message") String message) {
        logger.info("======从设备端接收到的心跳消息为：" + message);


        // 2.数据校验,心跳数据为7个字段
        String[] beat = message.split("#");
        if (beat.length!=7){
            log.error("【心跳】字段数量不正确");
            return "字段数量不正确";
        }


        // 3.先把messge里面的内容解析成bean方便调用
        try {
            BeatMessage beatMessageBean = new BeatMessage();
            beatMessageBean.setFrame(beat[0]);
            beatMessageBean.setMesNo(beat[1]);
            beatMessageBean.setMesDate(new Date(Integer.valueOf(beat[2].substring(0,4))-1900,
                    Integer.valueOf(beat[2].substring(5,7))-1, Integer.valueOf(beat[2].substring(8,10)),
                    Integer.valueOf(beat[2].substring(11,13)), Integer.valueOf(beat[2].substring(13,15)),
                    Integer.valueOf(beat[2].substring(15,17))));
            beatMessageBean.setDeviceModel(beat[3]);
            beatMessageBean.setSerial(beat[4]);
            beatMessageBean.setExeStatus(beat[5].charAt(0));
            beatMessageBean.setIp(beat[6]);

            // 校验字段
            if (!Pattern.matches(PATTERN1, beatMessageBean.getSerial())){
                log.error("【心跳】设备号不正确");
                return "设备号不正确";
            }

            // 调用登陆验证
            if (!service.checkLogin(beatMessageBean.getSerial())){
                log.error("【心跳】登陆验证信息错误");
                return "登陆验证信息错误.";
            }


            logger.info("【心跳】==========收到的心跳内容是"+beatMessageBean.toString());
            logger.info("【心跳】==========需要返回这样的格式的消息：0*FF#001#T42683512#OK");
            String returnStr = beatMessageBean.getFrame()+"#"+beatMessageBean.getMesNo()+"#"+beatMessageBean.getSerial()+"#OK";
            logger.info("【心跳】==========真实返回的内容为："+returnStr);

            // 4.将心跳信息入库
            service.beatMessageSave(beatMessageBean);

            return returnStr;
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("【心跳】===========系统异常");
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

        // 2.数据校验,告警数据为9个字段
        String[] beat = message.split("#");
        if (beat.length!=9){
            log.error("【心跳】字段数量不正确");
            return "字段数量不正确";
        }

        try {
            String[] warn = message.split("#");
            WarningMessage warningMessageBean = new WarningMessage();
            warningMessageBean.setFrame(warn[0]);
            warningMessageBean.setMesNo(warn[1]);
            warningMessageBean.setMesDate(new Date(Integer.valueOf(warn[2].substring(0,4))-1900,
                    Integer.valueOf(warn[2].substring(5,7))-1, Integer.valueOf(warn[2].substring(8,10)),
                    Integer.valueOf(warn[2].substring(11,13)), Integer.valueOf(warn[2].substring(13,15)),
                    Integer.valueOf(warn[2].substring(15,17))));
            warningMessageBean.setDeviceModel(warn[3]);
            warningMessageBean.setSerial(warn[4]);
            warningMessageBean.setVideoResolution(warn[5]);
            warningMessageBean.setTargetLocation(warn[6]);
            warningMessageBean.setExeStatus(warn[7]);
            warningMessageBean.setIp(warn[8]);
            logger.info("【告警】==========收到的告警内容是"+warningMessageBean.toString());
            logger.info("【告警】==========需要返回这样的格式的消息：0*10#001#T42683512#OK");
            String returnStr = warningMessageBean.getFrame()+"#"+warningMessageBean.getMesNo()+"#"+warningMessageBean.getSerial()+"#OK";
            logger.info("【告警】==========真实返回的内容为："+returnStr);

            // 校验字段
            if (!Pattern.matches(PATTERN1, warningMessageBean.getSerial())){
                log.error("【告警】设备号不正确");
                return "设备号不正确";
            }

            // 调用登陆验证
            if (!service.checkLogin(warningMessageBean.getSerial())){
                log.error("【告警】登陆验证信息错误");
                return "登陆验证信息错误.";
            }

            // 4.将告警信息入库
            service.warningMessageSave(warningMessageBean);

            return returnStr;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("【告警】===========系统异常");
            return "java后台系统异常=======";
        }
    }


    public static void main(String[] args) {
//          01、心跳消息
//        String beatMessage = "0*FF#001#2019-07-22T092312#ML16#T42683512#1#192.168.1.200";
//        String[] beat = beatMessage.split("#");
//        BeatMessage beatMessageBean = new BeatMessage();
//        beatMessageBean.setFrame(beat[0]);
//        beatMessageBean.setMesNo(beat[1]);
//        beatMessageBean.setMesDate(beat[2]);
//        beatMessageBean.setDeviceModel(beat[3]);
//        beatMessageBean.setSerial(beat[4]);
//        beatMessageBean.setExeStatus(beat[5]);
//        beatMessageBean.setIp(beat[6]);
//
//        logger.info("==="+beatMessageBean.toString());
    }
}
