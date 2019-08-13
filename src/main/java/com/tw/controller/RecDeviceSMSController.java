package com.tw.controller;

import com.tw.convert.String2DateConvert;
import com.tw.entity.BeatMessage;
import com.tw.entity.LoginMessage;
import com.tw.entity.WarningMessage;
import com.tw.service.RecDeviceSMSService;
import com.tw.util.HEXUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/recMessage")
@Slf4j
public class RecDeviceSMSController {

    final String PATTERN1 = "^[A-Z]\\d{8}$";
    final String PATTERN2 = "^[A-Z]{6}$";

    // 全局统一时间格式化格式
    SimpleDateFormat FMT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    RecDeviceSMSService service;

    //声明一个Logger，这个是static的方式
    private final static Logger logger = LoggerFactory.getLogger(RecDeviceSMSController.class);

    /**
     * @Author: John,zhuoshouyi
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
            log.error("【设备登陆】字段数量不正确");
            return "字段数量不正确,无法解析";
        }

        // 传入参数
        final String FRAME = mes[0];
        final String SERIAL = mes[1];
        final String DEVICEVERIFYCODE = mes[2];
        final String RAND = mes[6];

        // 返回值
        final String NOLOGIN = FRAME + "#" + SERIAL + "#notlogin";
        final String SUCCESS = FRAME + "#" + SERIAL + "#OK";
        final String FAILD = FRAME + "#" + SERIAL + "#ERROR";

        // 2.解密
        int rand = Integer.valueOf(RAND.substring(2, 3));
        // 十六进制-3
        String vertifMes;
        try {
//            vertifMes = loginMessageDecode(DEVICEVERIFYCODE, rand);
            vertifMes = DEVICEVERIFYCODE;

        }catch (Exception e){
            log.error("【设备登陆】验证码解析错误");
            return FAILD+"#验证码解析错误";
        }

        // 3.校验字段
        if (!Pattern.matches(PATTERN1, SERIAL)){
            log.error("【设备登陆】设备号格式不正确");
            return FAILD+"#设备号格式不正确";
        }
        if (!Pattern.matches(PATTERN2, DEVICEVERIFYCODE)){
            log.error("【设备登陆】设备验证码格式不正确");
            return FAILD+"#设备验证码格式不正确";
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
        boolean isCheck = service.checkDevice(SERIAL, vertifMes);

        // 6.验证数据无误后写入loginMessage
        if (isCheck){
            service.loginMessageSave(loginMessage);
            log.info("【设备登陆】" + SERIAL + "设备登陆成功");
            return SUCCESS;
        }else {
            log.error("【设备登陆】设备号或验证码错误");
            return FAILD+"#设备号或验证码错误";
        }

    }

    /**
     * @Author: John,zhuoshouyi
     * @Description: 从设备中接收心跳消息
     * @Date:  2019/8/4 23:49
     * @param: message 报文消息
     *      0*FF#001#2019-07-22T092312#ML16#T42683512#1#192.168.1.200
     * @return: 0*10#001#SQZN001#OK
     */
    @RequestMapping("/recDeviceBeat")
    public String recDeviceBeat(@RequestParam("message") String message) {

        logger.info("======从设备端接收到的心跳消息为：" + message);

        // 1.数据校验,心跳数据为7个字段
        String[] beat = message.split("#");
        if (beat.length!=7){
            log.error("【心跳】字段数量不正确");
            return "字段数量不正确,无法解析";
        }

        // 传入参数
        final String FRAME = beat[0];
        final String MESNO = beat[1];
        final String SERIAL = beat[4];
        final String EXESTATUS = beat[5];

        // 返回值
        final String NOLOGIN = FRAME + "#" + SERIAL + "#notlogin";
        final String SUCCESS = FRAME + "#" + MESNO + "#" + SERIAL + "#OK";
        final String FAILD = FRAME + "#"+ MESNO + "#" + SERIAL + "#ERROR";

        // 3.先把messge里面的内容解析成bean方便调用
        try {
            BeatMessage beatMessageBean = new BeatMessage();
            beatMessageBean.setFrame(FRAME);
            beatMessageBean.setMesNo(MESNO);
            beatMessageBean.setMesDate(String2DateConvert.convert(beat[2]));
            beatMessageBean.setDeviceModel(beat[3]);
            beatMessageBean.setSerial(SERIAL);
            beatMessageBean.setExeStatus(EXESTATUS.charAt(0));
            beatMessageBean.setIp(beat[6]);

            // 校验字段
            if (!Pattern.matches(PATTERN1, beatMessageBean.getSerial())){
                log.error("【心跳】设备号不正确");
                return FAILD+"#设备号不正确";
            }

            // 调用登陆验证
            if (!service.checkLogin(beatMessageBean.getSerial())){
                log.error("【心跳】登陆验证信息错误");
                return NOLOGIN;
            }

            // 4.将心跳信息入库
            service.beatMessageSave(beatMessageBean);
            return SUCCESS;

        } catch (Exception e) {
            e.printStackTrace();
            logger.info("【心跳】===========系统异常");
            return "java后台系统异常=======";
        }



    }


