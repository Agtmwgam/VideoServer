package com.tw.service;

import com.tw.dao.DevGroupDao;
import com.tw.entity.DeviceGroup;
import com.tw.entity.common.ConstantParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DevGroupService {

    @Autowired
    private DevGroupDao devGroupDao;

    //添加
    public int addDevGroup(DeviceGroup deviceGroup) {
        return devGroupDao.addDevGroup(deviceGroup);

    }


    //修改
    public int updateDevGroup(DeviceGroup deviceGroup) {
        return devGroupDao.updateDevGroup(deviceGroup);
    }


    //删除
    public int deleteDevGroupById(int devGroupId) {
        return devGroupDao.deleteDevGroupById(devGroupId);
    }


    //根据id查找
    public DeviceGroup getDevGroupById(int devGroupId) {
        return devGroupDao.getDevGroupById(devGroupId);
    }


    //根据groupName查找
    public List<DeviceGroup> getDevGroupByGroupName(String devGroupName) {
        return devGroupDao.getDevGroupByGroupName(devGroupName);
    }


    //查询是否可以删除和修改该分组
    public Boolean isCanOperate(int groupId) {
        //将id和名字传过去做对比，如果这个id的名字是与 预设的分组名称一样，即不可删除
        Map<String, Object> param = new HashMap<>();
        param.put("groupId", groupId);
        param.put("defaultGroupName", ConstantParam.DEFAULT_GROUP_NAME);
        int tipCode = devGroupDao.isCanOperate(param);
        if (tipCode == 0) {
            return true;
        } else {
            return false;
        }
    }


    //根据判断用户下面是否已经存在该分组，返回是否可以继续添加的标识
    public Boolean isCanAddGroup(int userId, String groupName) {
        //查询用户下名下是否存在分组名为groupName的设备
        Map<String, Object> param = new HashMap<>();
        param.put("userId", userId);
        param.put("groupName", groupName);
        int groupCount = devGroupDao.getUserDeviceGroup(param);
        if (groupCount == 0) {
            return true;
        } else {
            return false;
        }
    }
}
