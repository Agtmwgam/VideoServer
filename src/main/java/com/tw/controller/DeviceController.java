package com.tw.controller;

import com.tw.dto.UserRoleDTO;
import com.tw.entity.Device;
import com.tw.service.DeviceService;
import com.tw.util.ResponseInfo;
import com.tw.util.UserAuthentication;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    /**
     * @Author: John
     * @Description: 插入device设备信息
     * @Date:  2019/8/5 22:39
     * @param: device
     * @return:
     */
    @PostMapping("/addDevice")
    public ResponseInfo addDevice(@RequestBody Device device,
                                  HttpServletRequest httpServletRequest) {

        // 1.校验用户身份
        UserRoleDTO userRoleDTO = UserAuthentication.authentication(httpServletRequest);




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


    /**
     * @Author: John
     * @Description: 删除device设备信息
     * @Date:  2019/8/5 22:40
     * @param: deviceId
     * @return:
     */
    @PostMapping("/deviceId")
    public ResponseInfo deleteDevice(@RequestParam(value = "deviceId") int deviceId) {
        ResponseInfo response = new ResponseInfo();
        int isDel = deviceService.deleteDevice(deviceId);
        System.out.println("==========删除的结果为："+isDel);

        if (isDel == 1) {
            response.setCode(ResponseInfo.CODE_SUCCESS);
            response.setMessage("delete device success!");
        } else {
            response.setCode(ResponseInfo.CODE_ERROR);
            response.setMessage("delete device failed!");
        }
        return response;
    }


    /**
     * @Author: John
     * @Description: 更新device
     * @Date:  2019/8/5 22:56
     * @param: device json对象
     * @return:
     */
    @PostMapping("/updateDevice")
    public ResponseInfo updateDevice(@RequestBody Device device) {
        ResponseInfo response = new ResponseInfo();
        Integer isUpdate = deviceService.updateDevice(device);
        System.out.println("=======update的结果为："+isUpdate);
        if (isUpdate == 1) {
            response.setCode(ResponseInfo.CODE_SUCCESS);
            response.setMessage("update device success!");
        } else {
            response.setCode(ResponseInfo.CODE_ERROR);
            response.setMessage("update device failed!");
        }
        return response;
    }


    /**
     * @Author: John
     * @Description: 根据deviceId获得device对象
     * @Date:  2019/8/5 12:51
     * @param: deviceId
     * @return:
     */
    @GetMapping("/getDeviceByDeviceId")
    public ResponseInfo getDeviceByDeviceId(@RequestParam(value = "deviceId") int deviceId) {
        List<Device> devices = new ArrayList<>();
        Map<String, Object> resultMap = new HashMap<>();
        ResponseInfo response = new ResponseInfo();
        Device device = deviceService.getDeviceById(deviceId);
        if (device != null) {
            resultMap.put("totle", 1);
            resultMap.put("device", device);
            devices.add(device);
            response.setCode(ResponseInfo.CODE_SUCCESS);
            response.setMessage("getDeviceByDeviceId success!");
            response.setData(resultMap);
            System.out.println("=====device.toString()"+device.toString());
        } else {
            resultMap.put("totle", 1);
            response.setCode(ResponseInfo.CODE_ERROR);
            response.setMessage("getDeviceByDeviceId failed!");
            response.setData(resultMap);
        }
        return response;
    }


    /**
     * @Author: John
     * @Description: 根据条件查询设备列表
     * @Date:  2019/8/6 0:43
     * @param: device
     * @return:
     */
    @GetMapping("/getDeviceByCodition")
    public ResponseInfo getDeviceByCodition(Device device) {
        ResponseInfo response = new ResponseInfo();
        Map<String, Object> resultMap = new HashMap<>();
        List<Device> devices = deviceService.getDeviceByCodition(device);
        for (Device device1 : devices) {
            System.out.println("===:"+device1.toString());
        }
        if (devices != null) {
            int totle = devices.size();
            resultMap.put("total", totle);
            resultMap.put("list", devices);
            response.setCode(ResponseInfo.CODE_SUCCESS);
            response.setMessage("getDeviceByCondition success!");
        } else {
            resultMap.put("totle", 0);
            response.setCode(ResponseInfo.CODE_ERROR);
            response.setMessage("getDeviceByCondition failed!");
            response.setData(resultMap);
        }
        return response;
    }
}