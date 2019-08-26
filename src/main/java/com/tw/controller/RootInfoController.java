package com.tw.controller;

import com.tw.dto.UserRoleDTO;
import com.tw.entity.RootDeviceGroup;
import com.tw.entity.RootInfo;
import com.tw.entity.common.ConstantParam;
import com.tw.service.RootDeviceGroupService;
import com.tw.service.RootInfoService;
import com.tw.util.ResponseInfo;
import com.tw.util.UserAuthentication;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tw.util.ResponseInfo.*;


/**
 * @Author: John
 * @Description: 管理员登录相关
 * @Date:  2019/8/11 14:22
 * @param: null
 * @return:
 */
@RestController
@RequestMapping("/rootInfo")
public class RootInfoController {

    @Autowired
    private RootInfoService rootInfoService;

    @Autowired
    private RootDeviceGroupService rootDeviceGroupService;


    //日志
    private static Logger logger = Logger.getLogger(DeviceController.class);


    @PostMapping("/loginBySecPwd")
    public ResponseInfo  loginBySecPwd(@RequestParam("secondPsd") String secondePwd, HttpServletRequest httpServletRequest) {

        ResponseInfo response = new ResponseInfo();
        // 1.校验用户身份
        UserRoleDTO userRoleDTO = UserAuthentication.authentication(httpServletRequest);

        if (StringUtils.isBlank(secondePwd)) {
            response.setCode(CODE_ERROR);
            response.setMessage("secondPwd couldn't not be null!");
            return response;
        } else {
            RootInfo rootInfo = new RootInfo();
            rootInfo.setRootPhone(userRoleDTO.getPhoneNumber());
            rootInfo.setSecondPassword(secondePwd);
            List<RootInfo> rootInfos = rootInfoService.getRootInfo(rootInfo);
            if (rootInfos != null && rootInfos.size() > 0) {
                response.setCode(CODE_SUCCESS);
                response.setMessage("login success!");
                return response;
            } else {
                response.setCode(CODE_ERROR);
                response.setMessage("account is not exist or secondPassword is incorrect!");
                return  response;
            }
        }
    }


    /**
     * @Author: John
     * @Description: 校验root用户的二级密码
     * @Date:  2019/8/20 22:14
     * @param: isRoot
     * @param: rootPhone
     * @param: secondPsw
     * @return:
     */
    @PostMapping("/validateSecPsw")
    public ResponseInfo validateSecPsw(@RequestParam(value = "isRoot", required = true) boolean isRoot,
                                       @RequestParam(value = "rootPhone",required = true) String rootPhone,
                                       @RequestParam(value = "secondPsw", required = true) String secondPsw) {
        RootInfo rootInfo = new RootInfo();
        Map<String, Object> data = new HashMap<>();
        ResponseInfo responseInfo = new ResponseInfo();
        responseInfo.setData(data);
        //是管理员标记
        if (isRoot) {
            rootInfo.setRootPhone(rootPhone);
            rootInfo.setSecondPassword(secondPsw);
            List<RootInfo> resRootInfo = rootInfoService.getRootInfo(rootInfo);
            if (resRootInfo != null && resRootInfo.size() > 0) {
                responseInfo.setCode(CODE_SUCCESS);
                responseInfo.setMessage("root login success!");
            } else {
                responseInfo.setCode(CODE_ERROR);
                responseInfo.setMessage("root login failed!");
            }
        } else {
            responseInfo.setCode(CODE_ERROR);
            responseInfo.setMessage("it's not root user!");
        }
        return responseInfo;
    }


    /**
     * @Author: John
     * @Description: 添加root的设备分组
     * @Date:  2019/8/21 0:47
     * @param: rootDeviceGroupName
     * @return:
     */
    @PostMapping("/addRootGroup")
    public ResponseInfo addRootGroup(@RequestParam(value = "rootDeviceGroupName",required = true) String rootDeviceGroupName) {
        ResponseInfo responseInfo = new ResponseInfo();
        Map<String, Object> data = new HashMap<>();
        responseInfo.setData(data);
        //判断组名是否已经存在
        Boolean isExistGroupName = rootDeviceGroupService.checkExistGroup(rootDeviceGroupName);
        if (StringUtils.isNotEmpty(rootDeviceGroupName)) {
            //如果组名已经存在就返回错误提示
            if (isExistGroupName) {
                responseInfo.setCode(CODE_ERROR);
                responseInfo.setMessage("this groupName is already exist！");
                return responseInfo;
            }
            int isAdd = rootDeviceGroupService.addRootGroup(rootDeviceGroupName);
            if (isAdd > 0) {
                responseInfo.setCode(CODE_SUCCESS);
                responseInfo.setMessage("add Root deviceGroup success!");
            } else {
                responseInfo.setCode(CODE_ERROR);
                responseInfo.setMessage("add Root deviceGroup failed!");
            }
            return responseInfo;
        } else {
            responseInfo.setCode(CODE_ERROR);
            responseInfo.setMessage("root deviceGroupName can't not be null!");
            return responseInfo;
        }
    }


