package com.tw.service;

import com.tw.dao.DeviceGroupRelateDao;
import com.tw.entity.DeviceGroupRelate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceGroupRelateService {

    @Autowired
    private DeviceGroupRelateDao deviceGroupRelateDao;

    //添加设备和组的关联关系
    public int addDeviceGroupRelate(DeviceGroupRelate deviceGroupRelate) {
        return deviceGroupRelateDao.addDeviceGroupRelate(deviceGroupRelate);
    }


    //根据条件删除对象
    public int deleteByDeviceGroupRelate(DeviceGroupRelate deviceGroupRelate) {
        return deviceGroupRelateDao.deleteByDeviceGroupRelate(deviceGroupRelate);
    }


    //更新关联关系
    public int updateDeviceGroupRelateBy(DeviceGroupRelate deviceGroupRelate) {
        return deviceGroupRelateDao.updateDeviceGroupRelateBy(deviceGroupRelate);
    }


    //根据组id获得对象集合
    public List<DeviceGroupRelate> getDeviceGroupByGroupId(int groupId) {
        return deviceGroupRelateDao.getDeviceGroupByGroupId(groupId);
    }


    //根据条件获得
    public List<DeviceGroupRelate> getDeviceGroupRelateByCondition(DeviceGroupRelate deviceGroupRelate) {
        return deviceGroupRelateDao.getDeviceGroupRelateByCondition(deviceGroupRelate);
    }

    //根据设备组ID或者设备ID获得设备组与设备的唯一关系（每个设备只能属于同一用户的设备组下）
    public DeviceGroupRelate getDeviceGroupRelate(DeviceGroupRelate deviceGroupRelate) {
        return deviceGroupRelateDao.getDeviceGroupRelate(deviceGroupRelate);
    }


    //删除关联关系
    public int deleteDeviceGroupRelate(DeviceGroupRelate deviceGroupRelate) {
        return deviceGroupRelateDao.deleteDeviceGroupRelate(deviceGroupRelate);
    }


    //用设备id判断是否已经存在关联关系
    public Boolean isLinkGroup(int deviceId) {
        int linkCount = deviceGroupRelateDao.linkGroupCount(deviceId);
        if (linkCount > 0) {
            return true;
        } else {
            return false;
        }
    }
}
