package com.tw.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author liutianwen
 * @Description: 设备详情实体类
 * @date 2019年8月3日
 */
@Data
public class Device extends BaseEntity {

    private int deviceId;                 //设备的id
    private String deviceName;            //设备名称
    private String serial;                //序列号
    private String deviceVerifyCode;       //验证码
    private String deviceType;             //设备型号
    private String softVersion;            //软件版本
    private String productDate;            //设备生产日期
    private char deviceStatus;            //设备状态
    private char isOnline;                //设备是否在线
    private String ipAddress;            //消息来自哪个ip
    private Date newBeatTime;            //最新接到一个心跳的时间
    private Date oldBeatTime;            //上一次接到心跳的时间

    public Device() {
        super();
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getDeviceVerifyCode() {
        return deviceVerifyCode;
    }

    public void setDeviceVerifyCode(String deviceVerifyCode) {
        this.deviceVerifyCode = deviceVerifyCode;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getSoftVersion() {
        return softVersion;
    }

    public void setSoftVersion(String softVersion) {
        this.softVersion = softVersion;
    }

    public String getProductDate() {
        return productDate;
    }

    public void setProductDate(String productDate) {
        this.productDate = productDate;
    }

    public char getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(char deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public char getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(char isOnline) {
        this.isOnline = isOnline;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Date getNewBeatTime() {
        return newBeatTime;
    }

    public void setNewBeatTime(Date newBeatTime) {
        this.newBeatTime = newBeatTime;
    }

    public Date getOldBeatTime() {
        return oldBeatTime;
    }

    public void setOldBeatTime(Date oldBeatTime) {
        this.oldBeatTime = oldBeatTime;
    }
}

