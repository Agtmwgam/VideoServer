package com.tw.service;

import com.tw.dao.DeviceDao;
import com.tw.entity.Device;
import com.tw.entity.VUser;
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


    public List<Device> getDeviceByCoditionPage(Device device, int pageNo, int pageSize) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("start", (pageNo-1) * pageSize);
        param.put("pageSize", pageSize);
        param.put("deviceId", device.getDeviceId());
        param.put("deviceName", device.getDeviceName());
        param.put("serial", device.getSerial());
        param.put("deviceVerifyCode", device.getDeviceVerifyCode());
        param.put("deviceType", device.getDeviceType());
        param.put("softVersion", device.getSoftVersion());
        param.put("productDate", device.getProductDate());
        param.put("deviceStatus", device.getDeviceStatus());
        param.put("isOnline", device.getIsOnline());
        param.put("ipAddress", device.getIpAddress());
        param.put("isValid", device.getIsValid());
        return deviceDao.getDeviceByCoditionPage(param);
    }


    public List<Device> getDeviceByCodition(Device device) {
        return deviceDao.getDeviceByCodition(device);
    }


    public List<Device> getDeviceLikeCondition(Device device, int pageNo, int pageSize) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("start", (pageNo-1) * pageSize);
        param.put("pageSize", pageSize);
        param.put("serial", device.getSerial());
        param.put("deviceType", device.getDeviceType());
        param.put("produceDate", device.getProductDate());
        param.put("isValid ", '1');
        return deviceDao.getDeviceLikeCondition(param);
    }


    /**
     * @author liutianwen
     * @desc  根据传入vuser信息查看设备号
     * @param VUser
     * @return
     */
    public List<String> getDeviceByUser(VUser user) {
        return deviceDao.getDeviceByUser(user);
    }


}
