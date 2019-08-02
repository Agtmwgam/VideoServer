package com.tw.util;

import com.alibaba.fastjson.JSONException;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.tw.config.ConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.ws.http.HTTPException;
import java.io.IOException;

/**
 * @Author: John
 * @Description:    短信工具类
 * @Date:  2019/8/2 7:40
 * @param: null
 * @return:
 */
public class SMSUtil {

    @Autowired
    private static ConfigProperties configProperties;




    public void sendOneMessage() {
        //短信验证码
        String RANDOM_INT = String.valueOf((int)((Math.random()*9+1)*10000));
        //短信间隔时间
        String SMS_TIME = "5";

        // 短信应用 SDK AppID
        int appid = 1400237*70; // SDK AppID 以1400开头
        // 短信应用 SDK AppKey
        String appkey = "a0cd360671cc379c5bb0bc779cd6eeb*";
        // 需要发送短信的手机号码
        String[] phoneNumbers = {"1881437383*"};
        // 短信模板 ID，需要在短信应用中申请
        int templateId = 38*349; // NOTE: 真实的模板 ID 需要在短信控制台中申请
        // 签名
        String smsSign = "熵康科技"; // NOTE: 签名参数使用的是`签名内容`，而不是`签名ID`。真实的签名需要在短信控制台申请

        try {
            String[] params = {RANDOM_INT, SMS_TIME};//参数，验证码为5678，30秒内填写
            SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
            System.out.println("========参数为：phoneNum:"+phoneNumbers[0]);
            SmsSingleSenderResult result = ssender.sendWithParam("86",  phoneNumbers[0], templateId, params, smsSign, "", "");
            System.out.println("========发送短信的结果为:"+result);
        } catch (HTTPException e) {
            // HTTP 响应码错误
            e.printStackTrace();
        } catch (JSONException e) {
            // JSON 解析错误
            e.printStackTrace();
        } catch (IOException e) {
            // 网络 IO 错误
            e.printStackTrace();
        } catch (com.github.qcloudsms.httpclient.HTTPException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println("=============");
        System.out.println(configProperties.getAppid());
    }
}
