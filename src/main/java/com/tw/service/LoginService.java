package com.tw.service;

import com.tw.entity.common.ConstantParam;
import com.tw.util.DateUtils;
import com.tw.util.MD5Util;
import com.tw.util.MathUtil;
import com.tw.util.ResponseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.tw.util.ResponseInfo.CODE_ERROR;
import static com.tw.util.ResponseInfo.CODE_SUCCESS;

@Service
public class LoginService {

    @Autowired
    private MessageService messageService;


    /**
     * @Author: John
     * @Description: 发送短信公共方法
     * @Date:  2019/8/7 0:47
     * @param: phoneNumber
     * @return:
     */
    public Map<String, Object> sendMessage(String phoneNumber) {
        Map<String, Object> resultMap = new HashMap<>();
        //生成随机数
        String randomNum = String.valueOf(MathUtil.random(6));
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 5);
        //生成5分钟后时间，用户校验是否过期
        String currentTime = sf.format(calendar.getTime());

        //此处执行发送短信验证码方法
        Boolean isSend = messageService.sendMessage(phoneNumber);
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
        String tamp = requestMap.get("tamp").toString();
        String msgNum = requestMap.get("msgNum").toString();
        String hash = MD5Util.toMD5(ConstantParam.KEY + "@" + tamp + "@" + msgNum);
        if (tamp.compareTo(DateUtils.getDateTime()) > 0) {
            if (hash.equalsIgnoreCase(requestHash)){
                //校验成功
                response.setCode(CODE_SUCCESS);
                response.setMessage("message validate success!");
            }else {
                //验证码不正确，校验失败
                response.setCode(CODE_ERROR);
                response.setMessage("message validate failed!");
            }
        } else {
            // 超时
            response.setCode(CODE_ERROR);
            response.setMessage("code is out of time, please send again!");
        }
        return response;
    }
}
