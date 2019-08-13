package com.tw.controller;

import com.tw.dto.UserAndDeviceSerialDTO;
import com.tw.entity.Device;
import com.tw.entity.UserDeviceRelate;
import com.tw.entity.VUser;
import com.tw.entity.common.ConstantParam;
import com.tw.service.DeviceService;
import com.tw.service.UserDeviceRelateService;
import com.tw.service.VUserService;
import com.tw.util.ResponseInfo;
import org.apache.commons.lang.StringUtils;
import org.apache.jasper.tagplugins.jstl.core.If;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    private DeviceService DeviceService;

    /**
     * @param requestMap phoneNumber
     * @return
     * @Date 2019/8/5 22:21
     * @Created liutianwen
     * @Description 用户管理 根据用户手机，模糊搜索用户信息和相应绑定的设备号
     */
    @PostMapping(value = "/getUserInfo")
    public ResponseInfo getUserInfo(@RequestBody Map<String, Object> requestMap) {
        ResponseInfo response = new ResponseInfo();
        VUser user = new VUser();

        String phoneNumber = requestMap.get("phoneNumber").toString();
        user.setPhoneNumber(phoneNumber);

        //模糊查询用户
        List<UserAndDeviceSerialDTO> uAdList = userService.fuzzyQueryUserAndDeviceList(user);
        response.setCode(CODE_SUCCESS);
        response.setData(uAdList);

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
        String phoneNumber = requestMap.get("phoneNumber").toString();
        String serial = requestMap.get("serial").toString();

        // 非空校验
        response = checkPhoneAndSerial(phoneNumber, serial);
        if (response.getCode() == CODE_ERROR) {
            return response;
        }

        // 把值set到实体类中，供查找数据使用
        VUser vUser = new VUser();
        vUser.setPhoneNumber(phoneNumber);
        Device device = new Device();
        device.setSerial(serial);
        device.setIsValid('1');

        //查找vUser和deviceId
        vUser = userService.queryUser(vUser);
        List<Device> deviceList = DeviceService.getDeviceByCodition(device);
        //查找deviceId  tips:因为serial是确定的，所以此处只会找到一个deviceId
        int deviceId = 0;
        for (Device deviceTemp : deviceList) {
            deviceId = deviceTemp.getDeviceId();
        }

        //把所有要传入参数set到实体类中，准备调用接口
        UserDeviceRelate udr = new UserDeviceRelate();
        udr.setUserId(vUser.getUserID());
        udr.setDeviceId(deviceId);
        udr.setIsValid('0');

        //逻辑删除用户设备，并返回删除个数
        int delNum = userDeviceRelateService.delUserDevice(udr);

        response.setCode(CODE_SUCCESS);
        response.setMessage(delNum+"user's  devices were deleted successfull!");

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

}
