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




    public void sendOneMessage(String[] phoneNumbers) {
        //短信验证码
        String RANDOM_INT = String.valueOf((int)((Math.random()*9+1)*10000));

        //短信间隔时间
        String smsTime = configProperties.getSmsTime();

        // 短信应用 SDK AppID
        int appid = configProperties.getAppid();
        // 短信应用 SDK AppKey
        String appkey = configProperties.getAppkey();

        // 短信模板 ID，需要在短信应用中申请
        int templateId = configProperties.getTemplateId();
        // 签名
        String smsSign = configProperties.getSmsSign();

        try {
            //参数，例如 验证码为5678，3分钟内填写
            String[] params = {RANDOM_INT, smsTime};
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
