package com.tw.service;

import com.tw.dao.DeviceDao;
import com.tw.entity.Device;
import com.tw.entity.common.ConstantParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DeviceService {

    @Autowired
    private DeviceDao deviceDao;

    public int addDevice(Device device) {
        return deviceDao.addDevice(device);
    }

    public Integer deleteDevice(int deviceId) {
        Map<String, Object> param = new HashMap<>();
        param.put("deviceId", deviceId);
        param.put("isValid", ConstantParam.IS_VALID_NO);
        return deviceDao.deleteDevice(param);
    }


    public Integer updateDevice(Device device) {
        return deviceDao.updateDevice(device);
    }

    public Device getDeviceById(int deviceId) {
        Map<String, Object> param = new HashMap<>();
        param.put("deviceId", deviceId);
        param.put("isValid", ConstantParam.IS_VALID_YES);
        return deviceDao.getDeviceById(param);
    }

    public List<Device> getDeviceByCodition(Device device) {
        return deviceDao.getDeviceByCodition(device);
    }
}
