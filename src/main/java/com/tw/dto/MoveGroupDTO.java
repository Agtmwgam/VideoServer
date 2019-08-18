package com.tw.dto;

//移动分组时使用到的类
public class MoveGroupDTO {

    private int deviceId;       //设备id
    private int groupId;        //组id
    private int newGroupId;     //新的组id

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getNewGroupId() {
        return newGroupId;
    }

    public void setNewGroupId(int newGroupId) {
        this.newGroupId = newGroupId;
    }
}
