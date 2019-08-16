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


}
