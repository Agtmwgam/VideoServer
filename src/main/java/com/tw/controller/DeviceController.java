package com.tw.controller;

import com.tw.entity.Device;
import com.tw.service.DeviceService;
import com.tw.util.ResponseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: John
 * @Description: 设备相关的操作
 * @Date:  2019/8/5 12:49
 * @param: null
 * @return:
 */
@Controller
public class DeviceController {

    @Autowired
    private DeviceService deviceService;


    @PostMapping("/addDevice")
    public ResponseInfo addDevice(@RequestBody Device device) {
        ResponseInfo responseInfo = new ResponseInfo();
        int isAdd = deviceService.addDevice(device);
        if (isAdd == 1) {
            responseInfo.setCode(ResponseInfo.CODE_SUCCESS);
            responseInfo.setMessage("add device success!");
        } else {
            responseInfo.setCode(ResponseInfo.CODE_ERROR);
            responseInfo.setMessage("add device failed!");
        }
        return  responseInfo;
    }

    public int deleteDevice(int deviceId) {
        return 0;
    }


    public int updateDevice(Device device) {
        return 0;
    }


    /**
     * @Author: John
     * @Description: 根据deviceId获得device对象
     * @Date:  2019/8/5 12:51
     * @param: deviceId
     * @return:
     */
    public Device getDeviceByDeviceId(int deviceId) {
        Device device = new Device();
        return device;


    }


    public List<Device> getDeviceByCodition() {
        List<Device> devices = new ArrayList<>();
        return devices;
    }
}