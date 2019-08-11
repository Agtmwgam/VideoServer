package com.tw.controller;

import com.tw.dto.UserRoleDTO;
import com.tw.entity.DevGroup;
import com.tw.entity.Device;
import com.tw.service.DevGroupService;
import com.tw.service.DeviceService;
import com.tw.util.ResponseInfo;
import com.tw.util.UserAuthentication;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
@RestController
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private DevGroupService devGroupService;

    //日志
    private static Logger logger = Logger.getLogger(DeviceController.class);


    /**
     * @Author: John
     * @Description: 插入device设备信息
     * @Date:  2019/8/5 22:39
     * @param: device
     * @return:
     */
    @PostMapping("/addDevice")
    public ResponseInfo addDevice(@RequestBody Device device, HttpServletRequest httpServletRequest) {

        ResponseInfo responseInfo = new ResponseInfo();

        // 1.校验用户身份
        UserRoleDTO userRoleDTO = UserAuthentication.authentication(httpServletRequest);

        //查询数据库中是否已经存在该设备，感觉设备号和验证码检测
        List<Device> deviceList = deviceService.getDeviceByCodition(device);
        if (deviceList != null && deviceList.size() > 0) {
            logger.warn("设备 " + device.getSerial() +" 已经存在！");
            responseInfo.setCode(ResponseInfo.CODE_ERROR);
            responseInfo.setMessage("device already exists！");
            return responseInfo;
        }


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


    /**
     * @Author: John
     * @Description: 客户端添加分组接口
     * @Date:  2019/8/10 21:52
     * @param: groupName
     * @param: httpServletRequest
     * @return:
     */
    @PostMapping("/addGroup")
    public ResponseInfo addGroup(@RequestParam(value="groupName", required = true) String groupName, HttpServletRequest httpServletRequest) {
        ResponseInfo response = new ResponseInfo();
        // 1.校验用户身份
        UserRoleDTO userRoleDTO = UserAuthentication.authentication(httpServletRequest);
        String phoneNumber = userRoleDTO.getPhoneNumber();
        //如果已经登录成功，就可以添加，否则提示登录
        if (StringUtils.isNotBlank(phoneNumber)) {
            DevGroup devGroup = new DevGroup();
            devGroup.setGroupName(groupName);
            int isAdd = devGroupService.addDevGroup(devGroup);
            if (isAdd == 1) {
                response.setCode(ResponseInfo.CODE_SUCCESS);
                response.setMessage("add devGroup success!");
            } else {
                response.setCode(ResponseInfo.CODE_ERROR);
                response.setMessage("add devGroup failed!");
            }
            return response;
        } else {
            response.setCode(ResponseInfo.CODE_ERROR);
            response.setMessage("please login first!");
            return response;
        }
    }


    /**
     * @Author: John
     * @Description: 修改设备分组名称
     * @Date:  2019/8/10 21:56
     * @param: devGroup
     * @return:
     */
    @PostMapping("/modifyDeviceGroupName")
    public ResponseInfo modifyDeviceGroupName(@RequestBody DevGroup devGroup) {
        ResponseInfo response = new ResponseInfo();
        int isUpdate = devGroupService.updateDevGroup(devGroup);
        if (isUpdate == 1) {
            response.setCode(ResponseInfo.CODE_SUCCESS);
            response.setMessage("modify devGroup success!");
        } else {
            response.setCode(ResponseInfo.CODE_ERROR);
            response.setMessage("modify devGroup failed!");
        }
        return response;
    }


    /**
     * @Author: John
     * @Description: 删除设备分组接口
     * @Date:  2019/8/10 21:59
     * @param: groupId
     * @return:
     */
    @PostMapping("/deleteDeviceGroup")
    public ResponseInfo deleteDeviceGroup(@RequestParam(value = "groupId") int groupId) {
        ResponseInfo response = new ResponseInfo();
        int isDelete = devGroupService.deleteDevGroupById(groupId);
        if (isDelete == 1) {
            response.setCode(ResponseInfo.CODE_SUCCESS);
            response.setMessage("delete devGroup success!");
        } else {
            response.setCode(ResponseInfo.CODE_ERROR);
            response.setMessage("delete devGroup failed!");
        }
        return response;
    }
}