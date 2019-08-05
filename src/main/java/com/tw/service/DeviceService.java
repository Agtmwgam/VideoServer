package com.tw.service;

import com.tw.dao.DeviceDao;
import com.tw.entity.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DeviceService {

    @Autowired
    private DeviceDao deviceDao;

    public int addDevice(Device device) {
        Map<String, Object> param =  new HashMap<>();
        param.put("serial", device.getSerial());
        param.put("deviceVerifyCode", device.getDeviceVerifyCode());
        param.put("deviceType", device.getDeviceType());
        param.put("softVersion", device.getSoftVersion());
        param.put("productDate", device.getProductDate());
        return deviceDao.addDevice(device);
    }
}
