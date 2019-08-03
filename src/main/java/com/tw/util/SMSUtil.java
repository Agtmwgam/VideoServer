package com.tw.util;

import com.alibaba.fastjson.JSONException;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.tw.config.ConfigProperties;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    //声明一个Logger，这个是static的方式，我比较习惯这么写。
    private final static Logger logger = LoggerFactory.getLogger(SMSUtil.class);


    /**
     * @Author: John
     * @Description:       发送单条短信
     * @Date:  2019/8/3 23:12
     * @param: null
     * @return:
     */
    public static Boolean sendOneMessage(String phoneNumber) {
        //短信验证码
        String RANDOM_INT = MathUtil.randomStr(6);

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
            //参数，例如 验证码为5678，5分钟内填写
            String[] params = {RANDOM_INT, smsTime};
            SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
            logger.info("========参数为：phoneNum:"+phoneNumber);
            SmsSingleSenderResult result = ssender.sendWithParam("86",  phoneNumber, templateId, params, smsSign, "", "");
            logger.info("========发送短信的结果为:"+result);
            String resultStr = result.toString();
            //TODO
            //如果短信结果是成功的，就需要将短信和手机号码和加密密钥变成MD5码
            if (StringUtils.isNotEmpty(resultStr)) {
                if (resultStr.contains("success")) {
                    return true;
                } else {
                    return false;
                }
            }
            return false;
            //TODO
        } catch (HTTPException e) {
            // HTTP 响应码错误
            e.printStackTrace();
            return false;
        } catch (JSONException e) {
            // JSON 解析错误
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            // 网络 IO 错误
            e.printStackTrace();
            return false;
        } catch (com.github.qcloudsms.httpclient.HTTPException e) {
            e.printStackTrace();
            return false;
        }
    }
}