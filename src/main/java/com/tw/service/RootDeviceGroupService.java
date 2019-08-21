package com.tw.service;

import com.tw.dao.RootDeviceGroupDao;
import com.tw.entity.Device;
import com.tw.entity.RootDeviceGroup;
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

    public int moveRootGroup(int deviceId, int oldGroupId, int newGroupId, String newRootGroupName) {
        Map<String, Object> param = new HashMap<>();
        param.put("deviceId", deviceId);
        param.put("oldGroupId", oldGroupId);
        param.put("newGroupId", newGroupId);
        param.put("newRootGroupName", newRootGroupName);
        return rootDeviceGroupDao.moveRootGroup(param);
    }

    public String getGroupNameByCondition(int newGroupId) {
        return rootDeviceGroupDao.getGroupNameByCondition(newGroupId);
    }

    public int deleteRootGroup(RootDeviceGroup rootDeviceGroup) {
        return rootDeviceGroupDao.deleteRootGroup(rootDeviceGroup);
    }
}