package com.tw.service;

import com.tw.dao.BeatMessageDao;
import com.tw.dao.DeviceDao;
import com.tw.dao.LoginMessageDao;
import com.tw.dao.WarningMessageDao;
import com.tw.entity.BeatMessage;
import com.tw.entity.Device;
import com.tw.entity.LoginMessage;
import com.tw.entity.WarningMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;

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

    LoginMessageDao loginMessageDao;

    DeviceDao deviceDao;

    /**
     * 校验数据库是否与传进来的设备号和验证码一致
     * @return
     */
    public Boolean checkDevice(String serial, String code){

        HashMap map = new HashMap();
        map.put(serial, "1");

        Device device = deviceDao.getDeviceById(map);
        if (device.getDeviceVerifyCode().equals(code)){
            return true;
        }
        else {
            return false;
        }

    }

    /**
     *
     * @return
     */
    public Boolean checkLogin(String serial){

        // TODO 如何验证登陆信息

        LoginMessage loginMessage = loginMessageDao.findBySerial(serial);
        if (loginMessage==null){
            log.error("【验证登陆信息】此设备无登陆信息");
            return false;
        }else{
            // TODO 增加失效时间
            return true;
        }

    }

    /**
     * 登陆报文入库
     * @param loginMessage
     * @return
     */
    public Boolean loginMessageSave(LoginMessage loginMessage){

        // 1.将此条登陆信息写入进数据库
        loginMessageDao.saveLoginMessage(loginMessage);
        log.info("【登陆信息】登陆信息入库成功");

        return true;
    }

    /**
     * 告警信息处理service
     * @return
     */
    public Boolean warningMessageSave(WarningMessage warningMessage){

        // 1.将此条告警信息插入进数据库
        warningMessageDao.saveWarningMessage(warningMessage);
        log.info("【告警信息】告警信息入库成功");

        return true;
    }

    /**
     * 心跳信息处理service
     * @return
     */
    public Boolean beatMessageSave(BeatMessage beatMessage){

        // 1.将此条告警信息插入进数据库
        beatMessageDao.saveBeatMessage(beatMessage);
        HashMap deviceMap = new HashMap<>();
        deviceMap.put(beatMessage.getSerial(), "1");
        Device device = deviceDao.getDeviceById(deviceMap);
        device.setDeviceStatus(beatMessage.getExeStatus());
        device.setIsOnline('1');
        device.setNewBeatTime(beatMessage.getMesDate());
        deviceDao.updateDevice(device);

        log.info("【心跳信息】心跳信息入库成功");

        return true;
    }
}
