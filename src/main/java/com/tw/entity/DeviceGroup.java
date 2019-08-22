package com.tw.entity;

import lombok.Data;

/**
 * @Author: John
 * @Description:设备组类
 * @Date:  2019/8/10 21:20
 * @param: null
 * @return:
 */
@Data
public class DeviceGroup extends BaseEntity {

    private int deviceGroupId;             //自增主键
    private String deviceGroupName;       //组名


    @Override
    public String toString() {
        return "DeviceGroup{" +
                "deviceGroupId=" + deviceGroupId +
                ", deviceGroupName='" + deviceGroupName + '\'' +
                '}';
    }
}