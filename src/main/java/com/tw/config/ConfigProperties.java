package com.tw.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @Author: John
 * @Description:    配置文件
 * @Date:  2019/8/2 13:16
 * @param: null
 * @return:
 */
@Configuration
@ConfigurationProperties(prefix = "sms")
@PropertySource("classpath:application.properties")
public class ConfigProperties {

    // 短信应用 SDK AppID
    @Value("${sms.appid}")
    private int appid;

    // 短信应用 SDK AppKey
    @Value("${sms.appkey}")
    private String appkey;

    // 短信模板 ID，需要在短信应用中申请
    @Value("${sms.templateId}")
    private int templateId;

    // 签名
    @Value("${sms.smsSign}")
    private String smsSign;

    //时间间隔?分钟
    @Value("${sms.time}")
    private String smsTime;


    public int getAppid() {
        return appid;
    }

    public void setAppid(int appid) {
        this.appid = appid;
    }

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public int getTemplateId() {
        return templateId;
    }

    public void setTemplateId(int templateId) {
        this.templateId = templateId;
    }

    public String getSmsSign() {
        return smsSign;
    }

    public void setSmsSign(String smsSign) {
        this.smsSign = smsSign;
    }

    public String getSmsTime() {
        return smsTime;
    }

    public void setSmsTime(String smsTime) {
        this.smsTime = smsTime;
    }


    @Override
    public String toString() {
        return "ConfigProperties{" +
                "appid=" + appid +
                ", appkey='" + appkey + '\'' +
                ", templateId=" + templateId +
                ", smsSign='" + smsSign + '\'' +
                ", smsTime='" + smsTime + '\'' +
                '}';
    }
}