    /**
     * @Author: John
     * @Description: 删除root组与设备的关联管理
     * @Date:  2019/8/21 7:39
     * @param: rootDeviceGroup
     * @return:
     */
    @PostMapping("/deleteRootGroup")
    public ResponseInfo deleteRootGroup(@RequestBody RootDeviceGroup rootDeviceGroup) {
        ResponseInfo responseInfo = new ResponseInfo();
        Map<String, Object> data = new HashMap<>();
        responseInfo.setData(data);
        int id = rootDeviceGroup.getId();

        RootDeviceGroup rootDeviceGroup1 = rootDeviceGroupService.getRootDeviceGroupById(id);
        if (rootDeviceGroup == null) {
            responseInfo.setCode(CODE_ERROR);
            responseInfo.setMessage("data error!");
            return responseInfo;
        }

        //如果是默认分组即返回错误信息
        if (rootDeviceGroup1.getRootDeviceGroupName().equals(ConstantParam.DEFAULT_GROUP_NAME)) {
            responseInfo.setCode(CODE_ERROR);
            responseInfo.setMessage("you can't delete default group");
            return responseInfo;
        }

        //如果分不为空就执行删除
        if (rootDeviceGroup != null) {
            int isDelete = rootDeviceGroupService.deleteRootGroup(rootDeviceGroup);
            if (isDelete > 0) {
                responseInfo.setCode(CODE_SUCCESS);
                responseInfo.setMessage("delete rootDeviceGroup success!");
            } else {
                responseInfo.setCode(CODE_ERROR);
                responseInfo.setMessage("delete rootDeviceGroup failed!");
            }
        } else {
            responseInfo.setCode(CODE_ERROR);
            responseInfo.setMessage("rootDeviceGroup can't not be null!");
        }
        return responseInfo;
    }


    /**
     * @Author: John
     * @Description: 移动分组
     * @Date:  2019/8/21 12:56
     * @param: null
     * @return:
     */
    @PostMapping("/moveRootGroup")
    public ResponseInfo moveRootGroup(@RequestParam(value = "deviceId", required = true) int deviceId,
                                      @RequestParam(value = "oldGroupId", required = true) int oldGroupId,
                                      @RequestParam(value = "newGroupId",required = true) int newGroupId) {
        ResponseInfo responseInfo = new ResponseInfo();
        Map<String, Object> data = new HashMap<>();
        responseInfo.setData(data);
        if (deviceId > 0 && oldGroupId > 0 && newGroupId > 0) {
            String newRootGroupName = rootDeviceGroupService.getGroupNameByCondition(newGroupId);
            int isUpdate = rootDeviceGroupService.moveRootGroup(deviceId, oldGroupId, newGroupId, newRootGroupName);
            if (isUpdate > 0) {
                responseInfo.setCode(CODE_SUCCESS);
                responseInfo.setMessage("move root group success!");
            } else {
                responseInfo.setCode(CODE_ERROR);
                responseInfo.setMessage("move root group failed!");
            }
        } else {
            responseInfo.setCode(CODE_ERROR);
            responseInfo.setMessage("root move group failed!");
        }
        return responseInfo;
    }


    /**
     * @Author: John
     * @Description: 修改分组名称
     * @Date:  2019/8/21 13:08
     * @param: rootDeviceGroupId 分组的设备组id
     * @param: newDeviceGroupName 分组名称
     * @return:
     */
    @PostMapping("/modifyRootDeviceGroupName")
    public ResponseInfo modifyRootDeviceGroupName(@RequestParam(value = "rootDeviceGroupId", required = true) int rootDeviceGroupId,
                                          @RequestParam(value = "oldDeviceGroupName", required = true) String oldDeviceGroupName,
                                          @RequestParam(value = "newDeviceGroupName",required = true) String newDeviceGroupName) {
        ResponseInfo responseInfo = new ResponseInfo();
        Map<String, Object> data = new HashMap<>();
        responseInfo.setData(data);

        //判断是否是默认分组
        RootDeviceGroup rootDeviceGroup = rootDeviceGroupService.getRootDeviceGroupById(rootDeviceGroupId);
        //数据为空时，返回错误信息
        if (rootDeviceGroup == null) {
            responseInfo.setCode(CODE_ERROR);
            responseInfo.setMessage("data error!");
            return responseInfo;
        }
        //如果修改的是默认分组，则不予修改，返回信息
        if (rootDeviceGroup.getRootDeviceGroupName().equals(ConstantParam.DEFAULT_GROUP_NAME)) {
            responseInfo.setCode(CODE_ERROR);
            responseInfo.setMessage("you can't change the default group info!");
            return responseInfo;
        }

        //判断修改的组名是否在列表中已经存在,如果已经存在就返回
        Boolean isExistGroup = rootDeviceGroupService.checkExistGroup(newDeviceGroupName);
        if (isExistGroup) {
            responseInfo.setCode(CODE_ERROR);
            responseInfo.setMessage("new groupName is already exist in list!");
            return  responseInfo;
        }
        int isUpdate = rootDeviceGroupService.modifyRootDeviceGroupName(rootDeviceGroupId, oldDeviceGroupName,newDeviceGroupName);
        if (isUpdate > 0) {
            responseInfo.setCode(CODE_SUCCESS);
            responseInfo.setMessage("modify groupName success!");
        } else {
            responseInfo.setCode(CODE_ERROR);
            responseInfo.setMessage("modify groupName failed!");
        }
        return responseInfo;
    }
}