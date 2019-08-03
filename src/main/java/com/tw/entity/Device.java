package com.tw.entity;

import lombok.Data;

/**
 * @author liutianwen
 * @Description: 设备详情实体类
 * @date 2019年8月3日
 */
@Data
public class Device extends BaseEntity{
    //设备ID
    private String deviceID;
    //    设备名称
    private String deviceName;
    //    是否在线（1在线0不在线）
    private String isOnline;
    //    设备地址
    private String address;

    public Device() {
        super();
    }

    public Device(String deviceID, String deviceName, String isOnline, String address) {
        super();
        this.deviceID = deviceID;
        this.deviceName = deviceName;
        this.isOnline = isOnline;
        this.address = address;
    }
}