    /**
     * @Author: John,zhuoshouyi
     * @Description: 从设备中接收警告信息
     * @Date:  2019/8/4 23:50
     * @param: message 报文消息
     *      0*10#001#2019-07-22T092312#ML16#T42683512#1280*720#123,54,480,320#1#192.168.1.200
     * @return: 0*10#001#SQZN001#OK
     */
    @RequestMapping("/recDeviceWarn")
    public String recDeviceWarn(@RequestParam("message") String message) {
        logger.info("======从设备端接收到的告警消息为："+message);

        // 2.数据校验,告警数据为9个字段
        String[] warn = message.split("#");
        if (warn.length!=9){
            log.error("【告警】字段数量不正确");
            return "字段数量不正确,无法解析";
        }

        // 传入参数
        final String FRAME = warn[0];
        final String MESNO = warn[1];
        final String MESDATE = warn[2];
        final String SERIAL = warn[4];

        // 返回值
        final String NOLOGIN = FRAME + "#" + SERIAL + "#notlogin";
        final String SUCCESS = FRAME + "#" + MESNO + "#" + SERIAL + "#OK";
        final String FAILD = FRAME + "#"+ MESNO + "#" + SERIAL + "#ERROR";

        try {
            WarningMessage warningMessageBean = new WarningMessage();
            warningMessageBean.setFrame(FRAME);
            warningMessageBean.setMesNo(MESNO);
            warningMessageBean.setMesDate(String2DateConvert.convert(MESDATE));
            warningMessageBean.setDeviceModel(warn[3]);
            warningMessageBean.setSerial(SERIAL);
            warningMessageBean.setVideoResolution(warn[5]);
            warningMessageBean.setTargetLocation(warn[6]);
            warningMessageBean.setExeStatus(warn[7]);
            warningMessageBean.setIp(warn[8]);
            warningMessageBean.setEventId(SERIAL + "_" + MESDATE);
            // 通过 serial 查找此设备的分组名和设备名
            List<String> stringList = service.findGroupNameAndDeviceName(SERIAL);
            if (stringList!=null && stringList.size()==2){
                warningMessageBean.setGroupName(stringList.get(0));
                warningMessageBean.setDeviceName(stringList.get(1));
            }

            // 校验字段
            if (!Pattern.matches(PATTERN1, warningMessageBean.getSerial())){
                log.error("【告警】设备号不正确");
                return FAILD+"#设备号不正确";
            }

            // 调用登陆验证
            if (!service.checkLogin(warningMessageBean.getSerial())){
                log.error("【告警】登陆验证信息错误");
                return NOLOGIN;
            }

            // 4.将告警信息入库
            service.warningMessageSave(warningMessageBean);

            return SUCCESS;

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("【告警】===========系统异常");
            return "java后台系统异常=======";
        }
    }


    /**
     * 设备登陆信息解密方法
     *
     * @param serialEncode
     * @param rand
     * @return
     * @throws UnsupportedEncodingException
     */
    public String loginMessageDecode(String serialEncode, Integer rand) throws UnsupportedEncodingException {

        StringBuilder resultStr = new StringBuilder();

        // 将验证码转成16进制数
        serialEncode = serialEncode.replace("\\", "\\\\");
        String hexSerialEncode = HEXUtil.encode(serialEncode);

        // 将验证码的十六进制拆出来
        ArrayList<String> allHex16 = HEXUtil.splitByBytes(hexSerialEncode, 2);

        // 对每一个十六进制进行逆运算，减去rand值
        for (String everyHex16 : allHex16) {
            //十六进制转十进制
            int tempC = Integer.valueOf(everyHex16, 16);
            //将加密前加的那个随机数减回去，得到加密前的报文的十进制
            int resultC = tempC - rand;
            //再将得到的加密前的十进制转成十六进制
            String resultHex = Integer.toHexString(resultC);
            resultStr.append(resultHex);
//            System.out.println("==========resultHex:"+resultHex);
//            System.out.println("==========resultStr:"+resultStr);
        }

        // 转回字符串
        String result = HEXUtil.decode(resultStr.toString());
        return result;
    }


    public static void main(String[] args) throws UnsupportedEncodingException {

//        StringBuilder resultStr = new StringBuilder();
//
//        //测试十六进制每一位都减3
//        //ABCDEF ==》 414243444546(16)
//
//        String recCode = "444546474849";
//        //取到报文里面的随机数中的第三位数：
//        int random = 3;
//        //转成十进制
//        int randomHex = Integer.valueOf(String.valueOf(random), 16);
//
//        //01、将十六进制拆出来
//        ArrayList<String> allHex16 = HEXUtil.splitByBytes(recCode, 2);
//
//
//        //02、对每一个十六进制进行逆运算，减去random值
//        for (String everyHex16 : allHex16) {
//            //十六进制转十进制
//            int tempC = Integer.valueOf(everyHex16, 16);
//            //将加密前加的那个随机数减回去，得到加密前的报文的十进制
//            int resultC = tempC - randomHex;
//            //再将得到的加密前的十进制转成十六进制
//            String resultHex = Integer.toHexString(resultC);
//            resultStr.append(resultHex);
//            System.out.println("==========resultHex:"+resultHex);
//            System.out.println("==========resultStr:"+resultStr);
//        }
//        System.out.println("============转回字符串："+HEXUtil.decode(resultStr.toString()));



    }


}
