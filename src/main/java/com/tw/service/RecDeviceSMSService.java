package com.tw.service;

import com.tw.dao.*;
import com.tw.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    WarningMessageDao warningMessageDao;

    @Autowired
    BeatMessageDao beatMessageDao;

    @Autowired
    LoginMessageDao loginMessageDao;

    @Autowired
    DeviceDao deviceDao;

    @Autowired
    DevGroupDao devGroupDao;

    /**
     * 校验数据库是否与传进来的设备号和验证码一致
     * @return
     */
    public Boolean checkDevice(String serial, String code){

        // 获取此设备的信息
        Device d1 = new Device();
        d1.setSerial(serial);
        d1.setIsValid('1');
        List<Device> deviceList = deviceDao.getDeviceByCodition(d1);

        if (deviceList==null || deviceList.size()==0){
            log.error("【登陆校验】此设备信息未录入系统");
            return false;
        }else if (deviceList.size()!=1){
            log.error("【登陆校验】数据库存储错误,"+serial+"设备存在重复的有效数据");
            return false;
        }

        // 进行设备的验证码校验
        if (deviceList.get(0).getDeviceVerifyCode().equals(code)){
            log.info("【登陆校验】设备信息正确");
            return true;
        }
        else {
            log.error("【登陆校验】设备信息与数据库不符");
            return false;
        }

    }

    /**
     * 查询此设备是否有登陆信息
     * @return
     */
    public Boolean checkLogin(String serial){

        // 1.查看有无登陆信息
        LoginMessage loginMessage = loginMessageDao.findBySerial(serial);
        if (loginMessage==null){
            log.error("【验证登陆信息】此设备无登陆信息");
            return false;
        }else{
            // 2.校验登陆信息是否可用 isValid==1
            if (loginMessage.getIsValid()=='0'){
                // 登陆信息不可用
                log.error("【验证登陆信息】此设备登陆信息已失效,需重新登陆");
                return false;
            }else {
                // 登陆信息可用
                log.info("【验证登陆信息】此设备登陆通过");
                return true;
            }
        }
    }

    /**
     * 获取设备分组名和设备名
     * @return
     */
    public List<String> findGroupNameAndDeviceName(String serial){

        // 获取 deviceName 与 groupName
        Device device = deviceDao.getDeviceBySerial(serial);
        DevGroup devGroup = devGroupDao.getDevGroupBySerial(serial);

        List<String> groupNameAndDeviceNameList = new ArrayList<>();
        if (device!=null){
            groupNameAndDeviceNameList.add(device.getDeviceName());
        }
        if (devGroup!=null){
            groupNameAndDeviceNameList.add(devGroup.getGroupName());
        }

        return groupNameAndDeviceNameList;
    }

    /**
     * 登陆报文入库
     * @param loginMessage
     * @return
     */
    public Boolean loginMessageSave(LoginMessage loginMessage){

        // 查询此设备有无登陆信息
        LoginMessage message = loginMessageDao.findBySerial(loginMessage.getSerial());
        if (message==null){
            // 数据不存在,将此条登陆信息写入进数据库
            loginMessageDao.saveLoginMessage(loginMessage);
        }else {
            // 数据存在,修改
            loginMessageDao.modifyLogin(loginMessage);
            // 将 isValid 改为 1
            LoginMessage lm1 = new LoginMessage();
            lm1.setSerial(loginMessage.getSerial());
            lm1.setIsValid('1');
            loginMessageDao.updateIsValidBySerial(lm1);
        }

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

        // 1.查询数据库是否有此设备的心跳消息
        BeatMessage message = beatMessageDao.findBySerial(beatMessage.getSerial());
        if (message==null){
            // 如果不存在就插入
            beatMessageDao.saveBeatMessage(beatMessage);
        }else {
            // 如果存在就修改
            beatMessageDao.modifyBeat(beatMessage);
        }

        // TODO 心跳信息插入后是否立刻更改 device 信息
//        Device device = deviceDao.getDeviceBySerial(beatMessage.getSerial());
//        device.setDeviceStatus(beatMessage.getExeStatus());
//        device.setIsOnline('1');
//        device.setNewBeatTime(beatMessage.getMesDate());
//        deviceDao.updateDevice(device);

        log.info("【心跳信息】心跳信息入库成功");

        return true;
    }
}
