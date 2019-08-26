package com.tw.controller;

import com.tw.dto.UserDeviceDTO;
import com.tw.entity.Device;
import com.tw.entity.UserDeviceRelate;
import com.tw.entity.VUser;
import com.tw.entity.common.ConstantParam;
import com.tw.service.DeviceService;
import com.tw.service.UserDeviceRelateService;
import com.tw.service.VUserService;
import com.tw.util.ResponseInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tw.util.ResponseInfo.CODE_ERROR;
import static com.tw.util.ResponseInfo.CODE_SUCCESS;

/**
 * @Classname RegisterController
 * @Description 用户管理
 * @Date 2019/8/5 22:21
 * @Created by liutianwen
 */
@RestController
public class UserManagementController {

    @Autowired
    private VUserService userService;

    @Autowired
    private UserDeviceRelateService userDeviceRelateService;

    @Autowired
    private DeviceService deviceService;

    /**
     * @param user pageNo pageSize
     * @return
     * @Date 2019/8/5 22:21
     * @Created liutianwen
     * @Description 用户管理 根据用户手机，模糊搜索用户信息和相应绑定的设备号
     */
    @PostMapping (value = "/getUserInfo")
    public ResponseInfo getUserInfo(@RequestBody VUser user, @RequestParam(value = "pageNo") int pageNo, @RequestParam(value = "pageSize") int pageSize) {
        ResponseInfo response = new ResponseInfo();
        response.setPageNo(pageNo);
        response.setPageSize(pageSize);

        Map<String, Object> resultMap = new HashMap<>();
        int totle = userService.getTotleOfUserAndDevice(user);
        //模糊查询用户（分页）
        List<UserDeviceDTO> uAdList = userService.fuzzyQueryUserAndDeviceList(user, pageNo, pageSize);
        if (uAdList != null) {
            resultMap.put("total", uAdList.size());
            resultMap.put("list", uAdList);
            response.setCode(ResponseInfo.CODE_SUCCESS);
            response.setTotal(totle);
            response.setData(resultMap);
        } else {
            resultMap.put("totle", 0);
            response.setCode(ResponseInfo.CODE_ERROR);
            response.setMessage("getUserInfo failed!");
            response.setData(resultMap);
        }
        response.setCode(CODE_SUCCESS);
        response.setData(uAdList);

        return response;
    }

    /**
     * @param requestMap phoneNumber  serial  deviceVerifyCode
     * @return
     * @Date 2019/8/5 22:21
     * @Created liutianwen
     * @Description 增加用户设备
     */
    @PostMapping(value = "/addUserDevice")
    public ResponseInfo addUserDevice(@RequestBody Map<String, Object> requestMap) {
        ResponseInfo response = new ResponseInfo();
//       把参数提取出来
        String phoneNumber = requestMap.get("phoneNumber").toString();
        String serial = requestMap.get("serial").toString();
        String deviceVerifyCode = requestMap.get("deviceVerifyCode").toString();
//       检查phoneNumber  serial  deviceVerifyCode是否为空
        response = checkPhoneAndSerialAndDeviceVerifyCode(phoneNumber, serial, deviceVerifyCode);
        if (response.getCode() == CODE_ERROR) {
            return response;
        }

        VUser user = new VUser();
        Device device = new Device();
        //把参数放入实体类
        user.setPhoneNumber(phoneNumber);
        device.setSerial(serial);
        device.setDeviceVerifyCode(deviceVerifyCode);


        user = userService.queryUser(user);
//      根据条件查询出DeviceId
        List<Device> deviceList = deviceService.getDeviceByCodition(device);
        if (deviceList == null) {
            response.setMessage("The device is not exist!");
            response.setCode(CODE_ERROR);
            return response;
        }
        for (Device deviceTemp : deviceList) {
            device.setDeviceId(deviceTemp.getDeviceId());
        }
//       通过用户ID和设备ID增加用户设备
        UserDeviceRelate userDeviceRelate = new UserDeviceRelate();
        userDeviceRelate.setUserId(user.getUserID());
        userDeviceRelate.setDeviceId(device.getDeviceId());
        deviceService.addUserDevice(userDeviceRelate);

        response.setCode(CODE_SUCCESS);
        response.setMessage("add user's device successfully!");
        return response;
    }


