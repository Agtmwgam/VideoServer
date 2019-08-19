package com.tw.dto;


import com.tw.entity.Device;
import com.tw.entity.RootDeviceGroup;

import java.util.List;

//root用户设备分组中包含设备分组
public class RootDeviceGroupDTO {

    private RootDeviceGroup rootDeviceGroup;        //设备组
    private List<Device> deviceList;                //设备列表

    public RootDeviceGroup getRootDeviceGroup() {
        return rootDeviceGroup;
    }

    public void setRootDeviceGroup(RootDeviceGroup rootDeviceGroup) {
        this.rootDeviceGroup = rootDeviceGroup;
    }

    public List<Device> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(List<Device> deviceList) {
        this.deviceList = deviceList;
    }
}
