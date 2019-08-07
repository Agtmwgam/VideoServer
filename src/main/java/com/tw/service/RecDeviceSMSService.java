package com.tw.service;

import com.tw.convert.StringArr2BeatMessageConvert;
import com.tw.convert.StringArr2WarningMessageConvert;
import com.tw.dao.BeatMessageDao;
import com.tw.dao.WarningMessageDao;
import com.tw.entity.BeatMessage;
import com.tw.entity.WarningMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author: zhuoshouyi
 * @Description: 告警及心跳信息处理service
 * @Date: 2019/8/7
 * @Param:
 * @return:
 */
@Service
@Slf4j
public class RecDeviceSMSService {

    WarningMessageDao warningMessageDao;

    BeatMessageDao beatMessageDao;

    /**
     * 告警信息处理service
     * @return
     */
    public Boolean warningMessageSave(String message){

        // 1.将 message 转换成 WarningMessage 对象
        String[] mes = message.split("#");
        if (mes.length != 11){
            log.error("【ERROR】告警信息字段数量不正确");
            return false;
        }
        WarningMessage warningMessage = StringArr2WarningMessageConvert.convert(mes);


        // 2.将此条告警信息插入进数据库
        warningMessageDao.saveWarningMessage(warningMessage);
        log.info("【告警信息】告警信息入库成功");

        return true;
    }

    /**
     * 心跳信息处理service
     * @return
     */
    public Boolean beatMessageSave(String message){

        // 1.将 message 转换成 WarningMessage 对象
        String[] mes = message.split("#");
        if (mes.length != 9){
            log.error("【ERROR】心跳信息字段数量不正确");
            return false;
        }
        BeatMessage beatMessage = StringArr2BeatMessageConvert.convert(mes);


        // 2.将此条告警信息插入进数据库
        beatMessageDao.saveBeatMessage(beatMessage);
        log.info("【心跳信息】心跳信息入库成功");

        return true;
    }
}
