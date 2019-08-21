package com.tw.entity;

//root用户的设备分组
public class RootDeviceGroup extends BaseEntity {

    private int id;                     //主键
    private int deviceId;               //设备id
    private int rootDeviceGroupId;      //分组id
    private String rootDeviceGroupName; //分组组名

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public int getRootDeviceGroupId() {
        return rootDeviceGroupId;
    }

    public void setRootDeviceGroupId(int rootDeviceGroupId) {
        this.rootDeviceGroupId = rootDeviceGroupId;
    }

    public String getRootDeviceGroupName() {
        return rootDeviceGroupName;
    }

    public void setRootDeviceGroupName(String rootDeviceGroupName) {
        this.rootDeviceGroupName = rootDeviceGroupName;
    }


    @Override
    public String toString() {
        return "RootDeviceGroup{" +
                "id=" + id +
                ", deviceId=" + deviceId +
                ", rootDeviceGroupId=" + rootDeviceGroupId +
                ", rootDeviceGroupName='" + rootDeviceGroupName + '\'' +
                '}';
    }
}