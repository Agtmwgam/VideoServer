package com.tw.entity;

/**
 * @Author: John
 * @Description: 用户管理设备分组
 * @Date:  2019/8/14 1:37
 * @return:
 */
public class UserDeviceGroupRelate extends BaseEntity{

    private int id;             //自增主键
    private int userId;         //用户id
    private int deviceGroupId;   //设备分组id

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getDeviceGroupId() {
        return deviceGroupId;
    }

    public void setDeviceGroupId(int deviceGroupId) {
        this.deviceGroupId = deviceGroupId;
    }

    @Override
    public String toString() {
        return "UserDeviceGroupRelate{" +
                "id=" + id +
                ", userId=" + userId +
                ", deviceGroupId=" + deviceGroupId +
                '}';
    }
}