package com.tw.controller;

import com.tw.dto.DevGroupDTO;
import com.tw.dto.UserGroupDTO;
import com.tw.dto.UserRoleDTO;
import com.tw.entity.*;
import com.tw.service.*;
import com.tw.util.HEXUtil;
import com.tw.util.ResponseInfo;
import com.tw.util.UserAuthentication;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.rmi.log.LogInputStream;

import java.io.UnsupportedEncodingException;
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
@RequestMapping("/device")
public class DeviceController {

    @Autowired
    private VUserService vUserService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private DevGroupService devGroupService;

    @Autowired
    private UserDeviceGroupRelateService userDeviceGroupRelateService;

    @Autowired
    private DeviceGroupRelateService deviceGroupRelateService;

    //日志
    private static Logger logger = Logger.getLogger(DeviceController.class);


    /**
     * @Author: John
     * @Description: 插入device设备信息
     * @Date: 2019/8/5 22:39
     * @param: device
     * @return:
     */
    @PostMapping("/addDevice")
    public ResponseInfo addDevice(@RequestBody Device device) {

        ResponseInfo responseInfo = new ResponseInfo();

        //查询数据库中是否已经存在该设备，感觉设备号和验证码检测
        List<Device> deviceList = deviceService.getDeviceByCodition(device);
        if (deviceList != null && deviceList.size() > 0) {
            logger.warn("设备 " + device.getSerial() + " 已经存在！");
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
        return responseInfo;
    }


    /**
     * @Author: John
     * @Description: 删除device设备信息
     * @Date: 2019/8/5 22:40
     * @param: deviceId
     * @return:
     */
    @PostMapping("/deviceId")
    public ResponseInfo deleteDevice(@RequestParam(value = "deviceId") int deviceId) {
        ResponseInfo response = new ResponseInfo();
        int isDel = deviceService.deleteDevice(deviceId);
        System.out.println("==========删除的结果为：" + isDel);

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
     * @Date: 2019/8/5 22:56
     * @param: device json对象
     * @return:
     */
    @PostMapping("/updateDevice")
    public ResponseInfo updateDevice(@RequestBody Device device) {
        ResponseInfo response = new ResponseInfo();
        Integer isUpdate = deviceService.updateDevice(device);
        System.out.println("=======update的结果为：" + isUpdate);
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
     * @Date: 2019/8/5 12:51
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
            System.out.println("=====device.toString()" + device.toString());
        } else {
            resultMap.put("totle", 0);
            response.setCode(ResponseInfo.CODE_ERROR);
            response.setMessage("getDeviceByDeviceId failed!");
            response.setData(resultMap);
        }
        return response;
    }


    /**
     * @Author: John
     * @Description: 根据条件查询设备列表
     * @Date: 2019/8/6 0:43
     * @param: device
     * @return:
     */
    @GetMapping("/getDeviceByCondition")
    public ResponseInfo getDeviceByCodition(Device device, @RequestParam(value = "pageNo") int pageNo, @RequestParam(value = "pageSize") int pageSize) {
        ResponseInfo response = new ResponseInfo();
        response.setPageNo(pageNo);
        response.setPageSize(pageSize);

        Map<String, Object> resultMap = new HashMap<>();
        List<Device> devices = deviceService.getDeviceByCoditionPage(device, pageNo, pageSize);
        for (Device device1 : devices) {
            System.out.println("===:" + device1.toString());
        }
        if (devices != null) {
            int totle = devices.size();
            resultMap.put("total", totle);
            resultMap.put("list", devices);
            response.setCode(ResponseInfo.CODE_SUCCESS);
            response.setTotal(devices.size());
            response.setData(resultMap);
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
     * @Description: 根据设备序列号、设备型号和生成日期进行模糊搜索
     * @Date: 2019/8/13 1:27
     * @param: device
     * @return:
     */
    @PostMapping("/getDeviceLikeCondition")
    public ResponseInfo getDeviceLikeCondition(Device device, @RequestParam(value = "pageNo") int pageNo, @RequestParam(value = "pageSize") int pageSize) {
        ResponseInfo responseInfo = new ResponseInfo();
        responseInfo.setPageNo(pageNo);
        responseInfo.setPageSize(pageSize);

        List<Device> deviceList = deviceService.getDeviceLikeCondition(device, pageNo, pageSize);
        if (deviceList != null && deviceList.size() > 0) {
            responseInfo.setCode(ResponseInfo.CODE_SUCCESS);
            responseInfo.setMessage("get device by condition success!");
            responseInfo.setData(deviceList);
            responseInfo.setTotal(deviceList.size());
        } else {
            responseInfo.setCode(ResponseInfo.CODE_ERROR);
            responseInfo.setMessage("get device by condition failed!");
            responseInfo.setTotal(0);
        }
        return responseInfo;
    }


    /**
     * @Author: John
     * @Description: 客户端添加分组接口
     * @Date: 2019/8/10 21:52
     * @param: groupName
     * @param: httpServletRequest
     * @return:
     */
    @PostMapping("/addGroup")
    public ResponseInfo addGroup(@RequestParam(value = "groupName", required = true) String groupName) {
        ResponseInfo response = new ResponseInfo();
        // 1.校验用户身份
        //UserRoleDTO userRoleDTO = UserAuthentication.authentication(httpServletRequest);
        //String phoneNumber = userRoleDTO.getPhoneNumber();
        String phoneNumber = "18814373836";
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
     * @Date: 2019/8/10 21:56
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
     * @Date: 2019/8/10 21:59
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


    @GetMapping("/getDeviceByUserId")
    public UserGroupDTO getDeviceByUserId(@RequestParam(value = "userId") int userId) {
        //声明返回的对象
        UserGroupDTO userGroupDTO = new UserGroupDTO();

        //用于存放用户下面的组下面的设备的
        List<DevGroupDTO> devGroupDTOList = new ArrayList<>();


        //用于放结果的deviceList
        List<DevGroup> devGroupList = new ArrayList<>();
        Map<String, Object> param = new HashMap<String, Object>();

        //根据id查回用户信息
        VUser user = new VUser();
        user.setUserID(userId);
        VUser vUser = vUserService.queryUser(user);
        userGroupDTO.setUser(vUser);

        //根据用户id将所有的设备组都查回来
        List<UserDeviceGroupRelate> userDeviceGroupRelates = userDeviceGroupRelateService.getGroupListByUserId(userId);
        for (UserDeviceGroupRelate userDeviceGroupRelate : userDeviceGroupRelates) {
            DevGroupDTO devGroupDTO = new DevGroupDTO();

            //每次都需要重新new一个对象去接查出来的设备信息
            List<Device> deviceList = new ArrayList<>();
            int groupId = userDeviceGroupRelate.getGroupId();

            DevGroup devGroup = devGroupService.getDevGroupById(groupId);
            List<DeviceGroupRelate> deviceGroupList = deviceGroupRelateService.getDeviceGroupByGroupId(groupId);
            for (DeviceGroupRelate deviceGroup : deviceGroupList) {
                Device device = deviceService.getDeviceById(deviceGroup.getDeviceId());
                //将每次的结果添加进去deviceList里面
                deviceList.add(device);
            }
            //每一次结果都放到组里面的设备列表结果集里面
            devGroupDTO.setDevGroup(devGroup);
            devGroupDTO.setDeviceList(deviceList);
            devGroupDTOList.add(devGroupDTO);
        }
        userGroupDTO.setDevGroupList(devGroupDTOList);
        return userGroupDTO;
    }





//    public static void main(String[] args) throws UnsupportedEncodingException {
//
//        //假设原密码是这个：
//        String testText = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
//        System.out.println("原密码是："+testText);
//
//        //用于拼接加密后的
//        StringBuilder resultStr = new StringBuilder();
//
//        //转成十六进制：
//        String orgHex = HEXUtil.encode(testText);
//        System.out.println("原密码字符串转16进制"+ HEXUtil.encode(testText));
//
//        //假设报文的第三位随机数是(最大的一个数)
//        int random = 9;
//
//        //对原本的每一个密文加上9
//
//        //将十六进制拆出来，每两个十六进制确定一个值
//        ArrayList<String> allHex16 = HEXUtil.splitByBytes(orgHex, 2);
//
//
//        //02、对每一个十六进制进行逆运算，减去random值
//        for (String everyHex16 : allHex16) {
//            //十六进制转十进制
//            int tempC = Integer.valueOf(everyHex16, 16);
//            //将加密前加的那个随机数减回去，得到加密前的报文的十进制
//            int resultC = tempC + random;
//            //再将得到的加密前的十进制转成十六进制
//            String resultHex = Integer.toHexString(resultC);
//            //加密后的十六进制是：
//            System.out.println(everyHex16+"加密后的十六进制是:"+resultHex);
//
//            //添加到结果中
//            resultStr.append(resultHex);
//        }
//        //加密后的十六进制是：
//        System.out.println("加密后的十六进制resultStr:"+resultStr);
//        System.out.println("============转回字符串："+HEXUtil.decode(resultStr.toString()));
//    }
}