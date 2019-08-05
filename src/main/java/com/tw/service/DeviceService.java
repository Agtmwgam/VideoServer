package com.tw.service;

import com.tw.dao.DeviceDao;
import com.tw.entity.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceService {

    @Autowired
    private DeviceDao deviceDao;

    public int addDevice(Device device) {
        return deviceDao.addDevice(device);
    }
}
