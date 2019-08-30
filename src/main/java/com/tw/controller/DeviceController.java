package com.tw.controller;


import com.tw.dto.*;
import com.tw.entity.*;
import com.tw.service.*;
import com.tw.util.ResponseInfo;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: John
 * @Description: 设备相关的操作
 * @Date: 2019/8/5 12:49
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

    @Autowired
    private RootInfoService rootInfoService;

    @Autowired
    private RootDeviceGroupService rootDeviceGroupService;

    //日志
    private static Logger logger = Logger.getLogger(DeviceController.class);


    /**
     * @Author: John
     * @Description: 插入device设备信息（管理员录入设备）
     * @Date: 2019/8/5 22:39
     * @param: device
     * @return:
     */
    @PostMapping("/addDevice")
    @Transactional
    public ResponseInfo addDevice(@RequestBody Device device) {
        ResponseInfo responseInfo = new ResponseInfo();
        //判断必要的信息是否为空
        if (StringUtils.isEmpty(device.getSerial()) || StringUtils.isEmpty(device.getDeviceVerifyCode()) || StringUtils.isEmpty(device.getDeviceType()) || StringUtils.isEmpty(device.getSoftVersion()) || StringUtils.isEmpty(device.getProductDate())) {
            responseInfo.setCode(ResponseInfo.CODE_ERROR);
            responseInfo.setMessage("device impostant information couldn't be null!");
            return responseInfo;
        }


        //查询数据库中是否已经存在该设备，设备号和验证码检测
        List<Device> deviceList = deviceService.getDeviceByCodition(device);
        if (deviceList != null && deviceList.size() > 0) {
            logger.warn("设备 " + device.getSerial() + " 已经存在！");
            responseInfo.setCode(ResponseInfo.CODE_ERROR);
            responseInfo.setMessage("device already exists！");
            return responseInfo;
        }

        //       如果设备名为空，则Serial为默认设备名称  -by liutianwen
        if (StringUtils.isBlank(device.getDeviceName())) {
            device.setDeviceName(device.getSerial());
        }

        int isAdd = deviceService.addDevice(device);
        Integer isAddRootDeviceGroup = 0;
//        录入设备加入管理员默认分组  --by liutianwen
//       判断是否成功录入
        if (isAdd == 1) {
            //取出deviceID,并把设备放入管理员默认分组中
            deviceList = deviceService.getDeviceByCodition(device);
            for (Device device1 : deviceList) {
                isAddRootDeviceGroup = rootInfoService.addDeviceToDefaultRootDeviceGroup(device1.getDeviceId());
            }
        } else {
            responseInfo.setCode(ResponseInfo.CODE_ERROR);
            responseInfo.setMessage("device add fail！");
            return responseInfo;
        }
//        判断设备是否成功添加到root设备分组
        if (isAddRootDeviceGroup == 1) {
            if (isAdd > 0) {
                responseInfo.setCode(ResponseInfo.CODE_SUCCESS);
                responseInfo.setMessage("add device success!");
                return responseInfo;
            } else {
                responseInfo.setCode(ResponseInfo.CODE_ERROR);
                responseInfo.setMessage("device add to root device group fail！");
                return responseInfo;
            }
        }
        return responseInfo;
    }

    /**
     * @Author: John  & liutianwen
     * @Description: 普通用户删除设备
     * @Date: 2019/8/5 22:40
     * @param: deviceId
     * @return:
     */
    @PostMapping("/deleteDevice")
    public ResponseInfo deleteDevice(@RequestParam(value = "deviceId") int deviceId) {
        ResponseInfo response = new ResponseInfo();
        int isDel = deviceService.deleteDevice(deviceId);
        System.out.println("==========删除的结果为：" + isDel);

        if (isDel > 0) {
            //删除设备信息后，原本的关联关系也要删除
            DeviceGroupRelate deviceGroupRelate = new DeviceGroupRelate();
            deviceGroupRelate.setDeviceId(deviceId);
            int delDevGroup = deviceGroupRelateService.deleteByDeviceGroupRelate(deviceGroupRelate);
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
        //这里只是修改名字，所以这里不能真个类update
        Integer isUpdate = deviceService.updateDevice(device);
        System.out.println("=======update的结果为：" + isUpdate);
        if (isUpdate > 0) {
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
     * @Description: 更新device名称
     * @Date: 2019/8/5 22:56
     * @param: device json对象
     * @return:
     */
    @PostMapping("/modifyDeviceName")
    public ResponseInfo modifyDeviceName(@RequestBody Device device) {
        ResponseInfo response = new ResponseInfo();
        Map<String, Object> data = new HashMap<>();
        response.setData(data);
        int isUpdate = deviceService.updateDeviceName(device);
        System.out.println("=======update的结果为：" + isUpdate);
        if (isUpdate > 0) {
            response.setCode(ResponseInfo.CODE_SUCCESS);
            response.setMessage("modifyDeviceName success!");
        } else {
            response.setCode(ResponseInfo.CODE_ERROR);
            response.setMessage("modifyDeviceName failed!");
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
    @RequestMapping("/getDeviceByCondition")
    public ResponseInfo getDeviceByCodition(Device device, @RequestParam(value = "pageNo") int pageNo,
                                            @RequestParam(value = "pageSize") int pageSize) {
        ResponseInfo response = new ResponseInfo();
        response.setPageNo(pageNo);
        response.setPageSize(pageSize);

        Map<String, Object> resultMap = new HashMap<>();
        List<Device> devices = deviceService.getDeviceByCoditionPage(device, pageNo, pageSize);
        int totle = deviceService.getCountOfLikCondition(device);
        for (Device device1 : devices) {
            System.out.println("===:" + device1.toString());
        }
        if (devices != null) {
            resultMap.put("total", devices.size());
            resultMap.put("list", devices);
            response.setCode(ResponseInfo.CODE_SUCCESS);
            response.setTotal(totle);
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
    public ResponseInfo getDeviceLikeCondition(Device device, @RequestParam(value = "pageNo") int pageNo,
                                               @RequestParam(value = "pageSize") int pageSize) {
        ResponseInfo responseInfo = new ResponseInfo();
        responseInfo.setPageNo(pageNo);
        responseInfo.setPageSize(pageSize);

        // 模糊搜索（加上所属用户）    --by liutianwen
        List<DeviceAndUserNameDTO> deviceList = deviceService.getDeviceLikeCondition(device, pageNo, pageSize);
        int total = deviceService.getTotalOfCondition(device);
        if (deviceList != null) {
            responseInfo.setCode(ResponseInfo.CODE_SUCCESS);
            responseInfo.setMessage("get device by condition success!");
            responseInfo.setData(deviceList);
            responseInfo.setTotal(total);
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
     * @param: deviceGroupName
     * @param: httpServletRequest
     * @return:
     */
    @PostMapping("/addGroup")
    public ResponseInfo addGroup(@RequestParam(value = "deviceGroupName", required = true) String groupName,
                                 @RequestParam(value = "userId") int userId) {
        ResponseInfo response = new ResponseInfo();
        // 1.校验用户身份
        //UserRoleDTO userRoleDTO = UserAuthentication.authentication(httpServletRequest);
        //String phoneNumber = userRoleDTO.getPhoneNumber();
        //String phoneNumber = "18814373836";
        //如果已经登录成功，就可以添加，否则提示登录
        if (userId > 0) {
            DeviceGroup deviceGroup = new DeviceGroup();
            deviceGroup.setDeviceGroupName(groupName);

            //检查改用户是否有设备组名重复
            if (devGroupService.isCanAddGroup(userId, groupName)) {
                int isAdd = devGroupService.addDevGroup(deviceGroup);
                if (isAdd > 0) {
                    //添加到自己的分组中
                    UserDeviceGroupRelate userDeviceGroupRelate = new UserDeviceGroupRelate();
                    userDeviceGroupRelate.setDeviceGroupId(deviceGroup.getDeviceGroupId());
                    userDeviceGroupRelate.setUserId(userId);
                    int isAddRelate = userDeviceGroupRelateService.addUserDeviceGroupRelate(userDeviceGroupRelate);
                    if (isAddRelate == 1) {
                        response.setCode(ResponseInfo.CODE_SUCCESS);
                        response.setMessage("add deviceGroup success!");
                    } else {
                        response.setCode(ResponseInfo.CODE_ERROR);
                        response.setMessage("add deviceGroup failed!");
                    }
                } else {
                    response.setCode(ResponseInfo.CODE_ERROR);
                    response.setMessage("add deviceGroup failed!");
                }
            } else {
                response.setCode(ResponseInfo.CODE_ERROR);
                response.setMessage("The current user already owns the group!");
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
     * @param: deviceGroup
     * @return:
     */
    @PostMapping("/modifyDeviceGroupName")
    public ResponseInfo modifyDeviceGroupName(@RequestBody DeviceGroup deviceGroup) {
        ResponseInfo response = new ResponseInfo();
        String oldGroupName = deviceGroup.getDeviceGroupName();
        int groupId = deviceGroup.getDeviceGroupId();
        //如果可以操作（不是默认分组）
        if (devGroupService.isCanOperate(groupId)) {
            //检验分组名是否已经存在
            List<DeviceGroup> deviceList = devGroupService.getDevGroupByGroupName(oldGroupName);
            //说明已经存在这个数据
            if (deviceList != null && deviceList.size() > 0) {
                response.setCode(ResponseInfo.CODE_ERROR);
                response.setMessage("this groupName have already exists!");
                return response;
            }
            int isUpdate = devGroupService.updateDevGroup(deviceGroup);
            if (isUpdate > 0) {
                response.setCode(ResponseInfo.CODE_SUCCESS);
                response.setMessage("modify deviceGroup success!");
            } else {
                response.setCode(ResponseInfo.CODE_ERROR);
                response.setMessage("modify deviceGroup failed!");
            }
        } else {
            response.setCode(ResponseInfo.CODE_ERROR);
            response.setMessage("you can't operate the default group!");
        }
        return response;
    }


    /**
     * @Author: John
     * @Description: 删除设备分组接口
     * @Date: 2019/8/10 21:59
     * @param: deviceGroupId
     * @return:
     */
    @PostMapping("/deleteDeviceGroup")
    public ResponseInfo deleteDeviceGroup(@RequestParam(value = "groupId") int groupId) {
        ResponseInfo response = new ResponseInfo();
        if (groupId > 0) {
            if (devGroupService.isCanOperate(groupId)) {
                //01、删除关联关系表
                DeviceGroupRelate deviceGroupRelate = new DeviceGroupRelate();
                deviceGroupRelate.setGroupId(groupId);
                //删除用户和组的关系
                UserDeviceGroupRelate userDeviceGroupRelate = new UserDeviceGroupRelate();
                userDeviceGroupRelate.setDeviceGroupId(groupId);
                //这里不能够将是否删除关联关系作为决定下面是否运行的条件，因为有可能本来就是为空
                int isDelRelate = deviceGroupRelateService.deleteByDeviceGroupRelate(deviceGroupRelate);
                int idDelGroupRelate = userDeviceGroupRelateService.delUserGroupRelate(userDeviceGroupRelate);

                //02、删除分组
                int isDelete = devGroupService.deleteDevGroupById(groupId);
                if (isDelete > 0) {
                    response.setCode(ResponseInfo.CODE_SUCCESS);
                    response.setMessage("delete deviceGroup success!");
                } else {
                    response.setCode(ResponseInfo.CODE_ERROR);
                    response.setMessage("delete deviceGroup failed!");
                }
            } else {
                //如果是默认分组就返回
                response.setCode(ResponseInfo.CODE_ERROR);
                response.setMessage("you can't operate the default group!");
            }
        } else {
            response.setCode(ResponseInfo.CODE_ERROR);
            response.setMessage("deviceGroupId is not allow be null!");
        }
        return response;
    }

    /**
     * @Author: John  & liutianwen
     * @Description: 删除组和设备的关联关系(用户删除设备)
     * @Date: 2019/8/17 22:45
     * @param: 组和设备关联关系对象
     * @return: responseInfo 标准返回类
     */
    @PostMapping("/deleteDeviceGroupRelate")
    public ResponseInfo deleteDeviceGroupRelate(@RequestBody DeviceGroupRelate deviceGroupRelate) {
//        int groupId = deviceGroupRelate.getGroupId();
        int deviceId = deviceGroupRelate.getDeviceId();
        ResponseInfo responseInfo = new ResponseInfo();
//        删除设备和设备组关系
        int isDelete = deviceGroupRelateService.deleteDeviceGroupRelate(deviceGroupRelate);
//       如果成功删除，则把设备状态改为在库，并返回删除成功信息，反之返回失败信息
        if (isDelete > 0) {
            deviceService.updateDeviceStatus(deviceId, '0');
            responseInfo.setCode(ResponseInfo.CODE_SUCCESS);
            responseInfo.setMessage("delete deviceGroupRelate success!");
        } else {
            responseInfo.setCode(ResponseInfo.CODE_ERROR);
            responseInfo.setMessage("delete deviceGroupRelate failed!");
        }
        return responseInfo;
    }


    /**
     * @Author: John  &  liutianwen
     * @Description: 移动设备的分组
     * @Date: 2019/8/16 1:34
     * @param: deviceId 设备id
     * @param: deviceGroupId  分组id
     * @param: newGroupId  新分组id
     * @return:
     */
    @PostMapping("/moveDeviceGroup")
    @Transactional
    public ResponseInfo moveDeviceGroup(@RequestBody MoveGroupDTO moveGroupDTO) {

        //当前用户ID
//        String userId = null;
        //设备ID
        int deviceId = moveGroupDTO.getDeviceId();
        //现在的设备组ID
        int groupId = moveGroupDTO.getGroupId();
        //新的设备组ID
        int newGroupId = moveGroupDTO.getNewGroupId();

        ResponseInfo responseInfo = new ResponseInfo();
        DeviceGroupRelate deviceGroupRelate = new DeviceGroupRelate();
        deviceGroupRelate.setDeviceId(deviceId);
        deviceGroupRelate.setGroupId(groupId);
        //查询设备在哪个唯一分组下
        DeviceGroupRelate deviceGroupRelateDto = deviceGroupRelateService.getDeviceGroupRelate(deviceGroupRelate);
        if (deviceGroupRelateDto == null) {
            responseInfo.setMessage(ResponseInfo.CODE_ERROR);
            responseInfo.setMessage("设备不存在，请检查！");
        }
        //加入新设备组ID
        deviceGroupRelateDto.setGroupId(newGroupId);
        //更新设备与设备组关系
        int isUpdate = deviceGroupRelateService.updateDeviceGroupRelateBy(deviceGroupRelateDto);
        if (isUpdate > 0) {
            responseInfo.setCode(ResponseInfo.CODE_SUCCESS);
            responseInfo.setMessage("move device to new group success!");
        } else {
            responseInfo.setCode(ResponseInfo.CODE_ERROR);
            responseInfo.setMessage("move device to new group failed!");
        }

        return responseInfo;
    }


    //根据用户获得分组
    @GetMapping("/getDeviceByUserId")
    public ResponseInfo getDeviceByUserId(@RequestParam(value = "userId", required = false) int userId) {
        ResponseInfo responseInfo = new ResponseInfo();

        //声明返回的对象
        UserGroupDTO userGroupDTO = new UserGroupDTO();

        //用于存放用户下面的组下面的设备的
        List<DevGroupDTO> devGroupDTOList = new ArrayList<>();

        //用于放结果的deviceList
        List<DeviceGroup> devGroupList = new ArrayList<>();
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
            int groupId = userDeviceGroupRelate.getDeviceGroupId();
            DeviceGroup devGroup = devGroupService.getDevGroupById(groupId);

            deviceList = deviceService.getDeviceByGroupId(groupId);
            //每一次结果都放到组里面的设备列表结果集里面
            devGroupDTO.setDeviceGroup(devGroup);
            devGroupDTO.setDeviceList(deviceList);
            devGroupDTOList.add(devGroupDTO);
        }
        userGroupDTO.setDevGroupList(devGroupDTOList);
        if (userGroupDTO != null) {
            responseInfo.setCode(ResponseInfo.CODE_SUCCESS);
            responseInfo.setData(userGroupDTO);
            responseInfo.setMessage("Get user's all devices success!");
        } else {
            responseInfo.setCode(ResponseInfo.CODE_ERROR);
            responseInfo.setMessage("Get user's all devices failed!");
        }
        return responseInfo;
    }


    /**
     * @Author: John
     * @Description: 管理员获得所有设备
     * @Date: 2019/8/20 7:40
     * @param: isRoot
     * @return:
     */
    @RequestMapping("/getRootAllDevices")
    public ResponseInfo getRootAllDevices(@RequestParam(value = "isRoot", required = true) boolean isRoot) {
        ResponseInfo responseInfo = new ResponseInfo();
        List<RootDeviceGroupDTO> rootDeviceGroupDTOList = new ArrayList<>();
        //获得所有组，不重复
        List<RootDeviceGroup> rootDeviceGroups = rootDeviceGroupService.getAllRootDeviceGroup();
        for (RootDeviceGroup deviceGroup : rootDeviceGroups) {
            RootDeviceGroupDTO rootDeviceGroupDTO = new RootDeviceGroupDTO();
            List<Device> deviceList = rootDeviceGroupService.getRootDeviceByGroupId(deviceGroup.getRootDeviceGroupId());
            rootDeviceGroupDTO.setRootDeviceGroup(deviceGroup);
            rootDeviceGroupDTO.setDeviceList(deviceList);
            //将对象赋值后，全部添加到返回的数据集合中
            rootDeviceGroupDTOList.add(rootDeviceGroupDTO);
        }
        responseInfo.setCode(ResponseInfo.CODE_SUCCESS);
        responseInfo.setData(rootDeviceGroupDTOList);
        responseInfo.setMessage("get deviceList by userId success!");
        return responseInfo;
    }


    /**
     * @Author: John
     * @Description: 给设备添加分组（普通用户添加设备）
     * @Date: 2019/8/15 1:49
     * @param: serial               序列号
     * @param: deviceVerifyCode     验证码
     * @param: deviceGroupId        组id
     * @return:
     */
    @PostMapping("/addDevGroup")
    public ResponseInfo addDevGroup(@RequestParam("serial") String serial,
                                    @RequestParam(value = "deviceVerifyCode") String deviceVerifyCode,
                                    @RequestParam(value = "groupId") int groupId) {
        ResponseInfo responseInfo = new ResponseInfo();
        Device device = new Device();
        device.setSerial(serial);
        device.setDeviceVerifyCode(deviceVerifyCode);
        device.setIsValid('1');
        List<Device> deviceList = deviceService.getDeviceByCodition(device);
        //如果校验通过，说明可以对这个设备进行操作
        if (deviceList != null && deviceList.size() > 0) {
            int deviceId = deviceList.get(0).getDeviceId();
            DeviceGroup deviceGroup = new DeviceGroup();
            DeviceGroupRelate deviceGroupRelate = new DeviceGroupRelate();
            deviceGroupRelate.setDeviceId(deviceId);
            deviceGroupRelate.setGroupId(groupId);

            //如果已经存在关联关系
            if (deviceGroupRelateService.isLinkGroup(deviceId)) {
                responseInfo.setCode(ResponseInfo.CODE_ERROR);
                responseInfo.setMessage("这个设备已经被用户绑定！");
            } else {
                int isAdd = deviceGroupRelateService.addDeviceGroupRelate(deviceGroupRelate);
                if (isAdd > 0) {
                    responseInfo.setCode(ResponseInfo.CODE_SUCCESS);
                    responseInfo.setMessage("add deviceGroupRelate success!");
                } else {
                    responseInfo.setCode(ResponseInfo.CODE_ERROR);
                    responseInfo.setMessage("add deviceGroupRelate failed!");
                }
            }
            return responseInfo;
        } else {
            responseInfo.setCode(ResponseInfo.CODE_ERROR);
            responseInfo.setMessage("check the serial and deviceVeifyCode please!");
        }
        return responseInfo;
    }
}