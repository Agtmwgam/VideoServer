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

    public List<DeviceGroupRelate> getDeviceGroupByGroupId(int groupId) {
        return deviceGroupRelateDao.getDeviceGroupByGroupId(groupId);
    }


    //根据条件获得
    public List<DeviceGroupRelate> getDeviceGroupRelateByCondition(DeviceGroupRelate deviceGroupRelate) {
        return deviceGroupRelateDao.getDeviceGroupRelateByCondition(deviceGroupRelate);
    }

    public int addDeviceGroupRelate(DeviceGroupRelate deviceGroupRelate) {
        return deviceGroupRelateDao.addDeviceGroupRelate(deviceGroupRelate);
    }
}