    /**
     * @param requestMap phoneNumber serial
     * @return
     * @Date 2019/8/5 22:21
     * @Created liutianwen
     * @Description 用户管理  逻辑删除用户设备
     */
    @PostMapping(value = "/delUserInfo")
    public ResponseInfo findPassword(@RequestBody Map<String, Object> requestMap) {
        ResponseInfo response = new ResponseInfo();
        // 获取参数
        int userId = (int) requestMap.get("userId");
        int deviceId = (int) requestMap.get("deviceId");

        // 非空校验
        if (userId != 0 && deviceId != 0) {
            //直接将userId和deviceId作为参数，传入删除方法，逻辑删除用户设备，并返回删除个数
            int delNum = userDeviceRelateService.delUserDevice(userId, deviceId);
            if (delNum > 0) {
                response.setCode(CODE_SUCCESS);
                response.setMessage(delNum + "user's  devices were deleted successful!");
            } else {
                response.setCode(CODE_ERROR);
                response.setMessage("delete user's device failed!");
                return response;
            }
        } else {
            response.setCode(CODE_ERROR);
            response.setMessage("delete device info can't be null!");
        }
        return response;
    }

    /**
     * @return
     * @Date 2019/8/5 22:21
     * @Created liutianwen
     * @Description 校验用户管理中输入的手机号是否正确
     */
    public ResponseInfo checkUserInfo(Map<String, Object> requestMap) {
        ResponseInfo response = new ResponseInfo();
        //用户注册手机号
        String phoneNumber = requestMap.get("phoneNumber").toString();
        //手机号码是否正规范
        Boolean isRightPhone = false;

        //手机号码校验
//        手机号是否为空
        if (StringUtils.isBlank(phoneNumber)) {
            response.setCode(CODE_ERROR);
            response.setMessage("The phoneNumber can not be empty!");
            return response;
        }
        //手机号码校验是否规范
        isRightPhone = phoneNumber.matches(ConstantParam.VERIFYPHONENUMBER);
        if (!isRightPhone) {
            response.setCode(CODE_ERROR);
            response.setMessage("The phoneNumber is not correct!");
            return response;
        }

        //校验该用户是否已注册
        VUser user2 = new VUser();
        user2.setPhoneNumber(phoneNumber);
        VUser returnUser = userService.queryUser(user2);
        if (StringUtils.isBlank(returnUser.getPhoneNumber())) {
            response.setCode(CODE_ERROR);
            response.setMessage("The user is not exist！");
            return response;
        }

        response.setCode(CODE_SUCCESS);
        return response;
    }

    /**
     * @return
     * @Date 2019/8/12 22:21
     * @Created liutianwen
     * @Description 校验用户管理中输入的手机号是否正确
     */
    public ResponseInfo checkPhoneAndSerial(String phoneNumber, String serial) {
        ResponseInfo response = new ResponseInfo();
        if (StringUtils.isBlank(phoneNumber)) {
            response.setCode("9999");
            response.setMessage("WEB front phoneNumber can not be empty!");
            return response;
        }
        if (StringUtils.isBlank(serial)) {
            response.setCode("9999");
            response.setMessage("WEB front serial can not be empty!");
            return response;
        }

        return response;
    }

    /**
     * @return
     * @Date 2019/8/12 22:21
     * @Created liutianwen
     * @Description 校验用户管理中前端返回的参数是否为空
     */
    public ResponseInfo checkPhoneAndSerialAndDeviceVerifyCode(String phoneNumber, String serial, String deviceVerifyCode) {
        ResponseInfo response = new ResponseInfo();
        if (StringUtils.isBlank(phoneNumber)) {
            response.setCode("9999");
            response.setMessage("WEB front phoneNumber can not be empty!");
            return response;
        }
        if (StringUtils.isBlank(serial)) {
            response.setCode("9999");
            response.setMessage("WEB front serial can not be empty!");
            return response;
        }
        if (StringUtils.isBlank(deviceVerifyCode)) {
            response.setCode("9999");
            response.setMessage("WEB front deviceVerifyCode can not be empty!");
            return response;
        }

        return response;
    }
}