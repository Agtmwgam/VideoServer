package com.tw.timer;

import com.tw.dao.BeatMessageDao;
import com.tw.dao.DeviceDao;
import com.tw.entity.BeatMessage;
import com.tw.entity.Device;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: zhuoshouyi
 * @Description:
 * @Date: 2019/8/11
 * @Param:
 * @return:
 */
@Component
@Slf4j
public class BeatTimer implements InitializingBean {

    @Autowired
    BeatMessageDao beatMessageDao;

    @Autowired
    DeviceDao deviceDao;

    // 全局统一时间格式化格式
    SimpleDateFormat FMT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 每分钟执行一次
     */
    @Scheduled(cron="0 */1 * * * ?")
    @Transactional
    public void BeatTimerTest(){

        Date date = new Date();
        log.info("【检查心跳】当前时间: " + FMT.format(date));

        // 获取所有 isVaild 为1的设备的 seial
        Device deviceSelect = new Device();
        deviceSelect.setIsValid('1');
        List<Device> deviceList = deviceDao.getDeviceByCodition(deviceSelect);
        List<String> serialList = new ArrayList<>();
        deviceList.stream().forEach(e -> serialList.add(e.getSerial()));
        log.info("【检查心跳】获取所有的device serial: " + serialList.toString());

        // 从心跳信息中获取最近一条的心跳信息,与现在的时间作比较,如果大于5分钟或未查到心跳信息,即给此 device 的 isOnline 置为0
        for (Device devices : deviceList){

            log.info("【检查心跳】正在检查 " + devices.getSerial() + " 的心跳");
            // 获取最近一条的心跳信息
            BeatMessage beatMessage = beatMessageDao.findBySerial(devices.getSerial());

            // 1.如果没有查找到此设备的心跳信息,就将此设备的 isOnline 置为 0
            if (beatMessage==null){
                devices.setIsOnline('0');
                deviceDao.updateDevice(devices);
                log.info("【检查心跳】将 " + devices.getSerial() + " 的状态置为0");
            }else {
                // 2.查询此条心跳信息的设备状态,如果为0直接置0,如果为1再进行后续判断
                if (beatMessage.getExeStatus()=='0'){
                    devices.setIsOnline('0');
                    deviceDao.updateDevice(devices);
                    log.info("【检查心跳】将 " + devices.getSerial() + " 的状态置为0");
                }else {
                    // 3.如果最近一条心跳信息的分钟差值大于5,就将 device isOnline 置为 0
                    long difMin = (date.getTime() - beatMessage.getMesDate().getTime()) / (1000 * 60);
                    if (difMin>5){
                        devices.setIsOnline('0');
                        deviceDao.updateDevice(devices);
                        log.info("【检查心跳】将 " + devices.getSerial() + " 的状态置为0");
                    }else if (difMin<=5){
                        devices.setIsOnline('1');
                        deviceDao.updateDevice(devices);
                        log.info("【检查心跳】将 " + devices.getSerial() + " 的状态置为1");
                    }
                }
            }
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.BeatTimerTest();
    }
}
