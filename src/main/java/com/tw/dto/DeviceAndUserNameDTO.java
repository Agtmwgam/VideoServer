package com.tw.dto;

import com.tw.entity.Device;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class DeviceAndUserNameDTO {

    private int deviceId;                 //设备的id
    private String deviceName;            //设备名称
    private String serial;                //序列号
    private String deviceVerifyCode;       //验证码
    private String deviceType;             //设备型号
    private String softVersion;            //软件版本
    private String productDate;            //设备生产日期
    private char deviceStatus;              //设备状态
    private char isOnline;                  //设备是否在线
    private String ipAddress;               //消息来自哪个ip
    private Date newBeatTime;               //最新接到一个心跳的时间
    private Date oldBeatTime;               //上一次接到心跳的时间
    private char isValid;                   //是否可用
    private String nickName;                //用户昵称
}