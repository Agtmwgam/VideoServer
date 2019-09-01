package com.tw.service;

import com.tw.dao.RootDeviceGroupDao;
import com.tw.entity.Device;
import com.tw.entity.RootDeviceGroup;
import com.tw.entity.common.ConstantParam;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RootDeviceGroupService {

    @Autowired
    private RootDeviceGroupDao rootDeviceGroupDao;

    public List<RootDeviceGroup> getAllRootDeviceGroup() {
        return rootDeviceGroupDao.getAllRootDeviceGroup();
    }

    public List<Device> getRootDeviceByGroupId(int rootDeviceGroupId) {
        return rootDeviceGroupDao.getRootDeviceByGroupId(rootDeviceGroupId);
    }

    public int addRootGroup(String rootDeviceGroupName) {
        return rootDeviceGroupDao.addRootGroup(rootDeviceGroupName);
    }

    /**
     * @Author: John
     * @Description: 移动root分组
     * @Date:  2019/8/22 23:50
     * @param: deviceId
     * @param: oldGroupId
     * @param: newGroupId
     * @param: newRootGroupName
     * @return:
     */
    public int moveRootGroup(int deviceId, int oldGroupId, int newGroupId, String newRootGroupName) {
        Map<String, Object> param = new HashMap<>();
        param.put("deviceId", deviceId);
        param.put("oldGroupId", oldGroupId);
        param.put("newGroupId", newGroupId);
        param.put("newRootGroupName", newRootGroupName);
        return rootDeviceGroupDao.moveRootGroup(param);
    }

    //根据groupId拿到组名
    public String getGroupNameByCondition(int newGroupId) {
        return rootDeviceGroupDao.getGroupNameByCondition(newGroupId);
    }


    public int deleteRootGroup(RootDeviceGroup rootDeviceGroup) {
        //将设备的状态置为库存状态

        return rootDeviceGroupDao.deleteRootGroup(rootDeviceGroup);
    }

    public int modifyRootDeviceGroupName(String oldDeviceGroupName, String newDeviceGroupName) {
        Map<String, Object> param = new HashMap<>();
        param.put("oldDeviceGroupName", oldDeviceGroupName);
        param.put("newDeviceGroupName", newDeviceGroupName);
        return rootDeviceGroupDao.modifyRootDeviceGroupName(param);
    }

    public Boolean checkExistGroup(String rootDeviceGroupName) {
        Map<String, Object> param = new HashMap<>();
        param.put("isValid", ConstantParam.IS_VALID_YES);
        param.put("rootGroupName", rootDeviceGroupName);
        return rootDeviceGroupDao.checkExistGroup(param);
    }

    public Boolean checkIsDefaultGroup(int id) {
        Map<String, Object> param = new HashMap<>();
        param.put("isValid", ConstantParam.IS_VALID_YES);
        param.put("id", id);
        param.put("defaultGroupName", ConstantParam.DEFAULT_GROUP_NAME);
        return rootDeviceGroupDao.checkIsDefaultGroup(param);
    }

    public RootDeviceGroup getRootDeviceGroupById(int id) {
        return rootDeviceGroupDao.getRootDeviceGroupById(id);
    }

    public List<RootDeviceGroup> getObjByDeviceGroupId(int rootDeviceGroupId, String defaultGroupName) {
        Map<String, Object> param = new HashMap<>();
        param.put("rootDeviceGroupId", rootDeviceGroupId);
        param.put("defaultGroupName", defaultGroupName);
        param.put("isValid", ConstantParam.IS_VALID_YES);
        return rootDeviceGroupDao.getObjByDeviceGroupId(param);
    }


    public int getDefaultGroupId() {
        Map<String, Object> param = new HashMap<>();
        param.put("isValid", ConstantParam.IS_VALID_YES);
        param.put("defaultGroupName", ConstantParam.DEFAULT_GROUP_NAME);
        return rootDeviceGroupDao.getDefaultGroupId(param);
    }

    public int moveToDefaultGroup(int rootDeviceGroupId, int defaultGroupId) {
        Map<String, Object> param = new HashMap<>();
        param.put("isValid", ConstantParam.IS_VALID_YES);
        param.put("oldGroupId", rootDeviceGroupId);
        param.put("defaultGroupId", defaultGroupId);
        param.put("defaultGroupName", ConstantParam.DEFAULT_GROUP_NAME);
        param.put("isValid", ConstantParam.IS_VALID_YES);
        return rootDeviceGroupDao.moveToDefaultGroup(param);
    }

    public int delRootDevice(int deviceId, int rootDeviceGroupId) {
        Map<String, Object> param = new HashMap<>();
        param.put("isValid", ConstantParam.IS_VALID_YES);
        param.put("deviceId", deviceId);
        param.put("rootDeviceGroupId", rootDeviceGroupId);
        return rootDeviceGroupDao.delRootDevice(param);
    }
}