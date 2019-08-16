package com.tw.dto;

import com.tw.entity.Device;

import java.util.List;

//用户下面嵌套设备列表
public class UserDeviceDTO {

    private Integer userID;         //用户ID
    private String nickName;        //用户昵称
    private String password;        //账户密码
    private String phoneNumber;     //手机号
    List<Device> deviceList;        //设备列表

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Device> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(List<Device> deviceList) {
        this.deviceList = deviceList;
    }
}