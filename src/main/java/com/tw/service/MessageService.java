package com.tw.service;

import com.alibaba.fastjson.JSONException;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.tw.config.ConfigProperties;
import com.tw.entity.common.ConstantParam;
import com.tw.util.DateUtils;
import com.tw.util.MD5Util;
import com.tw.util.MathUtil;
import com.tw.util.ResponseInfo;
import org.apache.commons.lang.StringUtils;
import org.apache.jasper.tagplugins.jstl.core.If;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.xml.ws.http.HTTPException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.tw.util.ResponseInfo.CODE_ERROR;
import static com.tw.util.ResponseInfo.CODE_SUCCESS;

/**
 * @author liutianwen
 * @Description: 处理短信的service
 * @date 2019年8月3日
 */
@Service
public class MessageService {

    @Autowired
    private ConfigProperties configProperties;

	//声明一个Logger，这个是static的方式
	private final static Logger logger = LoggerFactory.getLogger(MessageService.class);


    @Async //异步注解
    public void sendMsg() {
        System.out.println("开始发送短信2");
        for (int i = 0; i < 3; i++) {
            System.out.println(i);
            if (i == 2) {
                System.out.println("短信发送完毕3");
            }
        }
    }


	/**
	 * @Author: John
	 * @Description: 发送单条短信
	 * @Date:  2019/8/5 1:26
	 * @param: phoneNumber	手机号码
	 * @return:
	 */
    public Boolean sendMessage(String phoneNumber) {
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
                if (resultStr.contains("ok")) {
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

    /**
     * @Author: John
     * @Description: 发送短信公共方法
     * @Date:  2019/8/7 0:47
     * @param: phoneNumber
     * @return:
     */
    public Map<String, Object> publicSendMessage(String phoneNumber) {
        Map<String, Object> resultMap = new HashMap<>();
        //生成随机数
        String randomNum = String.valueOf(MathUtil.random(6));
        logger.info("=======发送的短信校验码为："+randomNum);

        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 5);
        //生成5分钟后时间，用户校验是否过期
        String currentTime = sf.format(calendar.getTime());

        //此处执行发送短信验证码方法
        Boolean isSend =sendMessage(phoneNumber);

        logger.info("===========短信发送结果："+isSend);
        if (isSend) {
            //生成MD5值
            String hash = MD5Util.toMD5(ConstantParam.KEY + "@" + currentTime + "@" + randomNum);
            resultMap.put("hash", hash);
            resultMap.put("tamp", currentTime);
        }
        return resultMap;
    }


    /**
     * @Author: John
     * @Description: 检验参数是否正确
     * @Date:  2019/8/7 0:54
     * @param: requestMap
     * @return:
     */
    public ResponseInfo validateNum(Map<String, Object> requestMap) {
        ResponseInfo response = new ResponseInfo();
        //从前端传过来的数据
        String requestHash = requestMap.get("hash").toString();
        logger.info("=============前端收到的requestHash："+requestHash);
        String tamp = requestMap.get("tamp").toString();
        logger.info("=============前端收到的tamp："+tamp);
        String msgNum = requestMap.get("msgNum").toString();
        logger.info("=============前端收到的msgNum："+msgNum);
       //非空校验
        String hashString=requestHash+tamp+msgNum;
        if(StringUtils.isBlank(hashString)){
            response.setCode(CODE_ERROR);
            response.setMessage("verify code can not be empty!");
            return  response;
        }


        String hash = MD5Util.toMD5(ConstantParam.KEY + "@" + tamp + "@" + msgNum);
        logger.info("===========前端的hash码："+hash);
        if (tamp.compareTo(DateUtils.getDateTime()) > 0) {
            if (hash.equalsIgnoreCase(requestHash)){
                logger.warn("===========hash码校验结果："+true);
                //校验成功
                response.setCode(CODE_SUCCESS);
                response.setMessage("message validate success!");
            }else {
                logger.error("===========hash码校验结果："+false);
                //验证码不正确，校验失败
                response.setCode(CODE_ERROR);
                response.setMessage("message validate failed!");
            }
        } else {
            logger.warn("===========hash码校验结果："+"超时啦");
            // 超时
            response.setCode(CODE_ERROR);
            response.setMessage("code is out of time, please send again!");
        }
        return response;
    }




}